package co.bugu.framework.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by daocers on 2016/7/29.
 */
public class ReflectUtil {
    private static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    public static void getAnnotationOfController(String basePackage){
        Package pkg = Package.getPackage(basePackage);
        pkg.getClass().getClassLoader();

    }

    public static void main(String[] args){
        String name = "com.pinkbox.kacha.framework";
//        Set<Class<?>> classes = getClasses(name);
//        List<MvcParam> list = getAnnotationInfo(name);
//        logger.debug(list.size() + "");
        List<Integer> list = null;

        for(Integer i: list){
            System.out.println(i);
        }
    }

    /**
     * 获取mvc中的注解信息
     * @param packageName
     * @return
     */
    public static List<MvcParam> getAnnotationInfo(String packageName){
        List<MvcParam> res = new ArrayList<>();
//        获取当前包下的所有类
        Set<Class<?>> classes = getClasses(packageName);
        for(Class<?> cla : classes){
//            扫描当前类是否有controller注解，没有就略过
            Controller con = cla.getAnnotation(Controller.class);
            if(con == null){
                continue;
            }
//            当前类是否有requestMapping注解
            RequestMapping requestMapping = cla.getDeclaredAnnotation(RequestMapping.class);
            if(requestMapping == null){
                continue;
            }
            String[] rootPath = requestMapping.value();
            Method[] methods = cla.getDeclaredMethods();

            for(Method method: methods){
                MvcParam mvcParam = new MvcParam();
                mvcParam.setControllerName(cla.getSimpleName());
                mvcParam.setController(cla.getName());
                mvcParam.setMethodName(method.getName());
                if(rootPath.length > 0){
                    mvcParam.setRootPath(rootPath[0]);
                }
                method.setAccessible(true);
                RequestMapping methodRequestMapping = method.getDeclaredAnnotation(RequestMapping.class);
                if(methodRequestMapping == null){
                    continue;
                }
                String[] path = methodRequestMapping.value();
                if(path.length > 0){
                    mvcParam.setPath(path[0]);
                }
                RequestMethod[] requestMethods = methodRequestMapping.method();
                if(requestMethods.length > 0){
                    StringBuilder builder = new StringBuilder();
                    for(RequestMethod m: requestMethods){
                        builder.append(m.name()).append("|");
                    }
                    if(builder.length() > 0){
                        mvcParam.setMethod(builder.substring(0, builder.length() - 1));
                    }
                }
                ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
                if(responseBody != null){
                    mvcParam.setApi(true);
                }else{
                    mvcParam.setApi(false);
                }
                res.add(mvcParam);
            }
        }
        return res;
    }


    /**
     * 通过包名获取包下所有的 类
     * @param pack
     * @return
     */
    public static Set<Class<?>> getClasses(String pack){
        Set<Class<?>> classes = new LinkedHashSet<>();
//        是否循环迭代
        boolean recursive = true;
        String packageName = pack;
        String packageDirName = packageName.replace(".", "/");
        Enumeration<URL> dirs;
        try{
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while(dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if("file".equals(protocol)){
                    logger.debug("扫描结果： file类型");
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                }else if("jar".equals(protocol)){
                    logger.debug("扫描结果： jar类型");
                    JarFile jar;
                    try{
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection())
                                .getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx)
                                            .replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class")
                                            && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(
                                                packageName.length() + 1, name
                                                        .length() - 6);
                                        try {
                                            // 添加到classes
                                            classes.add(Class
                                                    .forName(packageName + '.'
                                                            + className));
                                        } catch (ClassNotFoundException e) {
                                            // log
                                            // .error("添加用户自定义视图类错误 找不到此类的.class文件");
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }catch (Exception e){
                        logger.error("扫描用户定义视图时从jar包获取文件出错", e);
                    }
                }
            }
        }catch (Exception e){
            logger.error("失败", e);
        }
        return classes;
    }

    private static void findAndAddClassesInPackageByFile(String packageName,
             String filePath, final boolean recursive, Set<Class<?>> classes) {
        File dir = new File(filePath);
        if(!dir.exists() || !dir.isDirectory()){
            logger.info("指定包：{} 下没有任何文件", packageName);
            return ;
        }

        File[] dirFiles = dir.listFiles(new FileFilter() {
//           自定义过滤规则 如果可以循环（包含子目录），或者是以.class结尾的文件
            @Override
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });

        //循环所有文件
        for(File file: dirFiles){
            if(file.isDirectory()){
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);;
            }else{
//                去掉后面的.class，留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try{
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                } catch (ClassNotFoundException e) {
                    logger.error("加载类失败", e);
                }
            }
        }
    }


    /**
     * 反射赋值
     * @param object
     * @param fieldName
     * @param value
     * @throws Exception
     */
    public static void setValue(Object object, String fieldName, Object value) throws Exception {
        if(object == null){
            throw new Exception("Object参数不能为null");
        }
        if(StringUtils.isEmpty(fieldName)){
            throw new Exception("字段名称不能为空");
        }
        Field field = object.getClass().getDeclaredField(fieldName);
//        Object finalVal = ConvertUtils.convert(value, field.getType());
        field.setAccessible(true);
        field.set(object, value);
    }

    /**
     * 获取对应字段的值
     * @param object
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static Object get(Object object, String fieldName) throws Exception {
        if(object == null){
            throw new Exception("Object参数不能为null");
        }
        if(StringUtils.isEmpty(fieldName)){
            throw new Exception("字段名称不能为空");
        }
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}
