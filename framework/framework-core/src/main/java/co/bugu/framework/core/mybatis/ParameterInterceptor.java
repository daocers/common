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
public class ParameterInterceptor implements Interceptor{
    private Logger logger = LoggerFactory.getLogger(ParameterInterceptor.class);
    private String methodNameIntercepted = "";
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        logger.debug("Parameter 拦截方法 {}", invocation.getMethod().getName());

        /**
        * 此处会拦截到ParameterHandler.setParameters()方法
         * 和statementHandler中的parameterize方法需要明确用途
         *
        * */


        long begin = System.currentTimeMillis();
        Map<String, Object> searchParam = ThreadLocalUtil.get();
        Object target = invocation.getTarget();


        DefaultParameterHandler parameterHandler = (DefaultParameterHandler) target;
        MappedStatement mappedStatement = (MappedStatement) ReflectUtil.get(parameterHandler, "mappedStatement");
        if (!InterceptorUtil.needIntercept(mappedStatement, methodNameIntercepted)) {
            return invocation.proceed();
        }
        if (SqlCommandType.SELECT != mappedStatement.getSqlCommandType()) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) ReflectUtil.get(parameterHandler, "boundSql");
        Object paramObj = boundSql.getParameterObject();
//        SqlSource targetSqlSource = getTargetDynamicSqlSource(mappedStatement, boundSql, searchParam);
//        ReflectUtil.setValue(parameterHandler, "boundSql", targetSqlSource.getBoundSql(paramObj));
//        ReflectUtil.setValue(mappedStatement, "sqlSource", targetSqlSource);
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
        this.methodNameIntercepted = properties.getProperty("method");
    }
}
