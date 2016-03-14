package com.zone.banner_zonelib;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class PagerAdapterCycle<T> extends PagerAdapter {

    private  boolean isCircle;
    private  Context context;
    private List<T> data = null;


    public PagerAdapterCycle(Context context,List<T> data,boolean isCircle) {
        this.data = data;
        this.context=context;
        if(data.size()==1)
            this.isCircle=false;
        else
            this.isCircle=isCircle;
    }

    /**
     * 定义ViewPager的总长度的.
     */
    @Override
    public int getCount() {
        if (isCircle)
            return Integer.MAX_VALUE;
        else
            return data.size();
    }

    /**
     * 判断是否使用缓存, 如果true, 使用缓存 arg0 就是拖动的对象 arg1 进来的对象
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * 销毁对象 position 就是被销毁的对象的索引
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 从viewpager中移除当前索引的对象
        container.removeView(container.findViewById(position));
    }

    /**
     * 加载item position 被加载的item的索引
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int reallyPosition = position % data.size();
        View view = getView(context,reallyPosition);
        view.setId(position);
        container.addView(view);
        return view;
    }

    public int getSize() {
        return data.size();
    }
    public boolean isCircle() {
        return isCircle;
    }

    /**
     * 通过list获得view
     * @param position
     * @return
     */
    public abstract View getView(Context context,int position);

}
