package co.bugu.framework.core.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by QDHL on 2017/8/16.
 */
@Intercepts({
        @Signature(type = Executor.class, method = "", args = {})
})
public class ExecutorInterceptor implements Interceptor {
    private Logger logger = LoggerFactory.getLogger(ExecutorInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.debug("executor 拦截方法 {}", invocation.getMethod().getName());
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
