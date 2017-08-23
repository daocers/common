package co.bugu.tes.util;

import co.bugu.tes.model.Property;
import co.bugu.tes.model.PropertyItem;
import co.bugu.tes.model.QuestionMetaInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by user on 2017/3/16.
 * 题型帮助类
 */
public class QuestionMetaInfoUtil {
    private static Logger logger = LoggerFactory.getLogger(QuestionMetaInfoUtil.class);

    public static List<String> getModelTitle(QuestionMetaInfo metaInfo) {
        if (metaInfo == null) {
            return null;
        }
        List<String> title = new ArrayList<>();
        title.add("题目");
        List<Property> propertyList = metaInfo.getPropertyList();
        if (propertyList != null) {
            for (Property property : propertyList) {
                StringBuffer buffer = new StringBuffer();
                if (property.getPropertyItemList() != null) {
                    for (PropertyItem item : property.getPropertyItemList()) {
                        buffer.append(item.getName())
                                .append("|");
                    }
                }
                if (buffer.length() > 0) {
                    title.add(property.getName() + "(" + buffer.substring(0, buffer.length() - 1) + ")");
                } else {
                    title.add(property.getName());
                }
            }
        }
        if (metaInfo.getCode().equals("single") || metaInfo.getCode().equals("multi")) {
            title.add("最佳答案");
            title.add("选项A");
            title.add("选项B");
            title.add("选项C");
            title.add("选项D");
            title.add("选项E");
            title.add("选项F");
        } else if (metaInfo.getCode().equals("judge")) {
            title.add("最佳答案(正确|错误)");
        }
        return title;
    }


    /**
     * 验证模板标题是否有效
     *
     * @param title
     * @param metaInfo
     * @return
     */
    public static boolean checkModelTitle(List<String> title, QuestionMetaInfo metaInfo) {
        List<String> rightTitle = getModelTitle(metaInfo);
        if (rightTitle.size() > title.size()) {
            return false;
        }
        for (int i = 0; i < rightTitle.size(); i++) {
            if (!rightTitle.get(i).equals(title.get(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 校验数据是否正确 并处理数据，将属性值转化为属性id,如果是选择题，将答案设置为大写
     *
     * @param data
     * @param metaInfo
     * @return 如果有错误，返回错误信息  如第几行 第几列 数据【容易】有误
     */
    public static String checkData(List<List<String>> data, QuestionMetaInfo metaInfo) {

        //获取属性条目对应的name:id信息
        Map<String, Integer> itemInfo = new HashMap<>();
        if (metaInfo != null) {
            List<Property> propertyList = metaInfo.getPropertyList();
            if (propertyList != null) {
                for (Property property : propertyList) {
                    List<PropertyItem> itemList = property.getPropertyItemList();
                    if (itemList != null) {
                        for (PropertyItem item : itemList) {
                            itemInfo.put(item.getName(), item.getId());
                        }
                    }
                }
            }
        }
        List<String> title = getModelTitle(metaInfo);
        //存放对应的列的取值范围列表
        Map<Integer, List<String>> keyInfo = new HashMap<>();
        for (int i = 0; i < title.size(); i++) {
            String col = title.get(i);
            if (StringUtils.isNotEmpty(col) && col.contains("(") && col.contains(")")) {
                String tmp = col.substring(col.indexOf("(") + 1, col.indexOf(")"));
                String[] propInfo = tmp.split("[|]");
                keyInfo.put(i, Arrays.asList(propInfo));
            }
        }

        Iterator<Map.Entry<Integer, List<String>>> iter = keyInfo.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, List<String>> entry = iter.next();
            List<String> values = entry.getValue();
            Integer index = entry.getKey();


            for (int i = 0; i < data.size(); i++) {
                if(i == 0){
                    continue;
                }
                List<String> line = data.get(i);
                if (!values.contains(line.get(index))) {
                    return "第" + (i + 1) + "行，第" + (index + 1) + "列，【" + line.get(index) + "】有误";
                } else {
                    String col = line.get(index);
                    if (col.equals("错误")) {
                        line.set(index, "F");
                    } else if (col.equals("正确")) {
                        line.set(index, "T");
                    } else {
                        line.set(index, itemInfo.get(line.get(index)) + "");
                    }
                }
            }
        }
        return null;
    }
}
