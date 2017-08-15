package co.bugu.framework.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daocers on 2016/9/19.
 */
public class DateConverter implements Converter<String, Date> {

    private static Logger logger = LoggerFactory.getLogger(DateConverter.class);

    @Override
    public Date convert(String stringDate) {
        if(StringUtils.isEmpty(stringDate)){
            return null;
        }
        if(stringDate.length() == 10){
            stringDate = stringDate + " 00:00:00";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            return format.parse(stringDate);
        } catch (ParseException e) {
            logger.error("日期转换失败", e);
        }
        return null;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = "2016-11-12";
        format.parse(s);
    }
}
