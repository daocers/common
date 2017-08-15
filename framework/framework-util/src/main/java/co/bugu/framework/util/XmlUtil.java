package co.bugu.framework.util;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xml 解析生成工具类
 * Created by daocers on 2016/9/23.
 */
public class XmlUtil {

    public static final String FIELD_POLICY_ORIGINAL = "original";
    public static final String FIELD_POLICY_UNDERLINE = "under_line";

    /**
     * 解析request，生成map数据
     * @param @param  request
     * @param @return
     * @param @throws Exception
     * @Description: 解析微信发来的请求（XML）
     * @author dapengniao
     * @date 2016 年 3 月 7 日 上午 10:04:02
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在 HashMap 中
        Map<String, String> map = new HashMap<String, String>();

        // 从 request 中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到 xml 根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {

        Book book = new Book();
        book.setId(121);
        book.setName("this is a good book");
        book.setAuthorName("daocers");

        Line line = new Line();
        line.setLineNumber(100);
        line.setData("this is the 100th data");
        Line line1 = new Line();
        line1.setLineNumber(101);
        line1.setData("this is the 101th data;.....");
        List<Line> lines = new ArrayList<>();
        lines.add(line);
        lines.add(line1);
        book.setLineList(lines);


        String xml = objToXml(book, FIELD_POLICY_UNDERLINE);
        System.out.println(xml);
        System.out.println("**********************************");

        Book book1 = xmlToObj(xml, Book.class, FIELD_POLICY_UNDERLINE);
        System.out.println(JSON.toJSONString(book1, true));

    }

    /**
     * javabean 转换为xml
     * @param object
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static String objToXml(Object object, String fieldPolicy) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        XStream xStream = processAlias(object.getClass(), fieldPolicy);
        return xStream.toXML(object);
    }

    /**
     * 从xml转换为对象
     * @param xml
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T xmlToObj(String xml, Class<T> tClass, String filedPolicy) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        XStream xStream = processAlias(tClass, filedPolicy);

        T res = (T) xStream.fromXML(xml);
        return res;
    }

    /**
     * 处理别名
     * @param tClass
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static XStream processAlias(Class tClass, String fieldPolicy) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.processAnnotations(tClass);
//      处理 类别名
        xStream.alias(getAliasOfClass(tClass), tClass);

//      处理字段别名
        Field[] fields = tClass.getDeclaredFields();
        for(Field field: fields){
            Type type = field.getGenericType();
            if(type instanceof ParameterizedType){
                String className = ((ParameterizedType)type).getActualTypeArguments()[0].getTypeName();
                xStream.alias(className2var(className), Class.forName(className));
            }
            String fieldAlias = getAliasOfField(field, fieldPolicy);
            xStream.aliasField(fieldAlias, tClass, field.getName());
        }
        return xStream;


    }

    /**
     * class类转换为变量
     * @param className
     * @return
     */
    private static String className2var(String className) {
        className = className.substring(className.lastIndexOf(".") + 1);
        className = className.substring(0, 1).toLowerCase() + className.substring(1);
        return className;
    }

    public static String getAliasOfClass(Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Annotation annotation = clazz.getAnnotation(XStreamAlias.class);
        String alias = null;
        if(annotation != null){
            Method valueMethod = annotation.annotationType().getDeclaredMethod("value");
            alias = (String) valueMethod.invoke(annotation, null);
        }

        if(StringUtils.isEmpty(alias)){
            String className = clazz.getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            alias = className.substring(0, 1).toLowerCase() + className.substring(1);
        }
        return alias;
    }

    public static String getAliasOfField(Field field, String fieldPolicy) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Annotation annotation = field.getAnnotation(XStreamAlias.class);
        String alias = null;
        if(annotation != null){
            Method valueMethod = annotation.annotationType().getDeclaredMethod("value");
            alias = (String) valueMethod.invoke(annotation, null);
        }

        if(StringUtils.isEmpty(alias)){
            if(FIELD_POLICY_UNDERLINE.equals(fieldPolicy)){
                String fieldName = field.getName();
                alias = camelToUnderLine(fieldName);
            }else if(FIELD_POLICY_ORIGINAL.equals(fieldPolicy)){
                alias = field.getName();
            }else{
                alias = field.getName();
            }

        }
        return alias;
    }

    /**
     * 驼峰命名法转换为下环线表示
     * @param source
     * @return
     */
    private static String camelToUnderLine(String source){
        source = source.substring(0, 1).toLowerCase() + source.substring(1);
        StringBuilder builder = new StringBuilder();
        for(char c: source.toCharArray()){
            if((c >= 'A' && c <= 'Z')|| c == '$'){
                builder.append("_");
            }
            builder.append(("" + c).toLowerCase());
        }
        return builder.toString();
    }

}
