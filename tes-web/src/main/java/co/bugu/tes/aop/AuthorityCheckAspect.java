package co.bugu.tes.aop;

import co.bugu.tes.controller.LoginController;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by daocers on 2017/8/15.
 */
public class AuthorityCheckAspect {
    private Logger logger = LoggerFactory.getLogger(AuthorityCheckAspect.class);

    public void before(JoinPoint joinPoint) {
        Class clazz = joinPoint.getSignature().getDeclaringType();
        //loginController中的方法直接放行，不做校验
        if(!clazz.getName().equals(LoginController.class.getName())){
            Object[] args = joinPoint.getArgs();
            StringBuilder builder = new StringBuilder();
            builder.append(clazz.getName())
                    .append(joinPoint.getSignature().getName());
            if (ArrayUtils.isNotEmpty(args)) {
                for (Object arg : args) {
                    builder.append(arg.getClass().getSimpleName());
                }
            }

            /**
             * 权限控制，如果没有权限，抛出AuthorizationException
             * */
            SecurityUtils.getSubject().checkPermission(builder.toString());
        }


    }

}
