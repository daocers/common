package co.bugu.framework.core.mybatis;

import co.bugu.framework.core.util.ReflectUtil;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.Properties;

/**
 * Created by QDHL on 2017/8/16.
 */
@Intercepts({
        @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
})
public class ParameterInterceptor implements Interceptor {
    private Logger logger = LoggerFactory.getLogger(ParameterInterceptor.class);
    private String interceptName = "findByObject";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long begin = System.currentTimeMillis();
        Method method = invocation.getMethod();
        System.out.println(method.getName());
        logger.debug("开始拦截");

        DefaultParameterHandler parameterHandler = (DefaultParameterHandler) invocation.getTarget();
        MappedStatement mappedStatement = (MappedStatement) ReflectUtil.get(parameterHandler, "mappedStatement");
        if (!mappedStatement.getId().contains(interceptName)) {
            return invocation.proceed();
        }
        if (SqlCommandType.SELECT != mappedStatement.getSqlCommandType()) {
            return invocation.proceed();
        }
        Map<String, Object> searchParameter = ThreadLocalUtil.get();

        BoundSql boundSql = (BoundSql) ReflectUtil.get(parameterHandler, "boundSql");
        Object paramObj = boundSql.getParameterObject();
        SqlSource targetSqlSource = DataUtil.getTargetDynamicSqlSource(mappedStatement, boundSql, searchParameter);
        ReflectUtil.setValue(parameterHandler, "boundSql", targetSqlSource.getBoundSql(paramObj));
        ReflectUtil.setValue(mappedStatement, "sqlSource", targetSqlSource);
        ThreadLocalUtil.remove();
        long end = System.currentTimeMillis();
        logger.debug("ParameterHandler 执行时长：{}毫秒", end - begin);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
