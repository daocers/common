package co.bugu.framework.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by user on 2017/1/19.
 */
public class DateFormatter implements Formatter<Date>{
    private static Logger logger = LoggerFactory.getLogger(DateFormatter.class);
    @Override
    public Date parse(String stringDate, Locale locale) throws ParseException {
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

    @Override
    public String print(Date date, Locale locale) {
        return null;
    }
}
