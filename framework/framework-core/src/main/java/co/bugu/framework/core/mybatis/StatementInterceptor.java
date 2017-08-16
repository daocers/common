package co.bugu.framework.core.mybatis;

import co.bugu.framework.core.util.ReflectUtil;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.IconView;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

/**
 * Created by QDHL on 2017/8/16.
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = StatementHandler.class, method = "parameterize", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "getParameterHandler", args = {}),
        @Signature(type = StatementHandler.class, method = "getBoundSql", args = {})
})
public class StatementInterceptor implements Interceptor {
    private Logger logger = LoggerFactory.getLogger(StatementInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.debug("statement开始拦截： 方法 {}", invocation.getMethod().getName());
        Method method = invocation.getMethod();
        System.out.println(method.getName());

        /**
         * 拦截到prepare方法，修改sql语句
         *
         * */
        if("prepare".equals(method.getName())){

        }

        /**
         * 拦截到parameterize方法，可以进行参数设置
         *
         * */
        if("parameterize".equals(method.getName())){

        }

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
        // 可以分离出最原始的的目标类)
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        // 分离最后一个代理对象的目标类
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
//            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

        BoundSql boundSql = statementHandler.getBoundSql();
//        if (!InterceptorUtil.needIntercept(mappedStatement)) {
//            return invocation.proceed();
//        }
//
//        if (SqlCommandType.SELECT != mappedStatement.getSqlCommandType()) {
//            return invocation.proceed();
//        }
//        RoutingStatementHandler handler = (RoutingStatementHandler) target;
//        BoundSql boundSql = statementHandler.getBoundSql();
//        Object paramObj = boundSql.getParameterObject();
//        Map<String, Object> searchParameter = ThreadLocalUtil.get();
//
//        //           没有初始化，新进行初始化操作。
//        if (resultMappingInfo.size() == 0) {
//            initPropertyColumnMap(mappedStatement);
//        }
//
//        SqlSource dynamicSqlSource = getTargetDynamicSqlSource(mappedStatement, boundSql, searchParameter);
//        ReflectUtil.setValue(boundSql, "sql", dynamicSqlSource.getBoundSql(paramObj).getSql());
        /**
         * 清除掉查询参数，防止对后续请求造成干扰
         */
        long end = System.currentTimeMillis();

//        logger.debug("StatementInterceptor 执行时长：{}毫秒", end - begin);


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
