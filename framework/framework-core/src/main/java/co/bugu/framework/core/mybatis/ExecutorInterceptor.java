package co.bugu.framework.core.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * Created by QDHL on 2017/8/16.
 * 此处不好处理，query方法有重载
 */
@Intercepts({
        @Signature(type = Executor.class, method = "", args = {})
})
public class ExecutorInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
