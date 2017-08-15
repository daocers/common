package co.bugu.framework.core.constant;

import co.bugu.framework.core.util.BuguPropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by daocers on 2016/5/25.
 */
public class SysProperty {
    private static Logger logger = LoggerFactory.getLogger(SysProperty.class);
    private static co.bugu.framework.core.util.BuguProperties properties = null;

    static {
        try {
            properties = BuguPropertiesUtil.load("conf/system.properties");
        } catch (IOException e) {
            logger.error("[加载默认配置文件]， 失败", e);
        }
    }

    private static String get(String key ){
        return properties.get(key);
    }

    private static Integer getInt(String key){
        return properties.getInt(key);
    }


}
