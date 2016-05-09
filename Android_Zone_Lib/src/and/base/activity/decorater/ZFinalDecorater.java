package and.base.activity.decorater;

import android.os.Bundle;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/3/26.
 */
public abstract class ZFinalDecorater  extends SwipeBackDecorater{
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
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
}
