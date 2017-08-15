package co.bugu.framework.core.util;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by daocers on 2016/6/30.
 */
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ApplicationContextUtil.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.debug("****************开始********************");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warning");
        logger.error("error");
        logger.trace("trace");
        ApplicationContextUtil.context = applicationContext;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> tClass) {
        return context.getBean(name, tClass);
    }

    public static <T> T getBean(Class<T> tClass) {
        return context.getBean(tClass);
    }

    public static boolean containsBean(String name) {
        return context.containsBean(name);
    }
}
