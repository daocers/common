package co.bugu.framework.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by daocers on 2016/9/28.
 */
public class DateUtil {
    /**
     * 获取时间戳，以秒为单位
     * @return
     */
    public static long getTimestampOfSecond(){
        return getTimestampOfMillSec() / 1000;
    }

    /**
     * 获取时间戳
     * 以毫秒为单位
     * @return
     */
    public static long getTimestampOfMillSec(){
        return new Date().getTime();
    }

    public static void main(String[] args){
        System.out.println(getTimestampOfMillSec());
        System.out.println(getTimestampOfSecond());
    }

    public static Date add(Date base, Integer type, Integer increment){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(base);
        calendar.add(type, increment);
        return calendar.getTime();
    }

    public static String formatLeft(Long millSeconds){
        Long seconds = millSeconds/1000;
        Long sec =  (seconds % 60);
        Long min =  (seconds/60 % 60);
        long hour =  (seconds/60/60);
        return hour + "h" + min + "m" + sec + "s";
    }
}
