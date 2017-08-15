package co.bugu.framework.core.util;


import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by daocers on 2016/5/25.
 */
public class BuguPropertiesUtil {

    private static Properties properties;

    /**
     * 加载classpath下的路径文件
     * @param name
     * @return
     * @throws IOException
     */
    public static BuguProperties load(String name) throws IOException {
        if(StringUtils.isEmpty(name)){
            return null;
        }
        InputStream inputStream = BuguPropertiesUtil.class.getClassLoader().getResourceAsStream(name);
        if(inputStream == null){
            throw new FileNotFoundException("没有在classpath下发现文件 " + name);
        }
        properties = new Properties();
        properties.load(inputStream);
        return new BuguProperties(properties);
    }

}
