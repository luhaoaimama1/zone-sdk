package and.base.activity;
import java.util.ArrayList;
import java.util.List;
import com.nostra13.universalimageloader.core.ImageLoader;
import and.base.activity.decorater.ZFinalDecorater;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActvity extends ZFinalDecorater implements Callback,OnClickListener{
	public static int Default_RequestCode=9999;
	public static int Reresh_Response=9998;
	protected ImageLoader imageLoader;
	protected Handler handler;
	public static List<Activity> activitys=new ArrayList<Activity>();

	@Override
	protected void onCreate(Bundle arg0) {
		activitys.add(this);
		Log.d("BaseActvity", "onCreate");
		super.onCreate(arg0);
		imageLoader = ImageLoader.getInstance();
		handler=new Handler(this);
		Log.d("BaseActvity","setContentView");
		setContentView();
		findIDs();
		initData();
		setListener();
	}


	/**
	 * 设置子类布局对象
	 */
	public abstract void setContentView();

	/**
	 * 子类查找当前界面所有id
	 */
	public abstract void findIDs();

	/**
	 * 子类初始化数据
	 */
	public abstract void initData();

	/**
	 * 子类设置事件监听
	 */
	public abstract void setListener();
	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
	@Override
	public void onClick(View v) {
		
	}
	@Override
	protected void onDestroy() {
		/*
		 * 防止内存泄漏
		 */
		activitys.remove(this);
		super.onDestroy();
	}
	/**
	 * 结束所有 还存在的activitys  一般在异常出现的时候
	 */
	public void finishAll() {
		for (Activity item : activitys) {
			item.finish();
		}
	}
	//带有可返回刷新 跳转
	public  void startActivityWithRefresh(Intent intent){
		startActivityForResult(intent,Default_RequestCode);
	}
	//有intent数据返回的刷新
	public void finishWithBackRefresh(Intent data){
		setResult(Reresh_Response, data);
		finish();
	}
	//没有intent数据返回的刷新
	public void finishWithBackRefresh(){
		setResult(Reresh_Response);
		finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//resultCode==0那么就是默认返回的即直接finish　的不管
		if(requestCode==Default_RequestCode&&resultCode==Reresh_Response)
			backRefresh();
		super.onActivityResult(requestCode, resultCode, data);
	}
	public void  backRefresh(){
		
	};


}
