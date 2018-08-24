package com.zone.lib.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: zone (1149324777@qq.com)
 * Time: 14-7-17 下午5:46
 * 可见做了兼容
 */
public abstract class LazyFragment extends Fragment {

    private LayoutInflater inflater;
    private View inflateView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        setContentView(inflater,container,savedInstanceState);
        findIDs();
        initData();
        setListener();
        if (!setUserVisibleHintUsed) {
            inflateView.post(new Runnable() {
                @Override
                public void run() {
                    setUserVisibleCompat(true);
                }
            });
        } else {
            if (mlastIsVisibleToUser) {
                setUserVisibleCompat(true);
            }
        }
        return inflateView;
    }

    boolean setUserVisibleHintUsed;
    boolean mlastIsVisibleToUser;

    /**
     * 与ViewPager一起使用，ViewPager的FragmentPagerAdapter会调用的是setUserVisibleHint
     * 注意:如果页面过多 则会从新 new 因为默认缓存是一个页面
     * setUserVisibleHint->onCreateView
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mlastIsVisibleToUser = isVisibleToUser;
        if (!setUserVisibleHintUsed) {
            setUserVisibleHintUsed = true;
        }
        if (inflateView != null) {
            setUserVisibleCompat(isVisibleToUser);
        }
    }


    /**
     * 对于show(A).hide(B)的切换方式，setUserVisibleHint()不会被调用，
     * 但是显示第一次不触发 onHiddenChanged 所以需要在onCreateView 触发了
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        setUserVisibleCompat(!hidden);
    }

    boolean isVisibleToUser;

    public boolean getUserVisibleCompat() {
        return isVisibleToUser;
    }

    private void setUserVisibleCompat(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            onFragmentShown();
        } else {
            onFragmentDismiss();
        }
    }

    protected abstract void onFragmentShown();

    protected abstract void onFragmentDismiss();


    // 若 viewpager 不设置 setOffscreenPageLimit 或设置数量不够
    // 销毁的Fragment onCreateView 每次都会执行(但实体类没有从内存销毁)
    @Override
    public void onDestroyView() {
        if (isVisibleToUser) {
            setUserVisibleCompat(false);
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public View setContentView(int layoutId) {
        inflateView = inflater.inflate(layoutId, null);
        if (inflateView == null)
            throw new IllegalStateException("inflateView is null! layoutId maybe is error");
        return inflateView;
    }
    public View setContentView(View view) {
        return inflateView=view;
    }

    public View findViewById(int viewId) {
        return inflateView.findViewById(viewId);
    }

    /**
     * 设置子类布局对象
     */
    public abstract void setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

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
