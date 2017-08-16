package co.bugu.framework.core.mybatis;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by QDHL on 2017/8/16.
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = StatementHandler.class, method = "parameterize", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "getBoundSql", args = {})
})
public class StatementInterceptor implements Interceptor {
    private Logger logger = LoggerFactory.getLogger(StatementInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.debug("开始拦截");
        Method method = invocation.getMethod();
        System.out.println(method.getName());
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
