package co.bugu.framework.core.mybatis;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * Created by QDHL on 2017/8/16.
 */
@Intercepts({
        @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
})
public class ParameterInterceptor implements Interceptor{
    private Logger logger = LoggerFactory.getLogger(ParameterInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        System.out.println(method.getName());
        logger.debug("开始拦截");
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
