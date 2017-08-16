package co.bugu.framework.core.mybatis;

import org.apache.ibatis.mapping.MappedStatement;

import java.util.regex.Pattern;

/**
 * Created by daocers on 2017/8/16.
 */
public class InterceptorUtil {
    /**
     * 确定是否需要拦截
     *
     * @param mappedStatement
     * @return
     */
    public static boolean needIntercept(MappedStatement mappedStatement, String methodName) {
        String id = mappedStatement.getId();
        return Pattern.matches(methodName, id);
    }
}
