package com.zone.view.base;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

//此view是ViewGroup而不是 onDraw那种自定义
public abstract class ViewGroup_Zone extends LinearLayout {
    //offset是关于padding的偏移
    private int mMeasureWith, mMeasureHeight, offsetX, offsetY;
    private List<ViewProperty> childList = new ArrayList<>();
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

    public class ViewProperty {
        public View view;// 收集view
        //location： view在布局中左上角的点
        //width2Height2Margin：view的宽高带margin的
        public PointF location = new PointF(), width2Height2Margin = new PointF();
        //offsetExtra ：不带标padding 和margin  仅仅是给view加的偏移量
        public PointF offsetExtra = new PointF();
        //是否在布局里绘制 发现不好使 只能用invsibility  为了避免和别人已经invisbility重复 isLayout=false的时候我才恢复成visibility
        public boolean isLayout=true;
        //记录 第几行 第几行的第几个。可以用可以不用 从0,0开始
        public int verticalNum=-1,horizontalNum=-1;
        //这里是对View显隐性  的support
        void layoutSupporVisibilty() {
            if (view.getVisibility() == View.VISIBLE) {
                int realX = (int) (location.x + offsetX);
                int realY = (int) (location.y + offsetY);
                LayoutParams lp = (LayoutParams) view.getLayoutParams();
                view.layout((int) (realX + lp.leftMargin + offsetExtra.x)
                        , (int) (realY + lp.topMargin + offsetExtra.y)
                        , (int) (realX + width2Height2Margin.x - lp.rightMargin+ offsetExtra.x),
                        (int) (realY + width2Height2Margin.y - lp.bottomMargin+offsetExtra.y));
            }
        }
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
        mMeasureWith = 0;
        mMeasureHeight = 0;
        offsetX = 0;
        offsetY = 0;
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
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view.getVisibility() != View.GONE) {
                ViewProperty viewAttr = new ViewProperty();
                viewAttr.view = view;
                childList.add(viewAttr);
                LayoutParams lp = (LayoutParams) view.getLayoutParams();
                viewAttr.width2Height2Margin = new PointF(view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin,
                        view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                PointF temp = getViewLocation(childList, viewAttr, childList.indexOf(viewAttr), mMeasureSpecMy);
                if(temp!=null)
                    viewAttr.location=temp;
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
    public void onMeasureFinal(MeasureSpecMy mMeasureSpecMy) {
        setMeasuredDimension(this.mMeasureSpecMy.widthMode == MeasureSpec.EXACTLY ? this.mMeasureSpecMy.widthSize
                : mMeasureWith, this.mMeasureSpecMy.heightMode == MeasureSpec.EXACTLY ? this.mMeasureSpecMy.heightSize
                : mMeasureHeight);
    }

    ;

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
        for (ViewProperty item : childList) {
            item.layoutSupporVisibilty();
        }
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
    public abstract PointF getViewLocation(List<ViewProperty> childList, ViewProperty viewAttr, int index, MeasureSpecMy mMeasureSpecMy);

    public abstract PointF makeSureMeasureSize(List<ViewProperty> childList, MeasureSpecMy mMeasureSpecMy);

}
