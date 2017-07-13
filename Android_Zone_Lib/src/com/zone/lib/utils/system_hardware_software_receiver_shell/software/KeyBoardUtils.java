package com.zone.lib.utils.system_hardware_software_receiver_shell.software;


import com.zone.lib.utils.activity_fragment_ui.MeasureUtils;
import com.zone.lib.utils.activity_fragment_ui.MeasureUtils.ListenerState;
import com.zone.lib.utils.activity_fragment_ui.MeasureUtils.OnMeasureListener;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 打开或关闭软键盘
 * 
 * @author zhy
 * 
 */
public abstract class KeyBoardUtils {
	public static int heightDiff=0;
	public OpenStatue openStatue=OpenStatue.NO_MEASURE;
	public enum OpenStatue{
		OPEN,CLOSE,NO_MEASURE
	}
	/**
	 * 打卡软键盘
	 * @param mEditText 输入框
	 * @param mContext  上下文
	 */
	public static void openKeybord(EditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}
	public static boolean openKeybord(View view) {
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		return imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	public static boolean openKeybord(Activity activity) {
		View view = activity.getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
					Context.INPUT_METHOD_SERVICE);
			return imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
		}
		return false;
	}

	/**
	 * 关闭软键盘
	 * @param mEditText 输入框
	 * @param mContext  上下文
	 */
	public static void closeKeybord(EditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}
	public static boolean closeKeybord(View view) {
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static boolean closeKeybord(Activity activity) {
		if (activity.getCurrentFocus() != null) {
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			return imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		}
		return false;
	}

	/**
	 * 这个貌似有时候不好使吧
	 * @param context
	 * @return
	 */
	public static boolean isActive(Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		return imm.isActive();
	}

	/**
	 * 如果输入法已经显示，那么就隐藏它；如果输入法现在没显示，那么就显示它
	 */
	public static void toggle(Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}


	/**
	 * 切换为英文输入模式
	 */
	public static void changeToEnglishInputType(EditText editText) {
		editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_URI);
	}

	/**
	 * 切换为中文输入模式
	 */
	public static void changeToChineseInputType(EditText editText) {
		editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
	}

	/**
	 * monitor 监控
	 * 项目清单添加这个
	 *  android:windowSoftInputMode="stateHidden|adjustResize"
	 *  上句话的解释：该Activity总是<strong>调整屏幕</strong>的大小以便留出软键盘的空间 
	 *  
	 */
	public void monitorKeybordState(final Activity activity) {
		MeasureUtils.measure(activity.getWindow().getDecorView().findViewById(android.R.id.content),
				ListenerState.MEASURE_CONTINUE, new OnMeasureListener() {

					@Override
					public void measureResult(View v, int viewWidth,
											  int viewHeight) {
						View rootView = activity.getWindow().getDecorView();
						int heightDiffTemp =rootView.getHeight() -((ViewGroup)((ViewGroup)rootView).getChildAt(0)).getChildAt(1).getHeight();
						//也可以用这个activity.getWindow().getDecorView().findViewById(R.id.content).getParent().getParent()
						if (heightDiffTemp > 100) {
							// 说明键盘是弹出状态
							if (heightDiff < 100) {
								heightDiff = heightDiffTemp;
							}
							switch (openStatue) {
								case CLOSE:
									openStatue = OpenStatue.OPEN;
									openState(heightDiff);
									break;
								case NO_MEASURE:
									openStatue = OpenStatue.OPEN;
									openState(heightDiff);
									break;
								default:
									break;
							}
						} else {
							switch (openStatue) {
								case OPEN:
									openStatue = OpenStatue.CLOSE;
									closeState(heightDiff);
									break;
								case NO_MEASURE:
									openStatue = OpenStatue.CLOSE;
									closeState(heightDiff);
									break;
								default:
									break;
							}

						}
					}
				});
	}
	/**
	 * 键盘高度
	 * @param keyboardHeight
	 */
	public abstract void openState(int keyboardHeight);
	/**
	 * 键盘高度
	 * @param keyboardHeight
	 */
	public abstract void closeState(int keyboardHeight);

	/**
	 * 监听输入法的回车按键
	 */
	public static void setEnterKeyListener(EditText editText, final View.OnClickListener listener) {
		editText.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// 这两个条件必须同时成立，如果仅仅用了enter判断，就会执行两次
				if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
					listener.onClick(v);
					return true;
				}
				return false;
			}
		});
	}
}
