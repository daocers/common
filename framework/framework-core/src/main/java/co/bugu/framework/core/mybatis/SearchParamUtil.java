package co.bugu.framework.core.mybatis;

import co.bugu.framework.core.util.ReflectUtil;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2017/2/13.
 * 搜索参数帮助类
 */
public class SearchParamUtil {
    private static Logger logger = LoggerFactory.getLogger(SearchParamUtil.class);

    private static final Object lockObj = new Object();

    private static Map<String, ThreadLocal<SimpleDateFormat>> formatMap = new HashMap<>();

    private static SimpleDateFormat getSimpleDateFormat(final String pattern) {
        ThreadLocal<SimpleDateFormat> threadLocal = formatMap.get(pattern);

//        此处使用双重判断和同步是为了防止formatMap这个单例被多次put重复的sdf
        if (threadLocal == null) {
            synchronized (lockObj) {
                threadLocal = formatMap.get(pattern);
                if (threadLocal == null) {
//                    只有map中没有这个pattern的sdf才会放入map
                    threadLocal = new ThreadLocal<>();
                    threadLocal.set(new SimpleDateFormat(pattern));
                    formatMap.put(pattern, threadLocal);
                }
            }
        }
        return threadLocal.get();
    }
    /**
     * 根据获取到的查询参数为查询实体类赋值
     * 查询参数格式为： LK_name, IN_age之类
     * @param obj
     * @param param
     * @param <T>
     * @return
     * @throws Exception
     */
//    public static <T> T getParamObject(T obj, Map<String, Object> param) throws Exception {
//        if(obj == null){
//            throw new Exception("参数类型不能为空");
//        }
//        Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String, Object> entry = iterator.next();
//            String key = entry.getKey();
//            Object val = entry.getValue();
//            if(key.contains("_")){
//                String fieldName = key.split("_")[1];
//                ReflectUtil.setValue(obj, fieldName, val);
//            }else{
//                logger.error("查询参数不合法，参数：{}", key);
//                throw new Exception("不合法的查询参数");
//            }
//        }
//        return obj;
//    }

//    /**
//     * 直接从HttpServletRequest对象中获取查询参数
//     * @param obj
//     * @param request
//     * @param <T>
//     * @return
//     * @throws Exception
//     */
//    public static <T> T getParamObject(T obj, HttpServletRequest request) throws Exception {
//        if(obj == null){
//            throw new Exception("参数类型不能为空");
//        }
//        Map<String, Object> param = request.getParameterMap();
//        Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String, Object> entry = iterator.next();
//            String key = entry.getKey();
//            Object val = entry.getValue();
//            if(key.contains("_")){
//                String fieldName = key.split("_")[1];
//                ReflectUtil.setValue(obj, fieldName, val);
//            }
//        }
//        return obj;
//    }

    /**
     * 处理查询参数，用于mybatis拦截处理
     * 1 获取查询参数，处理成map类型，将map放在threadlocal中
     * 2 将查询参数的值赋值到查询参数实体中。
     *
     * @param obj
     * @param request
     */
    public static Map<String, Object> processSearchParam(Object obj, HttpServletRequest request) throws Exception {
        if (obj == null) {
            throw new Exception("参数类型不能为空");
        }
        Map<String, Object> searchParam = new HashMap<>();
        Map<String, String[]> param = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> entry = iterator.next();
            String key = entry.getKey();
            String[] val = entry.getValue();
            //没有传值，会传来一个空字符串
            if (val.length == 1 && val[0].equals("")) {
                searchParam.put(key, null);
                continue;
            }
            if (key.contains("_")) {
                String value = null;
                String fieldName = key.split("_")[1];

                if (val.length == 0) {
                    continue;
                } else if (val.length == 1) {
                    value = val[0];
                    searchParam.put(key, value);
                } else {
                    //多个参数，暂时不作处理
                }
                Class type = obj.getClass().getDeclaredField(fieldName).getType();
                Object finalVal = null;
                if (type == Date.class) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    finalVal = format.parse(value);
                } else {
                    finalVal = ConvertUtils.convert(value, type);
                }
                ReflectUtil.setValue(obj, fieldName, finalVal);
            }
        }
        ThreadLocalUtil.set(searchParam);
        return searchParam;
    }
}
