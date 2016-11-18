package and.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by fuzhipeng on 16/8/30.
 * 模板的作用 如果不同 copy然后 更改不同的地方
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements View.OnClickListener, Handler.Callback{

    public Handler handler;
    protected KindControl mKindControl;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        handler = new Handler(this);

        mKindControl = new KindControl(this);
        mKindControl.initKinds(this);
        updateKinds();

        mKindControl.onCreate(arg0);

        setContentView();
        findIDs();
        initData();
        setListener();
    }

    public void updateKinds() {

    }

    //---------------本类实现的方法-------------------
    @Override
    public void onClick(View v) {

    }
    public boolean handleMessage(Message msg) {
        return false;
    }
   // ---------------本类抽象的方法-------------------
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




    //------------------kinds的实现-------------------------------
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mKindControl.onPostCreate(savedInstanceState);
    }

    ;

    @Override
    protected void onResume() {
        super.onResume();
        mKindControl.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mKindControl.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mKindControl.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mKindControl.onDestroy();
    }
}
