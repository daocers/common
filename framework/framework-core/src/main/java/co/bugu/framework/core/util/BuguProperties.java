package co.bugu.framework.core.util;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Properties;

/**
 * Created by daocers on 2016/5/30.
 */
@RequestMapping(value = "/test")
public class BuguProperties {
    private Properties properties;

    public BuguProperties(){

    }

    public BuguProperties(Properties properties){
        this.properties = properties;
    }

    public Properties getProperties(){
        return properties;
    }

    public Integer getInt(String key){
        if(key == null){
            return null;
        }
        if(!properties.containsKey(key)){
            return null;
        }
        return Integer.valueOf(properties.getProperty(key));
    }

    public String get(String key){
        if(key == null){
            return null;
        }
        if(!properties.containsKey(key)){
            return null;
        }
        return properties.getProperty(key);
    }
}
