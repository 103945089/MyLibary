package shopping.hlhj.com.mylibrary.Tool;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class Utils {

	private static final String FILENAME = "FILENAME_FILENAME";// sp保存对象的不变常量

	/** 用于完成录音 */
	public static MediaRecorder mRecorder = null;


	/**
	 * 判断email格式是否正确
	 * 
	 * @Title: isEmail
	 * @Description: TODO
	 * @param @param email
	 * @param @return
	 * @return boolean
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 验证手机号
	 * 
	 * @Title: isMobileNO
	 * @Description: TODO
	 * @param @param mobiles
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 用来判断服务是否运行.
	 * 
	 * @param mContext
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/**
	 * 
	 * @Title: getDate
	 * @Description: TODO 裁剪时间字符串
	 * @param @param createdate
	 * @param @return
	 * @return String
	 */
	public static String getDate(String createdate) {
		if (!"".equals(createdate) && createdate.length() > 11) {
			if (createdate.indexOf("T") != -1) {
				return createdate.substring(0, createdate.indexOf("T"));
			}
		}
		return "";
	}

	/**
	 * 
	 * @Title: getDate
	 * @Description: TODO 裁剪时间字符串
	 * @param @param createdate
	 * @param @return
	 * @return String
	 */
	public static String getallDate(String createdate) {
		if (!"".equals(createdate) && createdate.length() > 11) {
			if (createdate.indexOf("T") != -1) {
				return createdate.substring(0, createdate.indexOf("T"))
						+ " "
						+ createdate.substring(createdate.indexOf("T") + 1,
								createdate.lastIndexOf(":"));
			}
		}
		return "";
	}

	public static int getWeek(String createdate) {
		if (!"".equals(createdate) && createdate.length() > 11) {
			if (createdate.indexOf("T") != -1) {
				String sdate = createdate.substring(0, createdate.indexOf("T"));
				Date date = Date.valueOf(sdate);
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				return c.get(Calendar.DAY_OF_WEEK);
			}
		}
		return -1;
	}

	public static String getTime(String createdate) throws ParseException {
		if (!"".equals(createdate) && createdate.length() > 11) {
			if (createdate.indexOf("T") != -1) {
				String sdate = createdate.replace("T", " ");
				SimpleDateFormat dfd = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat df = new SimpleDateFormat("HH:mm");
				// String d = dfd.format(sdate);
				return df.format(dfd.parse(sdate));
			}
		}
		return "";
	}

	public static boolean isMorning(String stime) {
		if (null != stime && "".equals(stime)) {
			Time time = new Time(stime);
			if (time.hour > 12) {
				return false;
			}
		}
		return true;
	}

	/** 开始录音 */
	public static String startVoice(Context context) {
		String path = "/wzschool/xmppaudio/" + System.currentTimeMillis()
				+ ".amr";
		// 设置录音保存路径
		try {
			String mFileName = Environment.getExternalStorageDirectory() + path;
			String state = Environment.getExternalStorageState();
			if (!state.equals(Environment.MEDIA_MOUNTED)) {
				return "";
			}
			File directory = new File(mFileName).getParentFile();
			if (!directory.exists() && !directory.mkdirs()) {
				return "";
			}
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
			mRecorder.setOutputFile(mFileName);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			try {
				mRecorder.prepare();
			} catch (IOException e) {
			}
			mRecorder.start();
		} catch (Exception e) {
			return "";
		}
		return path;
	}

	/** 停止录音 */
	public static long stopVoice(long time) {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
		return System.currentTimeMillis() - time;
	}

	/**
	 * 将文件转成base64 字符串
	 * 
	 * @param path
	 *            文件路径
	 * @return *
	 * @throws Exception
	 */

	public static String encodeBase64File(String path) throws Exception {
		try {
			File file = new File(path);
			FileInputStream inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			return Base64.encodeToString(buffer, Base64.DEFAULT);
		} catch (OutOfMemoryError error) {
			System.out.println("==============errorerror=============="
					+ error.toString());
		}
		return "";
	}

	/**
	 * 将base64字符解码保存文件
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */

	public static void decoderBase64File(String base64Code, String targetPath)
			throws Exception {
		File file = new File(targetPath).getParentFile();
		if (!file.exists()) {
			file.mkdirs();
		}
		byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}

	/**
	 * 判读一个文件是否是图片
	 * 
	 * @Title: isIMG
	 * @Description: TODO
	 * @param @return
	 * @return boolean
	 */
	public static boolean isIMG(String path) {
		if (!"".equals(path) && path.length() > 0
				&& path.lastIndexOf(".") != -1) {
			String type = path.substring(path.lastIndexOf(".") + 1,
					path.length()).toLowerCase();
			if (!"".equals(type)
					&& (type.equals("jpg") || type.equals("gif")
							|| type.equals("png") || type.equals("jpeg")
							|| type.equals("bmp") || type.equals("wbmp")
							|| type.equals("ico") || type.equals("jpe"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判读一个文件是否是音频文件
	 * 
	 * @Title: isAUDIO
	 * @Description: TODO
	 * @param @param path
	 * @param @return
	 * @return boolean
	 */
	public static boolean isAUDIO(String path) {
		if (!"".equals(path) && path.length() > 0
				&& path.lastIndexOf(".") != -1) {
			String type = path.substring(path.lastIndexOf(".") + 1,
					path.length()).toLowerCase();
			if (!"".equals(type) && (type.equals("mp3") || type.equals("aac"))) {
				return true;
			}
		}
		return false;
	}

	public static boolean isVIDEO(String path) {
		if (!"".equals(path) && path.length() > 0
				&& path.lastIndexOf(".") != -1) {
			String type = path.substring(path.lastIndexOf(".") + 1,
					path.length()).toLowerCase();
			if (!"".equals(type)
					&& (type.equals("mp4") || type.equals("3gp")
							|| type.equals("avi") || type.equals("rmvb") || type
								.equals("wmv"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 设置WebView
	 * 
	 * @Title: setWebView
	 * @Description: TODO
	 * @param @param context
	 * @param @param webView
	 * @return void
	 */
	public static void LoadWeb(Context context, WebView webView, String url) {
		setWebView(context, webView);
		webView.loadUrl(url);
	}

	/**
	 * 设置WebView
	 * 
	 * @Title: setWebView
	 * @Description: TODO
	 * @param @param context
	 * @param @param webView
	 * @return void
	 */
	public static void setWebView(final Context context, final WebView webView) {
		webView.setVisibility(View.VISIBLE);
		WebSettings webSettings = webView.getSettings();
		webSettings.setBlockNetworkImage(true);
		webSettings.setRenderPriority(RenderPriority.HIGH);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {

				view.getSettings().setBlockNetworkImage(false);

				super.onPageFinished(view, url);

			}
		});
	}

	/**
	 * 加载WebView
	 * 
	 * @Title: LoadWeb
	 * @Description: TODO
	 * @param context
	 *            上下文对象
	 * @param webView
	 *            加载内容的WebView
	 * @param content
	 *            内容
	 * @return void
	 */
	public static void loadcontentWeb(Context context, WebView webView,
                                      String content) {
		setWebView(context, webView);
		try {
			webView.loadDataWithBaseURL(
					"fake://not/needed",
					"<html><head><meta http-equiv='content-type' content='text/html;charset=utf-8'><style type=\"text/css\">img{ width:100%}</style><STYLE TYPE=\"text/css\"> BODY { margin:0; padding: 5px 3px 5px 5px; background-color:#ffffff;} </STYLE><BODY TOPMARGIN=5 rightMargin=0 MARGINWIDTH=0 MARGINHEIGHT=0></head><body>"
							+ new String(content.getBytes("utf-8"))
							+ "</body></html>", "text/html", "utf-8", "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

    public static void loadcontentWebNoPadding(Context context, WebView webView,
                                               String content) {
        setWebView(context, webView);
        try {
            webView.loadDataWithBaseURL(
                    "fake://not/needed",
                    "<html><head><meta http-equiv='content-type' content='text/html;charset=utf-8'><style type=\"text/css\">img{ width:100%}</style><STYLE TYPE=\"text/css\"> BODY { margin:0; padding: 0px 0px 0px 0px; background-color:#ffffff;} </STYLE><BODY TOPMARGIN=5 rightMargin=0 MARGINWIDTH=0 MARGINHEIGHT=0></head><body>"
                            + new String(content.getBytes("utf-8"))
                            + "</body></html>", "text/html", "utf-8", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * url 加载WebView
     * @param context 上下文对象
     * @param webView 加载内容的WebView
     * @param url  url地址
     */
    public static void loadWebByUrl(Context context, WebView webView, String url) {
        setWebView(context, webView);
        webView.loadUrl(url);
    }

	/**
	 * 获取wifi下本机的ip地址
	 * 
	 * @Title: getWifiIp
	 * @Description: TODO
	 * @param @param context
	 * @param @return
	 * @return String
	 */
	public static String getWifiIp(Context context) {
		String ip = "";
		// 获取wifi服务
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (wifiManager.isWifiEnabled()) {
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int i = wifiInfo.getIpAddress();
			ip = (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "."
					+ ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
		}
		return ip;
	}

	/**
	 * 获取当前版本号
	 * 
	 * @Title: getVersionName
	 * @Description: TODO
	 * @param @param context
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 */
	public static String getVersionName(Context context) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(
				context.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}

	public static int bytesToInt(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (bytes == null || bytes.length <= 0) {
			return 0;
		}
		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return Integer.parseInt(stringBuilder.toString(), 16);
	}

	/**
	 * 整数转成byte数组
	 * 
	 * @Title: intToBytes
	 * @Description: TODO
	 * @param @param value
	 * @param @param len
	 * @param @return
	 * @return byte[]
	 */
	public static int[] intToBytes(int value, int len) {
		int[] b = new int[len];
		String sl = Integer.toHexString(value);
		int sur = len * 2 - sl.length();
		for (int i = 0; i < sur; i++) {
			sl = "0" + sl;
		}
		for (int i = 0; i < b.length; i++) {
			b[i] = (Integer.parseInt(sl.substring(i * 2, (i + 1) * 2), 16));
		}
		return b;
	}

	/**
	 * 判断数字
	 * 
	 * @Title: isNumeric
	 * @Description: TODO
	 * @param @param str
	 * @param @return
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static String compareNowdate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
		try {
			Date nowdate = new Date(System.currentTimeMillis());
			if (getTwodateDay(date, sdf.format(nowdate)) == 0) {
				return getNoDayTime(date);
			} else if (getTwodateDay(date, sdf.format(nowdate)) == 1) {
				return "昨天\t" + getNoDayTime(date);
			} else {
				return sdf1.format(sdf.parse(date));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getNoDayTime(String date) {
		SimpleDateFormat formatBuilder = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatBuilder.format(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * @Title: getTwodateDay
	 * @Description: TODO
	 * @param @param date1
	 * @param @param date2
	 * @param @return
	 * @return long
	 */
	public static long getTwodateDay(String date1, String date2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return (sdf.parse(date2).getTime() - sdf.parse(date1).getTime())
					/ (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * 聊天的时候比较两个时间
	 * 
	 * @Title: compareTwoDate
	 * @Description: TODO
	 * @param @return
	 * @return String
	 */
	public static boolean compareTwoDate(String predate, String nextdate) {
		if ((getLongTime(nextdate) - getLongTime(predate)) < (5 * 60 * 1000)) {
			return false;
		}
		return true;
	}

	/**
	 * 把标准时间转换成时间戳
	 * 
	 * @Title: getTime
	 * @Description: TODO
	 * @param @param user_time
	 * @param @return
	 * @return String
	 */
	public static long getLongTime(String user_time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date d;
		try {
			d = sdf.parse(user_time);
			long l = d.getTime();
			return l;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * bitmap转base64位
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String imgToBase64(Bitmap bitmap) {
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			byte[] imgBytes = out.toByteArray();
			return Base64.encodeToString(imgBytes, Base64.DEFAULT);
		} catch (Exception e) {
			return null;
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		String imageName = timeFormatter.format(new java.util.Date(time));
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
		Log.i("", "生成的照片输出路径：" + imageFilePath.toString());
		return imageFilePath;
	}

	/**
	 * uri转绝对路径
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String uriToPath(Context context, Uri uri) {
		// can post image
		String[] proj = { MediaStore.Images.Media.DATA };
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
	 * 隐藏软键盘
	 * 
	 * @param @param context
	 * @return void
	 * @Title: HiddenInputPanel
	 * @Description: TODO
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
	 * 显示软键盘 //不线程的话键盘弹不出
	 * 
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

	// 获取手机IMEI码 .平板为null，就要取设备号
	public static String getPhoneUdid(Context mContext) {
		String ss = getPhoneIMEI(mContext);// 获取手机IMEI码
		// .我平板为null，就要取设备号
		if (null == ss)
			ss = Settings.Secure.getString(mContext.getContentResolver(),
					Settings.Secure.ANDROID_ID);
		return ss;
	}

	public static String getPhoneIMEI(Context cxt) {
		TelephonyManager tm = (TelephonyManager) cxt.getSystemService("phone");
		return tm.getDeviceId();
	}

	// 调用系统播放视频
	public static void openSystemVideo(Context context, String url) {
		Intent intent = new Intent(Intent.ACTION_VIEW); // 创建一个intent,
		intent.setType("video/*"); // 設置其type，这里使用*支持全部格式
		intent.setDataAndType(Uri.parse(url), "video/*"); // 设置播放路径，可以是本地地址，也可以是网络地址
		context.startActivity(intent); // 发送intent，启动播放器
	}

	/**
	 * 调用系统打电话
	 * 
	 * @param mContext
	 *            上下文
	 * @param tel
	 *            电话号码
	 */
	public static void openSystemCall(Context mContext, String tel) {
//		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		mContext.startActivity(intent);

        Intent intent=new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:"+tel));
        mContext.startActivity(intent);
	}

	/**
	 * desc:保存对象
	 * 
	 * @param context
	 * @param key
	 * @param obj
	 *            要保存的对象，只能保存实现了serializable的对象 modified:
	 */
	public static void saveObject(Context context, String key, Object obj) {
		try {
			// 保存对象
			SharedPreferences.Editor sharedata = context.getSharedPreferences(
					FILENAME, 0).edit();
			// 先将序列化结果写到byte缓存中，其实就分配一个内存空间
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			// 将对象序列化写入byte缓存
			os.writeObject(obj);
			// 将序列化的数据转为16进制保存
			String bytesToHexString = bytesToHexString(bos.toByteArray());
			// 保存该16进制数组
			sharedata.putString(key, bytesToHexString);
			sharedata.commit();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("", "保存obj失败");
		}
	}

	/**
	 * desc:将数组转为16进制
	 * 
	 * @param bArray
	 * @return modified:
	 */
	public static String bytesToHexString(byte[] bArray) {
		if (bArray == null) {
			return null;
		}
		if (bArray.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * desc:获取保存的Object对象
	 * 
	 * @param context
	 * @param key
	 * @return modified:
	 */
	public Object readObject(Context context, String key) {
		try {
			SharedPreferences sharedata = context.getSharedPreferences(
					FILENAME, 0);
			if (sharedata.contains(key)) {
				String string = sharedata.getString(key, "");
				if (TextUtils.isEmpty(string)) {
					return null;
				} else {
					// 将16进制的数据转为数组，准备反序列化
					byte[] stringToBytes = StringToBytes(string);
					ByteArrayInputStream bis = new ByteArrayInputStream(
							stringToBytes);
					ObjectInputStream is = new ObjectInputStream(bis);
					// 返回反序列化得到的对象
					Object readObject = is.readObject();
					return readObject;
				}
			}
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 所有异常返回null
		return null;

	}

	/**
	 * desc:将16进制的数据转为数组
	 * @param data
	 * @return modified:
	 */
	public static byte[] StringToBytes(String data) {
		String hexString = data.toUpperCase().trim();
		if (hexString.length() % 2 != 0) {
			return null;
		}
		byte[] retData = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i++) {
			int int_ch; // 两位16进制数转化后的10进制数
			char hex_char1 = hexString.charAt(i); // //两位16进制数中的第一位(高位*16)
			int int_ch1;
			if (hex_char1 >= '0' && hex_char1 <= '9')
				int_ch1 = (hex_char1 - 48) * 16; // // 0 的Ascll - 48
			else if (hex_char1 >= 'A' && hex_char1 <= 'F')
				int_ch1 = (hex_char1 - 55) * 16; // // A 的Ascll - 65
			else
				return null;
			i++;
			char hex_char2 = hexString.charAt(i); // /两位16进制数中的第二位(低位)
			int int_ch2;
			if (hex_char2 >= '0' && hex_char2 <= '9')
				int_ch2 = (hex_char2 - 48); // // 0 的Ascll - 48
			else if (hex_char2 >= 'A' && hex_char2 <= 'F')
				int_ch2 = hex_char2 - 55; // // A 的Ascll - 65
			else
				return null;
			int_ch = int_ch1 + int_ch2;
			retData[i / 2] = (byte) int_ch;// 将转化后的数放入Byte里
		}
		return retData;
	}

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
     * 验证身份证号
     * @param idcard
     * @return
     */
    public static boolean isIDCard(String idcard) {
        Pattern pattern = Pattern.compile("([0-9]{17}([0-9]|X|x))|([0-9]{15})");
        return pattern.matcher(idcard).matches();
    }

    /**
     * 从网络上获取内容文本
     * @param path  文本路径
     * @return
     * @throws Exception
     */
    public static List<String> readTextCotant(String path) throws Exception {
        List<String> list = new ArrayList<String>();
        String string = null;
        BufferedReader buffer = null;
        URL url;
        HttpURLConnection hconn = null;

        url = new URL(path);
        hconn = (HttpURLConnection) url.openConnection();
        hconn.setConnectTimeout(5 * 1000);
        hconn.setReadTimeout(5 * 1000);
        buffer = new BufferedReader(new InputStreamReader(
                hconn.getInputStream(), "UTF-8"));
        while ((string = buffer.readLine()) != null) {
            int i = string.indexOf("\r\n");
            if (i != -1) {
                list.add(string.substring(0, i));
            } else {
                list.add(string);
            }
        }
        if (buffer != null) {
            buffer.close();
        }
        return list;
    }


	/**
	 *  开始定位
	 */
	public final static int MSG_LOCATION_START = 0;
	/**
	 * 定位完成
	 */
	public final static int MSG_LOCATION_FINISH = 1;
	/**
	 * 停止定位
	 */
	public final static int MSG_LOCATION_STOP= 2;

	public final static String KEY_URL = "URL";
	public final static String URL_H5LOCATION = "file:///android_asset/location.html";
	/**
	 * 根据定位结果返回定位信息的字符串
	 * @param location
	 * @return
	 */
//	public synchronized static String getLocationStr(AMapLocation location){
//		if(null == location){
//			return null;
//		}
//		StringBuffer sb = new StringBuffer();
//		//errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
//		if(location.getErrorCode() == 0){
//			sb.append("定位成功" + "\n");
//			sb.append("定位类型: " + location.getLocationType() + "\n");
//			sb.append("经    度    : " + location.getLongitude() + "\n");
//			sb.append("纬    度    : " + location.getLatitude() + "\n");
//			sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
//			sb.append("提供者    : " + location.getProvider() + "\n");
//
//			sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
//			sb.append("角    度    : " + location.getBearing() + "\n");
//			// 获取当前提供定位服务的卫星个数
//			sb.append("星    数    : " + location.getSatellites() + "\n");
//			sb.append("国    家    : " + location.getCountry() + "\n");
//			sb.append("省            : " + location.getProvince() + "\n");
//			sb.append("市            : " + location.getCity() + "\n");
//			sb.append("城市编码 : " + location.getCityCode() + "\n");
//			sb.append("区            : " + location.getDistrict() + "\n");
//			sb.append("区域 码   : " + location.getAdCode() + "\n");
//			sb.append("地    址    : " + location.getAddress() + "\n");
//			sb.append("兴趣点    : " + location.getPoiName() + "\n");
//			//定位完成的时间
//			sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
//		} else {
//			//定位失败
//			sb.append("定位失败" + "\n");
//			sb.append("错误码:" + location.getErrorCode() + "\n");
//			sb.append("错误信息:" + location.getErrorInfo() + "\n");
//			sb.append("错误描述:" + location.getLocationDetail() + "\n");
//		}
//		//定位之后的回调时间
//		sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
//		return sb.toString();
//	}

	private static SimpleDateFormat sdf = null;
	public  static String formatUTC(long l, String strPattern) {
		if (TextUtils.isEmpty(strPattern)) {
			strPattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (sdf == null) {
			try {
				sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
			} catch (Throwable e) {
			}
		} else {
			sdf.applyPattern(strPattern);
		}
		return sdf == null ? "NULL" : sdf.format(l);
	}

	public static <T> boolean notEmpty(List<T> list) {
		return !isEmpty(list);
	}

	public static <T> boolean isEmpty(List<T> list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}

	// 将px值转换为dip或dp值
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	// 将dip或dp值转换为px值
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	// 将px值转换为sp值
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	// 将sp值转换为px值
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	// 屏幕宽度（像素）
	public static int getWindowWidth(Activity context) {
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels;
	}

	// 屏幕高度（像素）
	public static int getWindowHeight(Activity context) {
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels;
	}

	// 根据Unicode编码判断中文汉字和符号
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 判断中文汉字和符号
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

}
