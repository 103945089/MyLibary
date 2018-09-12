package shopping.hlhj.com.mylibrary.Tool;

import android.content.Context;
import android.content.SharedPreferences;

public class SPStorage {
	public static final String UID = "uid";//用户ID
	private static final String MID = "mid";//邮箱
	private static final String LAST_LOGIN = "last_login";//头像地址
	private static final String LAST_IP = "last_ip" ;//账号
	private static final String USER_TYPE = "user_type" ; //密码
	private static final String TOKEN = "token" ; //密码
	private static final String TOKEN_EXPIRE_TIME = "token_expire_time" ; //密码
	private static final String PASS = "PASS";
	private static final String USERNAME = "USERNAME";
	private static final String Head = "Head";
	private static final String PHONE = "Head";
	private static final String LAT = "LAT";
	private static final String LOG = "LOG";

	private Context context;
	private SharedPreferences preferences;

	public SPStorage(Context context) {
		this.context = context;
		preferences = context.getSharedPreferences("user", 0);
	}
	public SPStorage setLog(String log) {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		preferences.edit().putString(LOG, log).commit();
		return this;
	}

	public String getLog() {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		return preferences.getString(LOG, "");
	}


	public SPStorage setLat(String lat) {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		preferences.edit().putString(LAT, lat).commit();
		return this;
	}

	public String getLat() {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		return preferences.getString(LAT, "");
	}

	public SPStorage setHead(String head) {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		preferences.edit().putString(Head, head).commit();
		return this;
	}

	public String getHead() {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		return preferences.getString(Head, "");
	}


	public SPStorage setPhone(String phone) {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		preferences.edit().putString(PHONE, phone).commit();
		return this;
	}

	public String getPhone() {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		return preferences.getString(PHONE, "");
	}



	public SPStorage setPass(String pass) {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		preferences.edit().putString(PASS, pass).commit();
		return this;
	}

	public String getPass() {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		return preferences.getString(PASS, "");
	}


	public SPStorage setUserName(String usr) {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		preferences.edit().putString(USERNAME, usr).commit();
		return this;
	}

	public String getUserName() {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		return preferences.getString(USERNAME, "");
	}



	public SPStorage setUid(String uid) {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		preferences.edit().putString(UID, uid).commit();
		return this;
	}

	public String getUid() {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		return preferences.getString(UID, "");
	}



	public SPStorage setMID(String mid) {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		preferences.edit().putString(MID, mid).commit();
		return this;
	}

	public String getMID() {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		return preferences.getString(MID, "");
	}




	public SPStorage settoken(String token) {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		preferences.edit().putString(TOKEN, token).commit();
		return this;
	}

	public String gettoken() {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		return preferences.getString(TOKEN, "");
	}


	public void remove(String key) {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		preferences.edit().remove(key).commit();
	}

	public void clearUserInfo() {
		preferences = context.getSharedPreferences("iwhoop_user", 0);
		preferences.edit().clear().commit();
	}
}
