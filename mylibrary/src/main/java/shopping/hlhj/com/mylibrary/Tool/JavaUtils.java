package shopping.hlhj.com.mylibrary.Tool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zzh
 */

public class JavaUtils {

    /**
     * @param time 时间类型字符串2015-11-20
     * @return 将2015-11-20时间格式转换成2015年11月20日格式返回
     */
    public static String getTime(String time) {
        StringBuffer sb = new StringBuffer();
        String times[] = time.split("-");
        for (int i = 0; i < times.length; i++) {
            if (i == 0) {
                sb.append(times[i] + "年");
            } else if (i == 1) {
                sb.append(times[i] + "月");
            } else {
                sb.append(times[i] + "日");
            }
        }
        return sb.toString();
    }

    /**
     * @param time1
     * @param time2
     * @return 判断两个时间大小 等于0相等 小于0time1<time2,大于0反之
     */
    public static int comperTime(String time1, String time2) {
        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(time1));
            c2.setTime(df.parse(time2));
        } catch (ParseException e) {
            System.err.println("格式不正确");
        }
        int result = c1.compareTo(c2);
        if (result == 0)
            System.out.println("c1相等c2");
        else if (result < 0)
            System.out.println("c1小于c2");
        else
            System.out.println("c1大于c2");

        return result;
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * @param pattern 时间格式
     * @return 返回时间的字符串
     */

    public static String getNowTime(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String result = dateFormat.format(new Date(System.currentTimeMillis()));
        return result;
    }

    /**
     * @return 将现在时间转成时间戳
     */
    public static String getNowTimeStamps() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String result = dateFormat.format(new Date(System.currentTimeMillis()));
        return result;
    }

    /**
     * @param time
     * @param patten yyyy/MM/dd HH:mm:ss
     * @return 将时间戳转成时间
     */
    public static String StampstoTime(String time, String patten) {
        String t = new SimpleDateFormat(patten).format(new Date(Long.parseLong(time) * 1000L));
        return t;
    }

    public static String TimeStamp2Date(String timestampString, String formats) {
        if (TextUtils.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }


    public static final SimpleDateFormat FORMAT_TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    /********* 单位：毫秒 ********/

    private static final long ONE_MINUTE = 60000L; // 一分钟
    private static final long ONE_HOUR = 3600000L; // 一小时
    private static final long ONE_DAY = 86400000L; // 一天
    private static final long ONE_MONTH = 2592000000L; // 一个月（按30天计算）
    private static final long ONE_YEAR = 31104000000L; // 一年

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    /**
     * 获取刚刚、n秒前、n分钟前、n小时前、昨天、前天、n天前、n月前、n年前
     *
     * @param dateStr 日期字符串
     * @return 格式化后串
     * @throws ParseException
     */
    public static String format(String dateStr) throws ParseException {
        Date date;
        if (dateStr.contains(" ")) {
            date = FORMAT_TIMESTAMP.parse(dateStr);
        } else {
            date = FORMAT_DATE.parse(dateStr);
        }
        long delta = new Date().getTime() - date.getTime();
        if (delta < ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return seconds <= 0 ? "刚刚" : seconds + ONE_SECOND_AGO;
        }
        if (delta < ONE_HOUR) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < ONE_DAY) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 2L * ONE_DAY) {
            return "昨天";
        }
        if (delta < 3L * ONE_DAY) {
            return "前天";
        }
        if (delta < ONE_MONTH) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < ONE_YEAR) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 12L;
    }


    /**
     * @param s // 打印输出
     */
    public static void outPrint(String s) {
        System.out.println("输出信息:" + s);
    }

    /**
     * @param time 完整时间    //如"2015-11-20 14:38:18"
     * @param type 是否拼接带有标示的
     * @return 返回年月日
     */
    public static String splitSpace(String time, String type) {

        String times[] = time.split(" ");
        if (!"".equals(times[0]) && null != times[0]) {
            String date[] = times[0].split("-");
            // String needtime = date[0] + date[1] + date[2]; 是返回年月日如 20160512
            //String needtime = date[0] + date[1] + date[2];
            //下面是返回带有type 的样式
            StringBuffer buffer = new StringBuffer();
            buffer.append(date[0]).append(type).append(date[1]).append(type).append(date[2]).append(type);
            return buffer.toString();
        }
        return "";
    }


    public static String getSpecifiedDayBefore(String specifiedDay, int days) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - days);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    public static String getSpecifiedDayAfter(String specifiedDay, int days) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + days);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    /**
     * @param ss     需要裁分的字符串
     * @param divide 裁分的标识
     * @return
     */
    public static String dividePart(String ss, String divide) {
        //java特殊符号转义
        String str = "";
        if ("+".equals(divide)) {
            str = "\\+";
        } else if ("|".equals(divide)) {
            str = "\\|";
        } else if ("*".equals(divide)) {
            str = "\\*";
        } else if (".".equals(divide)) {
            str = "\\.";
        } else {
            str = divide;
        }
        String buffer[] = ss.split(str);
        StringBuffer buffer1 = new StringBuffer("");
        for (int i = 0; i < buffer.length; i++) {
            buffer1.append(buffer[i]);
        }
        System.out.println(buffer1.toString());
        return buffer1.toString();
    }

    /**
     * @param ss          整体内容
     * @param needReplice 需要被替换的标识
     * @param type        替换的标识
     * @return 返回新的字符串
     */
    public static String repliceElementToAnotherElement(String ss, String needReplice, String type) {
        //java特殊符号转义
        String str = "";
        if ("+".equals(needReplice)) {
            str = "\\+";
        } else if ("|".equals(needReplice)) {
            str = "\\|";
        } else if ("*".equals(needReplice)) {
            str = "\\*";
        } else if (".".equals(needReplice)) {
            str = "\\.";
        } else {
            str = needReplice;
        }
        String buffer[] = ss.split(str);
        StringBuffer buffer1 = new StringBuffer("");
        for (int i = 0; i < buffer.length; i++) {
            buffer1.append(buffer[i]).append(type);
        }
        System.out.println(buffer1.toString());
        return buffer1.toString();
    }


    /**
     * @param array
     * @return 将String数组转String类型集合并返回
     */
    public static List<String> getArrayStringToList(String array[]) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < array.length; i++) {
            if (null != array[i] && !"".equals(array[i].trim())) {
                list.add(array[i].trim());
            }
        }
        return list;
    }


    /**
     * @param array
     * @return 将int数组转int类型集合并返回
     */
    public static List<Integer> getArrayIntToList(int array[]) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < array.length; i++) {
            if (!"".equals(array[i])) {
                list.add(array[i]);
            }
        }
        return list;
    }

    /**
     * @param list 装有String类型的集合
     * @return 将集合String拼接成字符串并返回
     */
    public static String getStringFromListSting(List<String> list) {
        String endString = "";
        if (list != null && list.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i)).append(",");
            }
            endString = sb.substring(0, sb.toString().length() - 1);
            System.out.println("endString" + endString);
        }
        return endString;
    }

    /**
     * @param list 装有Integer类型的集合
     * @return 将集合String拼接成字符串并返回
     */
    public static String getIntegerFromListSting(List<Integer> list) {
        String endString = "";
        if (list != null && list.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i)).append(",");
            }
            endString = sb.substring(0, sb.toString().length() - 1);
            System.out.println("endInteger" + endString);
        }
        return endString;
    }


    /**
     * @param list
     * @return 将字符数集合转数组
     */
    public static String[] getArrayToList(List<String> list) {
        String[] arrary = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arrary[i] = list.get(i);
        }
        return arrary;
    }

    /**
     * @param list
     * @return 去掉集合中 ""的新集合
     */
    public static List<String> splitSpaceList(List<String> list) {
        List<String> newList = new ArrayList<String>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (!"".equals(list.get(i))) {
                    newList.add(list.get(i));
                }
            }
        } else {
            System.out.println("集合为空");
        }
        return newList;
    }


    /**
     * @param list 装有Integer类型的集合
     * @return 将集合int拼接成字符串并返回
     */
    public static String getStringfromListInt(List<Integer> list, String bufftype) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(bufftype);
        }
        String endString = sb.substring(0, sb.toString().length() - 1);
        System.out.println("endString" + endString);
        return endString;
    }

    /**
     * @param arr 字符數組
     * @return 将原来数组中含有空的内容去掉赋予给新的数组(String 数组)并返回
     */
    public static String[] getNewStringArray(String arr[]) {
        int length = arr.length;
        //第一个循环只是为了确定新数组的长度
        for (int i = 0; i < arr.length; i++) {
            if ("".equals(arr[i])) {
                if (null == arr[i]) {
                    --length;
                }
            } else {
            }
        }
        //第二个是给新数组赋值
        String newArray[] = new String[length];
        for (int i = 0; i < arr.length; i++) {
            if (!"".equals(arr[i]) && null != arr[i]) {
                newArray[i] = arr[i];
            }
        }
        return newArray;
    }

    /**
     * @param arr int數組
     * @return //将原来数组中含有空的内容去掉赋予给新的数组(int类型 数组)并返回
     */
    public static int[] getNewIntArray(int arr[]) {
        int length = arr.length;
        //第一个循环只是为了确定新数组的长度
        for (int i = 0; i < arr.length; i++) {
            if (!"".equals(arr[i])) {
            } else {
                length--;
            }
        }
        //第二个是给新数组赋值
        int newArray[] = new int[length];
        for (int i = 0; i < arr.length; i++) {
            if (!"".equals(arr[i])) {
                newArray[i] = arr[i];
            }
        }
        return newArray;
    }

    /**
     * 这里使用LinkedHashMap 没有使用hashMap的原因是为了保证插入值的顺序是一直的
     *
     * @param map Map<Integer,String> map
     * @return 返回一个map中value的值的集合
     */
    public static List<String> forEachMapToList(LinkedHashMap<Integer, String> map) {
        List<String> newList = new ArrayList<String>();
        Iterator<Map.Entry<Integer, String>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, String> entry = entries.next();
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            newList.add(entry.getValue());
        }
        return newList;
    }

    /**
     * @param patten 判断是否有的字符
     * @param values 需要被判断的字符串
     * @return true 未不包含
     */
    public static boolean pattenKey(String patten, String values) {
        boolean flag = false;
        if (!"".equals(values) && null != values) {
            for (int i = 0; i < values.length(); i++) {
                try {
                    if (i == values.length() - 1) {
                        flag = true;
                        String str2 = values.substring(values.length() - 1, values.length());
                        if (patten.contains(str2)) {
                            return false;
                        }
                        return flag;
                    } else {
                        String str = values.substring(i, i + 1);
                        if (patten.contains(str)) {
                            return false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * @param patten 判断是否有的字符—>串
     * @param values 需要被判断的字符串
     * @return true 包含
     */
    public static boolean pattenKey(String patten, String values[]) {
        boolean flag = false;
        for (int j = 0; j < values.length; j++) {
            if (patten.contains(values[j])) {
                flag = true;
            }
        }
        return flag;
    }


    /**
     * @param num1 被除数
     * @param num2 除数
     * @param flag 是否乘100
     * @return 返回两个数相除后的数
     */
    public static String getRemainder(String num1, String num2, boolean flag) {
        double choice = Double.parseDouble(num1);
        double all = Double.parseDouble(num2);
        double point = 0;
        if (all > 0) {
            if (flag) {
                point = (choice / all) * 100;
            } else {
                point = (choice / all);
            }
        }
        DecimalFormat df = new DecimalFormat("########.00");
        System.out.println(point);  //科学计数法
        return df.format(point);
    }

    /**
     * @param number 需要被裁分的字符串
     * @param patter 裁分标识
     * @return 返回数组
     */
    public static String[] getfloor(String number, String patter) {
        if (null == number) {
            return new String[]{"0"};
        }
        String args[] = null;
        //java特殊符号转义
        String str = "";
        if (number.contains(patter)) {
            if ("+".equals(patter)) {
                str = "\\+";
            } else if ("|".equals(patter)) {
                str = "\\|";
            } else if ("*".equals(patter)) {
                str = "\\*";
            } else if (".".equals(patter)) {
                str = "\\.";
            } else {
                str = patter;
            }
            args = number.split(str);
        } else {
            args = new String[1];
            args[0] = number;
        }
        return args;
    }


    /**
     * 判断是不是json
     *
     * @param json
     * @return
     */
    public static boolean isJson(String json) {

        outPrint("responce" + json);

        if (justturn(json)) {
//            turnLogin();
        }
        boolean isjson = true;
        try {
            new JsonParser().parse(json);
            return isjson;
        } catch (JsonParseException e) {
            isjson = false;
            System.out.println("bad json: " + json);
            return isjson;
        }
    }

    public static boolean isJson(String json, String tag) {
        outPrint(tag + json);

        if (justturn(json)) {
//            turnLogin();
        }

        boolean isjson = true;
        try {
            new JsonParser().parse(json);
            return isjson;
        } catch (JsonParseException e) {
            isjson = false;
            System.out.println("bad json: " + json);
            return isjson;
        }
    }
//    private static void turnLogin(){
//        Intent intent = new Intent(App.getInstance(),LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        SPStorage spStorage  = new SPStorage(App.getInstance());
//        spStorage.clearUserInfo();
//        App.getInstance().startActivity(intent);
//        App.closeAllActivity();
//    }

    private static boolean justturn(String json) {
        /*  {"code":220,"mark":"登录状态无效或者已过期！","time":1513764806}*/
        try {
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(json);
            int code = jsonObject.getInteger("code");
            if (code == 220) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * @param jsonString json字符串
     * @return 将json字符串转换成map的集合
     */
    public static Map getMap4Json(String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator keyIter = jsonObject.keys();
        String key;
        Object value;
        Map valueMap = new HashMap();
        while (keyIter.hasNext()) {
            key = (String) keyIter.next();
            try {
                value = jsonObject.get(key);
                valueMap.put(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return valueMap;
    }

    /**
     * @param number 保留小数点后两位
     * @return
     */

    public static String getNewdatafordot(Double number) {
        String st = "0.0";
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            st = df.format(number);
        } catch (Exception e) {
            st = "0.0";
        }
        return st;
    }

    public static String getNewdatafordot(String number) {
        DecimalFormat df = new DecimalFormat("0.00");
        String st = df.format(Double.parseDouble(number));
        return st;
    }

    public static String getNewdatafordotforpatten(String number) {
        String needStr = "";
        if (number.contains(".")) {
            String money[] = number.split("\\.");
            needStr = money[0];
        } else {
            needStr = number;
        }

        return needStr;
    }

    /**
     * @return 返回中间部分被隐藏的字符串
     */
    public static String getPhoneHide(String number) {
        StringBuffer buffer = new StringBuffer("");
        if (number.length() > 10) {

            for (int i = 0; i < number.length(); i++) {
                if (i > 2 && i < 7) {
                    buffer.append("*");
                    continue;
                }
                buffer.append(number.charAt(i));

            }
        }
        System.out.println(buffer.toString());
        return buffer.toString();
    }

    /**
     * @return 只返回后面部分显示的
     */
    public static String getPhoneCenterHide(String number) {
        StringBuffer buffer = new StringBuffer("");
        if (number.length() > 10) {
            for (int i = 0; i < number.length(); i++) {
                if (i >= 0 && i < 7) {
                    buffer.append("*");
                    continue;
                }
                buffer.append(number.charAt(i));
            }
        }
        System.out.println(buffer.toString());
        return buffer.toString();
    }


    /**
     * @param name1
     * @param name2
     * @return 始终返回两个长度相等的字符串
     */
    public static String[] getLengAppend(String name1, String name2) {
        int name1lengt = name1.length();
        int name2lengt = name2.length();
        StringBuffer stringBuffer = null;
        int less = name1lengt - name2lengt;
        if (less > 0) {
            stringBuffer = new StringBuffer(name2);
            for (int i = 0; i < less; i++) {
                stringBuffer.append(" ");
            }
            name2 = stringBuffer.toString();
        } else {
            stringBuffer = new StringBuffer(name1);
            for (int i = 0; i < Math.abs(less); i++) {
                stringBuffer.append(" ");
            }
            name1 = stringBuffer.toString();
        }
        String[] info = new String[2];
        info[0] = name1;
        info[1] = name2;
        return info;
    }

    /**
     * @param str 判断一个字符串是不是村字母
     * @return
     */
    public static boolean justisAz(String str) {
        String reg = "^[a-zA-Z]*$";
        boolean isCract = str.matches(reg);
        return isCract;
    }

    public static boolean justisAz1(String str) {
        String reg = "^[a-zA-Z\\s]*$";
        boolean isCract = str.matches(reg);
        return isCract;
    }

    /**
     * @param str 判断一个字符串是不是村数字
     * @return
     */
    public static boolean justNumber(String str) {
        String reg = "^[0-9\\.]*$";
        boolean isCract = str.matches(reg);
        return isCract;
    }

    /**
     * @param str 判断一个字符串是不是只有数字 字母和/
     * @return
     */
    public static boolean justNumAz(String str) {
        String reg = "^[A-Za-z0-9/]+$";
        boolean isCract = str.matches(reg);
        return isCract;
    }


    /**
     * @param str           需要裁分的字符串
     * @param startposition 裁分的开始位置
     * @param endposition   裁分的结束位置
     * @return 返回裁分前和裁分后的数组content[0] 为第一段内容 content[1] 为第二段内容
     */

    public static String[] getDivStringArrary(String str, int startposition, int endposition) {
        String[] content = new String[2];
        String ct1 = str.substring(startposition, endposition);
        String ct2 = str.substring(endposition, str.length());
        content[0] = ct1;
        content[1] = ct2;
        return content;
    }


    public static boolean getdotHowleng(String str) {
/*        String reg= "^[0-9]+(.[0-9]{4})?$";
        boolean isCract = str.matches(reg);
        return isCract;*/

        boolean isCract = false;
        if (null != str) {
            if (str.contains(".")) {
                String arg[] = str.split("\\.");
                if (arg[1].length() <= 4) {
                    isCract = true;
                }
            } else {
                isCract = true;
            }
        }
        return isCract;
    }

    /**
     * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
     *
     * @param value
     * @return Sting
     */
    public static String formatFloatNumber(double value) {
        if (value != 0.00) {
            DecimalFormat df = new DecimalFormat("########.00");
            String result = df.format(value);
            if (result.startsWith(".")) {
                result = "0" + result;
            }
            return result;
        } else {
            return "0.00";
        }
    }

    public static String formatFloatNumber(Double value) {
        if (value != null) {
            if (value.doubleValue() != 0.00) {
                DecimalFormat df = new DecimalFormat("########.00");
                return df.format(value.doubleValue());
            } else {
                return "0.00";
            }
        }
        return "";
    }

    /**
     * md5加密方法
     *
     * @param password
     * @return
     */
    public static String md5Password(String password) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把没一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }


    public static Bitmap getInternetPicture(final Context context, final String UrlPath) {
        Bitmap bm = null;
        // 1、确定网址
        // http://pic39.nipic.com/20140226/18071023_164300608000_2.jpg
        String urlpath = UrlPath;
        // 2、获取Uri
        try {
            URL uri = new URL(urlpath);
            // 3、获取连接对象、此时还没有建立连接
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            // 4、初始化连接对象
            // 设置请求的方法，注意大写
            connection.setRequestMethod("GET");
            // 读取超时
            connection.setReadTimeout(5000);
            // 设置连接超时
            connection.setConnectTimeout(5000);
            // 5、建立连接
            connection.connect();
            // 6、获取成功判断,获取响应码
            if (connection.getResponseCode() == 200) {
                // 7、拿到服务器返回的流，客户端请求的数据，就保存在流当中
                InputStream is = connection.getInputStream();
                // 8、开启文件输出流，把读取到的字节写到本地缓存文件
                File file = new File(context.getCacheDir(), getFileName(urlpath));
                FileOutputStream fos = new FileOutputStream(file);
                int len = 0;
                byte[] b = new byte[1024];
                while ((len = is.read(b)) != -1) {
                    fos.write(b, 0, len);
                }
                fos.close();
                is.close();
                //9、 通过图片绝对路径，创建Bitmap对象
                bm = BitmapFactory.decodeFile(file.getAbsolutePath());

                Log.i("", "网络请求成功");

            } else {
                Log.v("tag", "网络请求失败");
                bm = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static String getFileName(String path) {
        int index = path.lastIndexOf("/");
        return path.substring(index + 1);
    }

}
