package and.base;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
/**
 * Author: zone (1149324777@qq.com)
 * Time: 14-7-17 下午5:46
 */
public abstract class Fragment_Lazy extends Fragment {
    protected boolean isVisible;
    private LayoutInflater inflater;
    private View inflateView;
    private int visibleInt = 0, inVisibleInt = 0;
    private Mode mode;
    /***
     * 标志位，View已经初始化完成。
     */
    private boolean isPrepared;
    private boolean showViewPagerLater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        setContentView();
        findIDs();
        initData();
        setListener();
        if (mode == Mode.Normal)
            inflateView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public boolean onPreDraw() {
                    inflateView.getViewTreeObserver().removeOnPreDrawListener(this);
                    show();
                    return false;
                }
            });

        isPrepared = true;
        if (showViewPagerLater)
            showViewPager();
        showViewPagerLater = false;

        return inflateView;
    }

    /**
     * 与ViewPager一起使用，ViewPager的FragmentPagerAdapter会调用的是setUserVisibleHint
     * 注意:如果页面过多 则会从新 new 因为默认缓存是一个页面
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint())
            showViewPager();
        else
            hideViewPager();
    }

    private void hideViewPager() {
        if (isPrepared) {
            isVisible = false;
            inVisibleInt++;
            onInvisible(inVisibleInt);
        }
    }

    private void showViewPager() {
        if (isPrepared) {
            isVisible = true;
            visibleInt++;
            onVisible(visibleInt);
        } else
            showViewPagerLater = true;
    }

    // 若 viewpager 不设置 setOffscreenPageLimit 或设置数量不够
    // 销毁的Fragment onCreateView 每次都会执行(但实体类没有从内存销毁)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
        System.out.println("onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }

    /**
     * 没有用viewpager  如果是通过FragmentTransaction的show和hide的方法来控制显示，会调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            show();
        else
            hide();

    }

    private void hide() {
        isVisible = false;
        inVisibleInt++;
        onInvisible(inVisibleInt);
    }

    private void show() {
        isVisible = true;
        visibleInt++;
        onVisible(visibleInt);
    }

    /**
     * @param visibleInt 从1开始 说明是第一次展示页面
     */
    protected abstract void onVisible(int visibleInt);

    /**
     * @param inVisibleInt 从1开始 1说明是第一次 隐藏
     */
    protected abstract void onInvisible(int inVisibleInt);

    public View setContentView(int layoutId, Mode mode) {
        this.mode = mode;
        inflateView = inflater.inflate(layoutId, null);
        if (inflateView == null)
            throw new IllegalStateException("inflateView is null! layoutId maybe is error");
        return inflateView;
    }

    ;

    public enum Mode {
        ViewPager, Normal;
    }

    public View findViewById(int viewId) {
        return inflateView.findViewById(viewId);
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
