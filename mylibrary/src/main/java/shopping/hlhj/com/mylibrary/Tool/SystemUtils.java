package shopping.hlhj.com.mylibrary.Tool;

/**
 * Created by zl on 2016/3/5.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemUtils {

    //判断是不是小米手机
    public static final String TAG="PhoneIsMiUi";
    public static String getSystemProperty(String propName){
        String line;
        BufferedReader input = null;
        try
        {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        }
        catch (IOException ex)
        {
            Log.e("", "Unable to read sysprop " + propName, ex);
            return null;
        }
        finally
        {
            if(input != null)
            {
                try
                {
                    input.close();
                }
                catch (IOException e)
                {
                    Log.e(TAG, "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }


    /**
     * 获取手机信息
     */
    public static String changshang="";
    public static String pingpai="";
    public static String phonexinhao="";
    public static String phoneid="";
    public static String phoneImsi="";
    public static String serviceNick="";
    public static String phonenumer="";
    public static void getPhoneInfo(Context context)
    {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String chanshang=android.os.Build.MANUFACTURER;
        String mtyb = android.os.Build.BRAND;// 手机品牌
        String mtype = android.os.Build.MODEL; // 手机型号
        @SuppressLint("MissingPermission") String imei = tm.getDeviceId();
        @SuppressLint("MissingPermission") String imsi = tm.getSubscriberId();
        @SuppressLint("MissingPermission") String numer = tm.getLine1Number(); // 手机号码
        String serviceName = tm.getSimOperatorName(); // 运营商
        changshang=chanshang;
        pingpai=mtyb;
        phonexinhao=mtype;
        phoneid=imei;
        phoneImsi=imsi;
        serviceNick=serviceName;
        phonenumer=numer;
    }



    /**
     * 获取手机内存大小
     *
     * @return
     */
    private String getTotalMemory(Context context)
    {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try
        {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString)
            {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        }
        catch (IOException e)
        {
        }
        return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

/*
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    public static boolean isMIUI() {
    //获取缓存状态
   if(SPUtils.getInstance().getCacheDataSP().contains("isMIUI"))
    {  return SPUtils.getInstance().getCacheDataSP().getBoolean("isMIUI",false);  }
    Properties prop= new Properties();  boolean isMIUI;  try {  prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));  } catch (IOException e)  {  e.printStackTrace();  return false;  }  isMIUI= prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;  SPUtils.getInstance().putCacheData("isMIUI",isMIUI);
    // 保存是否MIUI
    return isMIUI;  }
*/

}
