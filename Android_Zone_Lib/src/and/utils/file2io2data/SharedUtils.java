package and.utils.file2io2data;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
	private SharedPreferences share;
	private Editor editor;
	private static SharedUtils su;

	@SuppressLint("CommitPrefEdits")
	private SharedUtils(Context context) {
		share = context.getSharedPreferences(SP_TAG,Context.MODE_PRIVATE);
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
	/**
	 * put string preferences
	 *
	 * @param context
	 * @param key The name of the preference to modify
	 * @param value The new value for the preference
	 * @return True if the new values were successfully written to persistent storage.
	 */
	public static boolean putString(Context context, String key, String value) {
		SharedPreferences settings = context.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	/**
	 * get string preferences
	 *
	 * @param context
	 * @param key The name of the preference to retrieve
	 * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
	 *         name that is not a string
	 * @see #getString(Context, String, String)
	 */
	public static String getString(Context context, String key) {
		return getString(context, key, null);
	}

	/**
	 * get string preferences
	 *
	 * @param context
	 * @param key The name of the preference to retrieve
	 * @param defaultValue Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
	 *         this name that is not a string
	 */
	public static String getString(Context context, String key, String defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE);
		return settings.getString(key, defaultValue);
	}

	/**
	 * put int preferences
	 *
	 * @param context
	 * @param key The name of the preference to modify
	 * @param value The new value for the preference
	 * @return True if the new values were successfully written to persistent storage.
	 */
	public static boolean putInt(Context context, String key, int value) {
		SharedPreferences settings = context.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	/**
	 * get int preferences
	 *
	 * @param context
	 * @param key The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
	 *         name that is not a int
	 * @see #getInt(Context, String, int)
	 */
	public static int getInt(Context context, String key) {
		return getInt(context, key, -1);
	}

	/**
	 * get int preferences
	 *
	 * @param context
	 * @param key The name of the preference to retrieve
	 * @param defaultValue Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
	 *         this name that is not a int
	 */
	public static int getInt(Context context, String key, int defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE);
		return settings.getInt(key, defaultValue);
	}

	/**
	 * put long preferences
	 *
	 * @param context
	 * @param key The name of the preference to modify
	 * @param value The new value for the preference
	 * @return True if the new values were successfully written to persistent storage.
	 */
	public static boolean putLong(Context context, String key, long value) {
		SharedPreferences settings = context.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	/**
	 * get long preferences
	 *
	 * @param context
	 * @param key The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
	 *         name that is not a long
	 * @see #getLong(Context, String, long)
	 */
	public static long getLong(Context context, String key) {
		return getLong(context, key, -1);
	}

	/**
	 * get long preferences
	 *
	 * @param context
	 * @param key The name of the preference to retrieve
	 * @param defaultValue Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
	 *         this name that is not a long
	 */
	public static long getLong(Context context, String key, long defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE);
		return settings.getLong(key, defaultValue);
	}

	/**
	 * put float preferences
	 *
	 * @param context
	 * @param key The name of the preference to modify
	 * @param value The new value for the preference
	 * @return True if the new values were successfully written to persistent storage.
	 */
	public static boolean putFloat(Context context, String key, float value) {
		SharedPreferences settings = context.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}

	/**
	 * get float preferences
	 *
	 * @param context
	 * @param key The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
	 *         name that is not a float
	 * @see #getFloat(Context, String, float)
	 */
	public static float getFloat(Context context, String key) {
		return getFloat(context, key, -1);
	}

	/**
	 * get float preferences
	 *
	 * @param context
	 * @param key The name of the preference to retrieve
	 * @param defaultValue Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
	 *         this name that is not a float
	 */
	public static float getFloat(Context context, String key, float defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE);
		return settings.getFloat(key, defaultValue);
	}

	/**
	 * put boolean preferences
	 *
	 * @param context
	 * @param key The name of the preference to modify
	 * @param value The new value for the preference
	 * @return True if the new values were successfully written to persistent storage.
	 */
	public static boolean putBoolean(Context context, String key, boolean value) {
		SharedPreferences settings = context.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	/**
	 * get boolean preferences, default is false
	 *
	 * @param context
	 * @param key The name of the preference to retrieve
	 * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
	 *         name that is not a boolean
	 * @see #getBoolean(Context, String, boolean)
	 */
	public static boolean getBoolean(Context context, String key) {
		return getBoolean(context, key, false);
	}

	/**
	 * get boolean preferences
	 *
	 * @param context
	 * @param key The name of the preference to retrieve
	 * @param defaultValue Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
	 *         this name that is not a boolean
	 */
	public static boolean getBoolean(Context context, String key, boolean defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE);
		return settings.getBoolean(key, defaultValue);
	}
}
