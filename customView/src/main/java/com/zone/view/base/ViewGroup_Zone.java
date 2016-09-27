package com.zone.view.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import and.utils.data.file2io2data.HashMapZ;
import and.utils.reflect.ReflectGenericUtils;

//此view是ViewGroup而不是 onDraw那种自定义
public  abstract  class ViewGroup_Zone<T extends ViewProperty> extends LinearLayout {
    //offset是关于padding的偏移
    private int mMeasureWith, mMeasureHeight,mViewWidth,mViewHeight;
    int offsetX, offsetY;
    private List<T> childList = new ArrayList<>();
    private MeasureSpecMy mMeasureSpecMy;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ViewGroup_Zone(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ViewGroup_Zone(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewGroup_Zone(Context context) {
        this(context, null);
    }

    public class MeasureSpecMy {
        //这里widthSize heightSize 是最大值 要改他可以在这里改 例如宽高相等 改成相等即可
        public int widthMode, heightMode, widthSize, heightSize;
    }



    public void setMeasureSpec(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMeasureSpecMy == null)
            mMeasureSpecMy = new MeasureSpecMy();
        mMeasureSpecMy.widthMode = MeasureSpec.getMode(widthMeasureSpec);
        mMeasureSpecMy.heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //这里widthSize heightSize 是最大值
        mMeasureSpecMy.widthSize = MeasureSpec.getSize(widthMeasureSpec);
        mMeasureSpecMy.heightSize = MeasureSpec.getSize(heightMeasureSpec);
    }

    private void onMeasureReset() {
        mMeasureSpecMy = null;
        childList.clear();
        mMeasureWith = mMeasureHeight=0;
        offsetX = offsetY=0;
    }

    /**
     * 通过测量孩子的大小 来真正测量自己需要多大 如果不考虑 wrap_content 则可以不写此方法,直接在布局里写逻辑！！！
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        onMeasureReset();
        setMeasureSpec(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        ViewProperty lastView=null;
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view.getVisibility() != View.GONE) {

                T viewAttr =  generateViewProperty();
                viewAttr.view = view;
                viewAttr.preView=lastView;
                childList.add(viewAttr);
                LayoutParams lp = (LayoutParams) view.getLayoutParams();
                viewAttr.width2Height2Margin = new PointF(view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin,
                        view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                viewAttr.index=childList.indexOf(viewAttr);
                PointF temp = getViewLocation(childList, viewAttr,viewAttr.index, mMeasureSpecMy);
                if(temp!=null)
                    viewAttr.location=temp;
                lastView=viewAttr;//记录下最后一个viewAttr;
            }
        }

        PointF sizePoint = makeSureMeasureSize(childList, mMeasureSpecMy);
        mMeasureWith = (int) sizePoint.x;
        mMeasureHeight = (int) sizePoint.y;

        //在这之前确定  mMeasureWith  mMeasureHeight的值
        measureSupportPadding();

        onMeasureFinal(mMeasureSpecMy);
    }



    /**
     * 如果想改成自己的测量 可以重写此方法
     *
     * 如果是wrap_content设置为我们计算的值 否则：直接设置为父容器计算的值
     */
    public void onMeasureFinal(MeasureSpecMy measureSpecMy) {
        setMeasuredDimension(
                measureSpecMy.widthMode == MeasureSpec.EXACTLY ? measureSpecMy.widthSize : mMeasureWith,
                measureSpecMy.heightMode == MeasureSpec.EXACTLY ? measureSpecMy.heightSize: mMeasureHeight);
    }

    //	getPaddingLeft:可以写成-10dp
    private void measureSupportPadding() {
        if (mMeasureSpecMy.widthMode == MeasureSpec.EXACTLY) {
            offsetX = getPaddingLeft();
        } else {
            offsetX = getPaddingLeft();
            mMeasureWith = mMeasureWith + getPaddingLeft() + getPaddingRight();
            //如果这个widthSize<0 则代表无界限 view的宽最大值为widthSize
            if (mMeasureSpecMy.widthSize > 0 && mMeasureWith > mMeasureSpecMy.widthSize)
                mMeasureWith = mMeasureSpecMy.widthSize;
        }
        if (mMeasureSpecMy.heightMode == MeasureSpec.EXACTLY) {
            offsetY = getPaddingTop();
        } else {
            offsetY = getPaddingTop();
            mMeasureHeight = mMeasureHeight + getPaddingTop() + getPaddingBottom();
            if (mMeasureSpecMy.heightSize > 0 && mMeasureHeight > mMeasureSpecMy.heightSize)
                mMeasureHeight = mMeasureSpecMy.heightSize;
        }
    }

    /**
     * 知道自己多大后 然后给孩子布局
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (T item : childList) {
            item.layoutSupporVisibilty();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //在回调父类方法之前    实现自己的逻辑
        super.onDraw(canvas);//此方法来实现原生控件的功能
        //在回调父类方法之后    实现自己的逻辑
    }

    /**
     * 如何迭代这个位置 然后让viewAttr.location 有值  是业务逻辑   记得支持margin
     *注意：viewAttr 的location 还未确定既是null;
     * @return 返回的这个点是不包含padding的业务逻辑  因为padding已经内部支持了
     */
    public abstract PointF getViewLocation(List<T> childList, T viewAttr, int index, MeasureSpecMy mMeasureSpecMy);

    public abstract PointF makeSureMeasureSize(List<T> childList, MeasureSpecMy mMeasureSpecMy);


    //如果子类ViewProperty想不一样 集成他重写;
    @NonNull
    protected T generateViewProperty() {
        try {
            T result=((Class<T>) ReflectGenericUtils.getSuperGenericClass(this)).newInstance();
            result.mViewGroup_Zone=this;
            return result;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
