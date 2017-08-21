package co.bugu.framework.core.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.scripting.xmltags.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by QDHL on 2017/8/21.
 */
public class DataUtil {
    private static Logger logger = LoggerFactory.getLogger(DataUtil.class);
    public static Map<Class, Map<String, String>> resultMappingInfo = new HashMap<>();


    /**
     * 创建最终需要执行的DynamicSource
     *
     * @return
     */
    public static DynamicSqlSource getTargetDynamicSqlSource(MappedStatement mappedStatement, BoundSql boundSql, Map<String, Object> searchParam) throws Exception {
        boolean sqlMode = false;
        Object paramObject = null;
        String sql = "";

        if (mappedStatement.getResultMaps().size() == 1) {
            Class type = mappedStatement.getResultMaps().get(0).getType();
            paramObject = boundSql.getParameterObject();

            sql = boundSql.getSql();
            if (sql.toLowerCase().contains("where")) {
                sql = sql.substring(0, sql.toLowerCase().indexOf("where"));
            }
            if (sql.toLowerCase().contains(" order ")) {
                sql = sql.substring(0, sql.toLowerCase().indexOf(" order "));
            }

            Map<String, String> sortTypeMap = new HashMap<>();
            Map<Integer, String> sortIndexMap = new TreeMap<>();
            List<SqlNode> ifNodes = new ArrayList<>();

            //如果搜索参数map为空，根据查询对象来初始化搜索参数
            if (searchParam == null) {
                searchParam = processSearchParam(paramObject);
            }

            Iterator<String> iterator = searchParam.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = searchParam.get(key);
                if (key.contains("_")) {//需要筛选，处理where子句
                    String[] keyInfo = key.split("_");
                    String relation = keyInfo[0];
                    String property = keyInfo[1];
                    String column = resultMappingInfo.get(type).get(property);
                    String orderType = null;
                    Integer orderIndex = null;
                    if (keyInfo.length > 2) {
                        orderType = keyInfo[2].toUpperCase();
                    }
                    if (keyInfo.length > 3) {
                        try {
                            orderIndex = Integer.parseInt(keyInfo[3]);
                        } catch (NumberFormatException E) {
                            logger.error("查询参数有误， 错误查询参数名为：{}", key);
                        }
                    }
                    if (StringUtils.isNotEmpty(orderType)) {
                        sortTypeMap.put(column, orderType);
                    }
                    if (orderIndex != null) {
                        sortIndexMap.put(orderIndex, column);
                    }

                    if (value != null) {
                        StaticTextSqlNode staticTextSqlNode = new StaticTextSqlNode(processRelation(relation, column, property));
                        IfSqlNode ifSqlNode = new IfSqlNode(staticTextSqlNode, property + " != null");
                        ifNodes.add(ifSqlNode);
                    } else {
                        //参数值为空，只做排序，不做筛选
                    }
                } else {
                    if ("SQL".equals(key)) {
                        sqlMode = true;
                        sql = (String) searchParam.get(key);
                    }
                }
            }

            DynamicContext dynamicContext = new DynamicContext(mappedStatement.getConfiguration(), paramObject);
            List<SqlNode> content = new ArrayList<>();
            if (sqlMode) {
                content.add(new StaticTextSqlNode(sql));
            } else {
                content.add(new StaticTextSqlNode(sql));
                if (ifNodes != null || ifNodes.size() > 0) {
                    content.add(new WhereSqlNode(mappedStatement.getConfiguration(), new MixedSqlNode(ifNodes)));
                }
            }

            String orderSql = getOrderSQL(sortTypeMap, sortIndexMap);
            if (StringUtils.isNotEmpty(orderSql)) {
                content.add(new StaticTextSqlNode(orderSql));
            }
            MixedSqlNode mixedSqlNode = new MixedSqlNode(content);
            mixedSqlNode.apply(dynamicContext);
            sql = dynamicContext.getSql();
            logger.debug("最终的sql语句为：{}", sql);
            logger.debug("dyS: {}", System.currentTimeMillis() / 1000);
            DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(mappedStatement.getConfiguration(), mixedSqlNode);
            logger.debug("dyE: {}", System.currentTimeMillis() / 1000);
            BoundSql targeBoundSql = dynamicSqlSource.getBoundSql(paramObject);
            return dynamicSqlSource;
        }
        return null;
    }


    /**
     * 初始化property和column的对应关系
     *
     * @param mappedStatement
     */
    public static synchronized void initPropertyColumnMap(MappedStatement mappedStatement) {
        Collection<MappedStatement> mappedStatementList = mappedStatement.getConfiguration().getMappedStatements();
        Iterator iter = mappedStatementList.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            if (obj instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) obj;
                if (ms.getResultMaps().size() == 1) {
                    Class type = ms.getResultMaps().get(0).getType();
                    Map<String, String> map = new HashMap<>();
                    List<ResultMapping> resultMappings = ms.getResultMaps().get(0).getResultMappings();
                    for (ResultMapping resultMapping : resultMappings) {
                        if (resultMapping.getJdbcType() != null) {
                            String column = resultMapping.getColumn();
                            String property = resultMapping.getProperty();
//                            boolean isId = false;
//                            if(resultMapping.getFlags().contains(ResultFlag.ID)){
//                                isId = true;
//                            }
                            map.put(property, column);
                        }
                    }
                    resultMappingInfo.put(type, map);
                }
            }
        }
    }


    /**
     * 直接传查询参数，进行等值查询
     *
     * @param paramObject
     * @return
     */
    public static Map<String, Object> processSearchParam(Object paramObject) throws IllegalAccessException {
        Map<String, Object> searchParam = new HashMap<>();
        if (paramObject == null) {
            return searchParam;
        }
        Field[] fields = paramObject.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(paramObject);
            Class type = field.getType();
//          不处理基本类型，建议在domain中都使用包装类型
            if (type != int.class
                    && type != byte.class
                    && type != char.class
                    && type != int.class
                    && type != double.class
                    && type != float.class
                    && type != long.class) {
                if (value == null || (type == String.class && StringUtils.isEmpty((String) value))) {
                    continue;
                }
                searchParam.put("EQ_" + name, value);
            }
        }
        ThreadLocalUtil.set(searchParam);
        return searchParam;
    }


    /**
     * 根据传入的关系数值，获取到最终的sql关系
     *
     * @param relation
     * @return
     */
    public static String processRelation(String relation, String column, String property) throws Exception {
        String res = null;
        if (StringUtils.isNotEmpty(relation)) {
            if ("EQ".equals(relation)) {
                res = "=";
            }
            if ("NEQ".equals(relation)) {
                res = "!=";
            }
            if ("LT".equals(relation)) {
//                res = "<![CDATA[<]]>";
                res = "<";
            }
            if ("LTE".equals(relation)) {
//                res = "<![CDATA[<=]]>";
                res = "<=";
            }
            if ("GT".equals(relation)) {
//                res = "<![CDATA[>]]>";
                res = ">";
            }
            if ("GTE".equals(relation)) {
//                res = "<![CDATA[>=]]>";
                res = ">=";
            }
            if ("IN".equals(relation)) {
                res = "in";
            }
            if ("NIN".equals(relation)) {
                res = "not in";
            }
            if ("LK".equals(relation)) {
                res = "like";
            }
            if (res == null) {
                throw new Exception("不能识别的数据库关系");
            }

            if ("IN".equals(relation) || "NIN".equals(relation)) {
                res = " and " + column + " " + res + " (#{" + property + "})";
            } else if ("LK".equals(relation)) {
//                res = " and " + column + " " + res + "% ||#{" + property + "}|| %";
                res = " and " + column + " " + res + " CONCAT('%', #{" + property + "} ,'%') ";
            } else {
                res = " and " + column + " " + res + " #{" + property + "}";
            }
            return res;
        } else {
            throw new Exception("不能识别的数据库关系");
        }
    }


    /**
     * 获取排序sql语句段
     *
     * @param sortTypeMap
     * @param sortIndexMap
     * @return
     */
    public static String getOrderSQL(Map<String, String> sortTypeMap, Map<Integer, String> sortIndexMap) {
        String resSQL = "";
        if (sortIndexMap == null || sortIndexMap.size() == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
//        先遍历有排序先后信息的
        for (Map.Entry<Integer, String> entry : sortIndexMap.entrySet()) {
            Integer index = entry.getKey();
            String column = entry.getValue();
            String orderType = sortTypeMap.get(column);
            stringBuilder.append(column)
                    .append(" ")
                    .append(orderType)
                    .append(",");
            sortTypeMap.remove(column);
        }
//        没有指定排序先后顺序的，不处理
        for (Map.Entry<String, String> entry : sortTypeMap.entrySet()) {
            String column = entry.getKey();
            String orderType = entry.getValue();
            stringBuilder.append(column)
                    .append(" ")
                    .append(orderType)
                    .append(",");
        }
        if (stringBuilder.length() > 0) {
            resSQL = " order by " + stringBuilder.substring(0, stringBuilder.length() - 1);
        }
        return resSQL;
    }

}
