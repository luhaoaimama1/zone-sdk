package and.utlis;


import and.utlis.MeasureUtils.GlobalState;
import and.utlis.MeasureUtils.OnMeasureListener;
import android.content.Context;
import android.view.View;
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
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void openKeybord(EditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void closeKeybord(EditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}

	/**
	 * monitor 监控
	 * 项目清单添加这个
	 *  android:windowSoftInputMode="stateHidden|adjustResize"
	 *  上句话的解释：该Activity总是<strong>调整屏幕</strong>的大小以便留出软键盘的空间 
	 *  
	 * @param mainLayoutView  整个布局的顶部
	 */
	public void monitorKeybordState(View mainLayoutView) {
		MeasureUtils.measureView_addGlobal(mainLayoutView,
				GlobalState.MEASURE_CONTINUE_LISNTER, new OnMeasureListener() {

					@Override
					public void measureResult(View v, int view_width,
							int view_height) {
						int heightDiffTemp = v.getRootView().getHeight()- v.getHeight();
						if (heightDiffTemp > 100) {
							// 说明键盘是弹出状态
							if (heightDiff<100) {
								heightDiff = heightDiffTemp;
							}
							switch (openStatue) {
							case CLOSE:
								openStatue=OpenStatue.OPEN;
								openState(heightDiff);
								break;
							case NO_MEASURE:
								openStatue=OpenStatue.OPEN;
								openState(heightDiff);
								break;
							default:
								break;
							}
						} else {
							switch (openStatue) {
							case OPEN:
								openStatue=OpenStatue.CLOSE;
								closeState(heightDiff);
								break;
							case NO_MEASURE:
								openStatue=OpenStatue.CLOSE;
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
}
