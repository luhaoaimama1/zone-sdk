package and.utils.data.file2io2data;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @version 2015.7.15
 * @author Zone
 *
 * 主要因为效率分两部分
 * 静态方法：是单独操作
 * 非静态方法 ：是连续操作
 */
public class SharedUtils {
	private static final String SP_TAG="preferences";
	private static final String UserNameKEY="userName_";
	private  Gson gson;
	private SharedPreferences share;
	private Editor editor;
	private static SharedUtils su;
	@SuppressLint("CommitPrefEdits")
	private SharedUtils(Context context) {
		share = context.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE);
		editor = share.edit();
	}

	public static SharedUtils getInstance(Context context) {
		if (su == null)
			synchronized (SharedUtils.class) {
				if (su == null)
					su = new SharedUtils(context);
			}
		return su;
	}

	public SharedPreferences readSp() {
		return share;
	}

	public Editor writeSp() {
		return editor;
	}

	/**
	 * todo getUser()
	 * @return
	 */
//	public User  getUser() {
//		// 读取用户名密码
//		String userName = share.getString("userName", null);
//		String userPassword = share.getString("userPassword", null);
//		User user = new User(userName, userPassword);
//		return user;
//
//	}
	/**
	 * @return
	 */
	public List<User> getAllUser() {
		// 读取用户名密码
		Map<String, ?> allData = share.getAll();
		List<User> userList=new ArrayList<>();
		for (Map.Entry<String, ?> stringEntry : allData.entrySet()) {
			if (stringEntry.getKey().startsWith(stringEntry.getKey())) {
				String userString= (String) stringEntry.getValue();
				checkGson();
				User user=gson.fromJson(userString,User.class);
				userList.add(user);
			}
		}
		Collections.sort(userList);
		return userList;

	}
	private void checkGson(){
		if(gson==null)
			gson=new Gson();
	}
	/**
	 * gson来保存实体类
	 * @return
	 */
	public void setUser(User user) {
		checkGson();
		editor.putString(UserNameKEY+user.getUserName(), gson.toJson(user));
		editor.commit();
	}

	public static class User implements Comparable<User>{
		private String userName;
		private String userPassword;
		private boolean  isRememberPassword;
		private int loginNum;
		private Date date;

		public User() {
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

		public boolean isRememberPassword() {
			return isRememberPassword;
		}

		public void setIsRememberPassword(boolean isRememberPassword) {
			this.isRememberPassword = isRememberPassword;
		}

		public int getLoginNum() {
			return loginNum;
		}

		public void setLoginNum(int loginNum) {
			this.loginNum = loginNum;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		@Override
		public int compareTo(User another) {
			return this.date.getTime()<=another.getDate().getTime()?-1:1;
		}
	}
}
