package co.bugu.tes.aop;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by daocers on 2017/8/15.
 * 方法执行之前执行
 * 进行权限校验
 */
public class AuthorityCheckAop implements MethodBeforeAdvice {
    private Logger logger = LoggerFactory.getLogger(AuthorityCheckAop.class);

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        Class clazz = method.getDeclaringClass();
        StringBuilder builder = new StringBuilder();
        builder.append(clazz.getName()).append(method.getName());
        if (ArrayUtils.isNotEmpty(args)) {
            for (Object arg : args) {
                builder.append(arg.getClass().getSimpleName());
            }
        }
        String code = builder.toString();
        try {
            SecurityUtils.getSubject().checkPermission(code);
        } catch (AuthorizationException e) {
            logger.error("权限校验失败", e);
        }
    }
}
