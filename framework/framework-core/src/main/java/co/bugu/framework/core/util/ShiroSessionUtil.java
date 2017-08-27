package co.bugu.framework.core.util;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by daocers on 2017/8/26.
 */
public class ShiroSessionUtil {
    private static Logger logger = LoggerFactory.getLogger(ShiroSessionUtil.class);

    /**
     * 获取用户id
     * @return
     */
    public static Integer getUserId(){
        return (Integer) SecurityUtils.getSubject().getSession().getAttribute("userId");
    }

    /**
     * 获取属性值
     * @param key
     * @return
     */
    public static Object getAttribute(Object key){
        return SecurityUtils.getSubject().getSession().getAttribute(key);
    }
}
