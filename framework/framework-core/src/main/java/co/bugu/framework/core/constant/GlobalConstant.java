package co.bugu.framework.core.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局变量
 * properties 会加载到该类中，作为map维护，需要手动调用add方法
 * 一般用于将所有的配置属性加载到该类中
 * Created by daocers on 2016/6/14.
 */
public class GlobalConstant {
    private static Logger logger = LoggerFactory.getLogger(GlobalConstant.class);

    private static Map<String, String> data = new HashMap<>();

    public static String get(String name){
        return data.get(name);
    }

    public static Integer getInt(String name){
        if(data.containsKey(name)){
            String tmp = data.get(name);
            return Integer.valueOf(tmp);
        }
        logger.info("[全局数据不包含字段：{}]", name);
        return null;
    }

    public static Boolean getBoolean(String name){
        if(data.containsKey(name)){
            String tmp = data.get(name);
            return "true".equals(tmp);
        }
        logger.info("[全局数据不包括字段：{}]", name);
        return null;
    }

//    /**
//     * 添加properties到该常量中
//     * @param properties
//     */
//    public static void add(BuguProperties properties){
//        Set<String> set = properties.getProperties().stringPropertyNames();
//        for(String key: set){
//            data.put(key, properties.get(key));
//        }
//        logger.debug("[全局数据添加完毕]");
//    }
}
