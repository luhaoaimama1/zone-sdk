package com.zone.zbanner.viewpager;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Zone on 2016/2/3.
 */
public class ViewPagerCompat extends ViewPager {
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    public ViewPagerCompat(Context context) {
        super(context);
    }

    public ViewPagerCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //兼容动画  注释了  if (Build.VERSION.SDK_INT >= 11)
    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
        Field mPageTransformer_Field = null;
        Field mDrawingOrder_Field = null;
        PageTransformer mPageTransformer = null;
        Method setChildrenDrawingOrderEnabledCompat_Method=null;
        Method populate_Method=null;
        try {
            mPageTransformer_Field = ViewPager.class.getDeclaredField("mPageTransformer");
            mPageTransformer_Field.setAccessible(true);
            mPageTransformer = (PageTransformer) mPageTransformer_Field.get(this);

            setChildrenDrawingOrderEnabledCompat_Method =ViewPager.class.getDeclaredMethod("setChildrenDrawingOrderEnabledCompat", boolean.class);
            setChildrenDrawingOrderEnabledCompat_Method.setAccessible(true);


            mDrawingOrder_Field= ViewPager.class.getDeclaredField("mDrawingOrder");
            mDrawingOrder_Field.setAccessible(true);

            populate_Method = ViewPager.class.getDeclaredMethod("populate");
            populate_Method.setAccessible(true);

            //逻辑层

            final boolean hasTransformer = transformer != null;
            final boolean needsPopulate = hasTransformer != (mPageTransformer != null);
            mPageTransformer_Field.set(this, transformer);
            setChildrenDrawingOrderEnabledCompat_Method.invoke(this, hasTransformer);
            if (hasTransformer) {
                mDrawingOrder_Field.setInt(this,reverseDrawingOrder ? DRAW_ORDER_REVERSE : DRAW_ORDER_FORWARD);
            }else{
                mDrawingOrder_Field.setInt(this,DRAW_ORDER_DEFAULT);
            }
            if (needsPopulate) populate_Method.invoke(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
