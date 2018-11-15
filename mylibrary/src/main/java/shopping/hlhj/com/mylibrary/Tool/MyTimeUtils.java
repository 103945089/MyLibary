package shopping.hlhj.com.mylibrary.Tool;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Never Fear   on 2018\11\5 0005.
 * Never More....
 */

public class MyTimeUtils {
    /**
     * 将一个时间戳转换成提示性时间字符串，今天 xxx；
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToCustom(long timeStamp) {
        String times = timeStamp + "";
        long current = System.currentTimeMillis();
        long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        long curTime = zero / (long) 1000;

        long time = curTime - timeStamp;
        if (time<0){
            return "今天";
        }else if (time>0&&time<60*60*24){
            return "昨天";
        }else if (time>3600*24&&time<60*60*48){
            return "前天";
        }
        else {
            return timeStampToMD(timeStamp);
        }

    }
    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStrYMD(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
    public static String timeStampToStrYMMD(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
    public static String timeStampToStrY(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
    /*获得年*/
    public static String getYear(){
        long timeMillis = System.currentTimeMillis();
        String ym = MyTimeUtils.timeStampToYM(timeMillis);
        return ym.split("-")[0];
    }
    /*获得月*/
    public static String getMonth(){
        long timeMillis = System.currentTimeMillis();
        String ym = MyTimeUtils.timeStampToYM(timeMillis);
        return ym.split("-")[1];
    }
    /**
     * 时间戳转化为小时分钟时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToHM(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
    public static String timeStampToMD(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
    public static String timeStampToYM(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
    public static String timeStampToD(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
}
