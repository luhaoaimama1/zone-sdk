package and.utlis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * @version 2015.7.15
 * @author Zone
 *
 */
public class SharedUtils {
	private static final String SP_TAG="preferences";
	private SharedPreferences share;
	private Editor editor;
	private static SharedUtils su;

	@SuppressLint("CommitPrefEdits")
	private SharedUtils(Context context) {
		share = context.getSharedPreferences(SP_TAG,Context.MODE_PRIVATE);
		editor = share.edit();
	}

	public static SharedUtils getInstance(Context context) {
		if(su==null){
			su=new SharedUtils(context);
		}
		return su;
	}

	public SharedPreferences readSp() {
		return share;
	}

	public Editor writeSp() {
		return editor;
	}

	public UserName_Password getUserName_Password() {
		// 读取用户名密码
		String userName = share.getString("userName", null);
		String userPassword = share.getString("userPassword", null);
		UserName_Password user = new UserName_Password(userName, userPassword);
		return user;

	}

	public void setUserName_Password(String userName, String userPassword) {
		UserName_Password user = new UserName_Password(userName, userPassword);
		editor.putString("userName", user.getUserName());
		editor.putString("userPassword", user.getUserPassword());
		editor.commit();
	}

	public static class UserName_Password {
		private String userName;
		private String userPassword;

		public UserName_Password(String userName, String userPassword) {
			this.userName = userName;
			this.userPassword = userPassword;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getUserPassword() {
			return userPassword;
		}

		public void setUserPassword(String userPassword) {
			this.userPassword = userPassword;
		}
	}
}
