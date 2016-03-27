package and.base.activity.decorater;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;


/**
 * TODO 在功能  在界面 最后一个装置者 制定顺序的人 才能  是抽象类 别的不要 不然方法都得实现太难看了
 * Created by Administrator on 2016/3/26.
 */
public class BaseDecorater extends FragmentActivity implements Handler.Callback,View.OnClickListener {
    protected Handler handler;
    public static List<Activity> activitys=new ArrayList<Activity>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activitys.add(this);
        super.onCreate(savedInstanceState);
        handler=new Handler(this);
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    /**
     * 必须在 setContentView 之前用 否则无效！
     */
    public BaseDecorater setNoTitle(){
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return this;
    }
    /**
     * 必须在 setContentView 之前用 否则无效!
     */
    public BaseDecorater setFullScreen(){
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return this;
    }

    @Override
    protected void onDestroy() {
		/* 防止内存泄漏 */
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
}
