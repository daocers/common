package co.bugu.framework.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by daocers on 2016/5/26.
 */
public class LogUtil {
    public static Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static Logger getLogger(String name){
        return LoggerFactory.getLogger(name);
    }

    public static Logger getLogger(Class clazz){
        return LoggerFactory.getLogger(clazz);
    }

    public static void debug(){
    }
}
