package shopping.hlhj.com.mylibrary.Tool;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author zzh
 */
public class AndroidUtils {

    /**
     * 获取屏幕的宽和高
     *
     * @param context
     * @return 返回宽高数组 [0]:width [1]:高度
     */
    public static int[] getScreenWidth(Context context) {
        WindowManager manager = ((Activity) context).getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

//    /**
//     * ImageView 控件
//     */
//    public static void drowImage(String uri, ImageView image) {
//        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
//        DisplayImageOptions options = builder.showImageForEmptyUri(null)
//                .showImageOnFail(null).showImageOnLoading(null)
//                .cacheInMemory(true).cacheOnDisk(true).build();
//        PicUtil.displayImage(uri, image, options);
//    }
//
//    /**
//     * ImageView 控件 可以传指定默认图片
//     */
//    public static void drowImage2(String uri, ImageView image, int imageid) {
//        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
//        DisplayImageOptions options = builder.showImageForEmptyUri(imageid)
//                .showImageOnFail(imageid).showImageOnLoading(imageid)
//                .cacheInMemory(true).cacheOnDisk(true).build();
//        PicUtil.displayImage(uri, image, options);
//    }
//
//    /**
//     * 绘制本地文件图片
//     */
//    public static void drowLocationImage(String uri, ImageView image) {
//        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
//        DisplayImageOptions options = builder.showImageForEmptyUri(R.drawable.bgphones)
//                .showImageOnFail(R.drawable.bgphones).showImageOnLoading(R.drawable.bgphones)
//                .cacheInMemory(true).cacheOnDisk(true).build();
//        PicUtil.displayImage("file://" + uri, image, options);
//    }

    /**
     * ImageView 控件 可以传指定默认图片
     *
     * @param image   控件
     * @param imageid 指定图片id
     */

    public static void drowImageNotUrl(ImageView image, int imageid) {
        image.setBackgroundResource(imageid);
    }


    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param context
     * @return 图片的uri
     */
    public static Uri createImagePathUri(Context context) {
        Uri imageFilePath = null;
        String status = Environment.getExternalStorageState();
        SimpleDateFormat timeFormatter = new SimpleDateFormat(
                "yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));
        // ContentValues是我们希望这条记录被创建时包含的数据信息
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
        values.put(MediaStore.Images.Media.DATE_TAKEN, time);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
            imageFilePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            imageFilePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        }
        System.out.print("生成的照片输出路径：" + imageFilePath.toString());
        return imageFilePath;
    }



    /**
     * 获取当前时间:yyyy年MM月dd日
     */

    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取当前时间:yyyyMMddHHmmss
     *
     * @return
     */
    public static String getTimes() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }


    //居中显示的toast
    public static Toast toast;

    public static void showcenterToast(Context context, String message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void showcenterToast(Context context, String message, int time) {
        if (null == toast) {
            if(time>0){
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            }else{
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    public static void hideToast() {
        if (null != toast) {
            toast.cancel();
        }
    }

    public static void showBottenToast(Context context, String message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    // 从资源中获取Bitmap
    public static Bitmap getBitmapFromResources(Activity act, int resId) {
        Resources res = act.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }

    // byte[] → Bitmap
    public static Bitmap convertBytes2Bimap(byte[] b) {
        if (b.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    // Bitmap → byte[]
    public static byte[] convertBitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // 1)Drawable → Bitmap
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(

                drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight(),

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        // canvas.setBitmap(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;
    }

    // 2)Drawable → Bitmap
    public static Bitmap convertDrawable2BitmapSimple(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    // Bitmap → Drawable
    public static Drawable convertBitmap2Drawable(Bitmap bitmap) {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
// 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
        return bd;
    }


    public static Bitmap returnBitMap(String url) {
        JavaUtils.outPrint("returnBitMap" + url);
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }



    //根据资源id获取图片byte
    public static byte[] img(Context context, int id) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) context.getResources().getDrawable(id)).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        System.out.println("图片流：" + baos.toByteArray());
        return baos.toByteArray();
    }


    //重新设置Bitmap的宽和高
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高 b
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    //获取控件在屏幕上的坐标
    public static int[] getViewWhereOnScreen(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        return location;
    }


    /**
     * 获取editext文本的长度
     * @param editText
     * @return
     */
    public static int getEditTextLength(EditText editText){
        if(null!=editText){
          String string = editText.getText()+"";
            if(string.length()>0){
                return string.length();
            }
        }
        return 0;
    }

    /**
     * 获取版本
     *
     * @param context
     * @return app版本
     */
    public static String getAppVersion(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    /**
     * 验证手机号
     *
     * @param @param mobiles
     * @return boolean
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-3,5-9]|(17[0-9])))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 保存数据到sp
     *
     * @param @param context 上下文对象
     * @param @param key 键
     * @param @param value 值
     */
    public static void savePreference(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).commit();
    }

    /**
     * 从sp获取数据
     *
     * @param @param  context 上下文对象
     * @param @param  key 键
     * @param @return
     */
    public static String getpreference(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
    }

    /**
     * 从sp删除数据
     *
     * @param @param context 上下文对象
     * @param @param key 键
     * @Title: getpreference
     */
    public static boolean deltepreference(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit().remove(key).commit();
    }


    /**
     * 发表文章几小时前之类
     *
     * @param time
     * @return
     */
    public static String toTureTime(String time, String patten) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
        String result = dateFormat.format(new Date(System.currentTimeMillis()));
        String now = result;
        int artilcYear = Integer.parseInt(time.substring(0, 4));
        int artilcMonth = Integer.parseInt(time.substring(5, 7));
        int artilcDay = Integer.parseInt(time.substring(8, 10));
        int artilcHours = Integer.parseInt(time.substring(11, 13));
        int artilcMin = Integer.parseInt(time.substring(14, 16));
        int artilcSec = Integer.parseInt(time.substring(17, time.length()));

        int nowYear = Integer.parseInt(now.substring(0, 4));
        int nowMonth = Integer.parseInt(now.substring(5, 7));
        int nowDay = Integer.parseInt(now.substring(8, 10));
        int nowHours = Integer.parseInt(now.substring(11, 13));
        int nowMin = Integer.parseInt(now.substring(14, 16));
        int nowSec = Integer.parseInt(now.substring(17, time.length()));
        StringBuilder sb = new StringBuilder();

        if (nowYear - artilcYear > 0) {
            sb.append(artilcYear).append("-").append(artilcMonth).append("-").append(artilcDay).append("");
            return sb.toString();
        } else if (nowMonth - artilcMonth > 0) {
            sb.append(artilcMonth).append("-").append(artilcDay).append("");
            return sb.toString();
        } else if (nowDay - artilcDay == 0) {
            if (nowHours - artilcHours == 0) {
                if (nowMin - artilcMin <= 1) {
                    sb.append("刚刚");
                    return sb.toString();
                } else {
                    sb.append(nowMin - artilcMin).append("分钟前");
                    return sb.toString();
                }
            } else {
                if (nowHours - artilcHours < 0) {
                    int tt = nowHours - artilcHours;
                    tt = Math.abs(tt);
                    sb.append("" + tt).append("小时前");
                } else {
                    sb.append(nowHours - artilcHours).append("小时前");
                }
                return sb.toString();
            }
        } else if (nowDay - artilcDay == 1) {
            sb.append("昨天");
            return sb.toString();
        } else if (nowDay - artilcDay == 2) {
            sb.append("前天");
            return sb.toString();
        } else {
            sb.append(artilcMonth).append("-").append(artilcDay).append("");
            return sb.toString();
        }
    }

    /**
     * 清楚sd卡上所有的缓存
     *
     * @param context
     */

    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 设置粗体文字
     * @param context  上下文
     * @param textView texview
     * @param content  文字
     * @param startPosition 文字的开始位置
     * @param endPosition   位置的结束位置
     */

    public static void setBlodTextColor(Context context, final TextView textView, String content, int startPosition, int endPosition) {
        SpannableString spanString = new SpannableString(content);
        //再构造一个改变字体颜色的Span
        spanString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startPosition, endPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //spanString.setSpan(span, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给view显示出来
        textView.setText(spanString);
    }

    /**
     * @param context  上下文
     * @param textView texview
     * @param content  文字
     * @param startPosition 文字的开始位置
     * @param endPosition   位置的结束位置
     * @param color   文字的颜色
     */

    public static void setTextColor(Context context, final TextView textView, String content, int startPosition, int endPosition, String color) {
        SpannableString spanString = new SpannableString(content);
        //再构造一个改变字体颜色的Span
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
        //将这个Span应用于指定范围的字体
        spanString.setSpan(span, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给view显示出来
        textView.setText(spanString);
    }

    /**
     * @param context  上下文
     * @param edittext edittext
     * @param content  文字
     * @param startPosition 文字的开始位置
     * @param endPosition   位置的结束位置
     * @param color   文字的颜色
     */
    public static void setTextColor(Context context, final EditText edittext, String content, int startPosition, int endPosition, String color) {
        SpannableString spanString = new SpannableString(content);
        //再构造一个改变字体颜色的Span
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
        //将这个Span应用于指定范围的字体
        spanString.setSpan(span, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给view显示出来
        edittext.setHint(spanString);
    }

    /**
     * @param context  上下文
     * @param textView textView
     * @param content  文字
     * @param color   文字的颜色
     * @param colorTextArray   需要被标色的文字的数组
     */
    public static void setTextColor(Context context, final TextView textView, String content, String colorTextArray[], String color) {
        SpannableString spanString = new SpannableString(content);
        //将这个Span应用于指定范围的字体
        if(colorTextArray!=null &&colorTextArray.length>0){
            for(int i=0;i<colorTextArray.length;i++){
                ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
                String temtext=colorTextArray[i];
                int currentposition=content.indexOf(temtext);
                spanString.setSpan(span, currentposition, currentposition+temtext.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        //设置给view显示出来
        textView.setText(spanString);
    }
    public static void setTextColor1(Context context, final TextView textView, String content, String colorTextArray[], String color) {
        SpannableString spanString = new SpannableString(content);
        //将这个Span应用于指定范围的字体
        if(colorTextArray!=null &&colorTextArray.length>0){
            for(int i=0;i<colorTextArray.length;i++){
                ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
                String temtext=colorTextArray[i];
                int currentposition=content.indexOf(temtext);
                spanString.setSpan(span, currentposition, currentposition+temtext.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        //设置给view显示出来
        textView.setText(spanString);
    }



    /**
     * @param context  上下文
     * @param textView textView
     * @param content  文字
     * @param color   文字的颜色
     * @param colorTextArray   需要被标色的文字的数组
     */
    public static void setRadioButtonTextColor(Context context, final RadioButton textView, String content, String colorTextArray[], String color) {
        SpannableString spanString = new SpannableString(content);
        //将这个Span应用于指定范围的字体
        if(colorTextArray!=null &&colorTextArray.length>0){
            for(int i=0;i<colorTextArray.length;i++){
                ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
                String temtext=colorTextArray[i];
                int currentposition=content.indexOf(temtext);
                spanString.setSpan(span, currentposition, currentposition+temtext.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        //设置给view显示出来
        textView.setText(spanString);
    }


    /**
     * @param context  上下文
     * @param textView textView
     * @param content  文字
     * @param color   文字的颜色
     * @param colorTextArray   需要被标色的文字的数组
     */
    public static void setCheckBoxTextColor(Context context, final CheckBox textView, String content, String colorTextArray[], String color) {
        SpannableString spanString = new SpannableString(content);
        //将这个Span应用于指定范围的字体
        if(colorTextArray!=null &&colorTextArray.length>0){
            for(int i=0;i<colorTextArray.length;i++){
                ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
                String temtext=colorTextArray[i];
                int currentposition=content.indexOf(temtext);
                spanString.setSpan(span, currentposition, currentposition+temtext.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        //设置给view显示出来
        textView.setText(spanString);
    }

    /**
     * @param context  上下文
     * @param edittext edittext
     * @param content  文字
     * @param color   文字的颜色
     * @param colorTextArray   需要被标色的文字的数组
     */
    public static void setTextColor(Context context, final EditText edittext, String content, String colorTextArray[], String color) {
        SpannableString spanString = new SpannableString(content);
        //将这个Span应用于指定范围的字体
        if(colorTextArray!=null &&colorTextArray.length>0){
            for(int i=0;i<colorTextArray.length;i++){
                ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
                String temtext=colorTextArray[i];
                int currentposition=content.indexOf(temtext);
                spanString.setSpan(span, currentposition, currentposition+temtext.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        //设置给view显示出来
        edittext.setText(spanString);
    }

    /**
     * 可对字体响应点击事件
     * @param context  上下文
     * @param content  文字
     */
    public static void setTextColorAndClick(final Context context, final TextView textView, String content, int startPosition, int endPosition, final String colors) {
        SpannableString spanString = new SpannableString(content);
        //再构造一个改变字体颜色的Span
//        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#F06AA3"));
        //将这个Span应用于指定范围的字体
        spanString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor(colors));//设置文本颜色
                ds.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                AndroidUtils.showBottenToast(context, "你点到我了");
            }
        }, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给view显示出来
        textView.setText(spanString);
        textView.setHighlightColor(Color.TRANSPARENT);//去掉点击后出现的高亮显示
        textView.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }


    public static void setTextColorAndClick(final Context context, final TextView textView, String content, String colorTextArray[], final String colors) {
        SpannableString spanString = new SpannableString(content);

        for(int i=0;i<colorTextArray.length;i++){
            //将这个Span应用于指定范围的字体
            spanString.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor(colors));//设置文本颜色
                    ds.setUnderlineText(false);      //设置下划线
                }
                @Override
                public void onClick(View widget) {
                    AndroidUtils.showBottenToast(context, "你点到我了");
                }
            }, 0, colorTextArray[i].length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        //设置给view显示出来
        textView.setText(spanString);
        textView.setHighlightColor(Color.TRANSPARENT);//去掉点击后出现的高亮显示
        textView.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }


    public int getCurentdip(Context context){
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics());
        return dip;
    }



    //根据路径将文件转换成字节流
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    // 根据byte数组，生成文件
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
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

    // compressImage下面两个都是压缩图片方法 返回Bitmap对象
    public static Bitmap compressImage(String picpath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(picpath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 600f;// 这里设置高度为800f
        float ww = 360f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(picpath, newOpts);
        return compressImage(bitmap);
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 60, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 60;
        while (baos.toByteArray().length / 1024 > 30) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    /**
     * uri转绝对路径
     *
     * @param context
     * @param uri
     */
    public static String uriToPath(Context context, Uri uri) {
        // can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = ((Activity) context).managedQuery(uri, proj, // Which
                // columns
                // to
                // return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else {
            return uri.getPath();// 如果游标为空说明获取的已经是绝对路径了
        }
    }



    /**
     * @param fromFile,图片文件路径
     * @param bitmap          图片对象
     * @param width，height    quality，
     * @toFile 存储地址
     */

    public static Bitmap ZipBitmap(String fromFile, Bitmap bitmap, String toFile, int width, int height, int quality) {
        Bitmap resizeBitmap = null;
        try {
//            Bitmap bitmap = BitmapFactory.decodeFile(fromFile);
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();
            // 缩放图片的尺寸
            float scaleWidth = (float) width / bitmapWidth;
            float scaleHeight = (float) height / bitmapHeight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 产生缩放后的Bitmap对象
            resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
            // save file
            File myCaptureFile = new File(toFile);
            FileOutputStream out = new FileOutputStream(myCaptureFile);
            if (resizeBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                out.flush();
                out.close();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();//记得释放资源，否则会内存溢出
            }
            if (!resizeBitmap.isRecycled()) {
                resizeBitmap.recycle();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resizeBitmap;
    }

    /**
     * @param context 上下文
     *  @return isOpen若返回true，则表示输入法打开
     */
    public static boolean InputMethodManagerStatus(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        return isOpen;
    }

    /**
     * 获取当前版本号
     *
     * @return String
     */
    public static String getVersionName(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    /**
     *  关闭输入法
     * @param context
     * @param activity 当前的activity
     */
    public static void closeInputMethodManagerStatus(Context context, Activity activity){
        ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /**
     * 隐藏软键盘
     * @param @param context
     */
    public static void hiddenKeyBoard(Context context) {
        final View v = ((Activity) context).getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
    /**
     * 显示软键盘 用线程才能开启
     * @param editText
     */
    public static void showKeyBoard(final EditText editText) {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    InputMethodManager inputManager = (InputMethodManager) editText
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(editText, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

//    public static void showKeyBoard2(final GridPasswordView editText) {
////        new Thread() {
////            public void run() {
////                try {
////                    Thread.sleep(200);
////                    InputMethodManager inputManager = (InputMethodManager) editText
////                            .getContext().getSystemService(
////                                    Context.INPUT_METHOD_SERVICE);
////                    inputManager.showSoftInput(editText, 0);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////            }
////        }.start();
////    }
    public static void showKeyBoard3(final View view) {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    InputMethodManager inputManager = (InputMethodManager) view
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(view, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }



    /**
     * 读取图片的旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm
     *            需要旋转的图片
     * @param degree
     *            旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     *判断是否锁屏
     */
    public static boolean isCloseSrceen(Context context){
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean locked = km.inKeyguardRestrictedInputMode();
        return locked;
    }
    //转换dip为px
    public static int convertDipOrPx(Context context, int dip) {

        float scale = context.getResources().getDisplayMetrics().density;

        return (int)(dip*scale + 0.5f*(dip>=0?1:-1));

    }

   //转换px为dip
    public static int convertPxOrDip(Context context, int px) {

        float scale = context.getResources().getDisplayMetrics().density;

        return (int)(px/scale + 0.5f*(px>=0?1:-1));

    }

    //获取当前屏幕密度
    public static int getcurrentDimens(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        return mDensity;
    }
    public static View getView(Context context, int viewid) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(viewid,null);
    }
}
