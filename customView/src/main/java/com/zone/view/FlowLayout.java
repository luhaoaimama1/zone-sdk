package com.zone.view;

import java.util.ArrayList;
import java.util.List;

import com.zone.view.base.ViewGroup_Zone;
import com.zone.view.base.ViewProperty;
import com.zone.view.base.entity.ViewProperty_FlowLayout;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;


public class FlowLayout extends ViewGroup_Zone<ViewProperty_FlowLayout> {
    private int maxLine;
    private List<ViewProperty_FlowLayout> childList;
    private boolean center_vertical;

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context) {
        super(context);
    }


    /**
     * 重1开始
     *
     * @param maxLine
     */
    public void setMaxLine(int maxLine) {
        if (maxLine < 1)
            throw new IllegalArgumentException("maxLine must be >= 1");
        this.maxLine = maxLine;
        if (childList != null && childList.size() > 0) {
            for (ViewProperty_FlowLayout viewProperty : childList)
                if (!viewProperty.isLayout) {
                    //为了避免和别人已经invisbility 混乱。只有isLayout=false的时候我才恢复成visibility
                    viewProperty.isLayout = true;
                    viewProperty.view.setVisibility(View.VISIBLE);
                }
            requestLayout();
        }
    }

    /**
     * 居中的时候 是考虑margin的  margin是居中后在用的
     *
     * @param center_vertical
     */
    public void setVCenter(boolean center_vertical) {
        this.center_vertical = center_vertical;
        requestLayout();
    }

    @Override
    public PointF getViewLocation(List<ViewProperty_FlowLayout> childList, ViewProperty_FlowLayout viewAttr, int index, MeasureSpecMy mMeasureSpecMy) {
        if (index == 0) {
            return new PointF(0, 0);
        } else {
            ViewProperty_FlowLayout lastView = (ViewProperty_FlowLayout) viewAttr.preView;
            if (lastView.isLayout) {
                PointF viewWillLocation = new PointF(lastView.location.x + lastView.width2Height2Margin.x, lastView.location.y);
                if (viewWillLocation.x + viewAttr.width2Height2Margin.x > mMeasureSpecMy.widthSize) {
                    if (lastView.verticalNum == maxLine - 1) {
                        //换行的时候发现 上一行和最大行数相等 所以下面的都不显示了
                        viewAttr.isLayout = false;
                        viewAttr.view.setVisibility(View.INVISIBLE);
                        return null;
                    }
                    float maxY = 0;
                    for (ViewProperty attr : childList)
                        //当前不算在内   计算这行 最大的高度;
                        if (attr != viewAttr && attr.location.y == viewWillLocation.y && attr.width2Height2Margin.y > maxY)
                            maxY = attr.width2Height2Margin.y;
                    viewAttr.horizontalNum = 0;
                    viewAttr.verticalNum = lastView.verticalNum + 1;
                    return new PointF(0, viewWillLocation.y + maxY);
                } else {
                    viewAttr.horizontalNum = lastView.horizontalNum + 1;
                    viewAttr.verticalNum = lastView.verticalNum;
                    return viewWillLocation;
                }
            } else {
                viewAttr.isLayout = false;
                viewAttr.view.setVisibility(View.INVISIBLE);
                return null;
            }
        }
    }

    @Override
    public PointF makeSureMeasureSize(List<ViewProperty_FlowLayout> childList, MeasureSpecMy mMeasureSpecMy) {
        this.childList = childList;
        if (childList.size() != 0) {
            ViewProperty_FlowLayout lastOne = null;
            for (ViewProperty_FlowLayout viewAttr : childList) {
                if (!viewAttr.isLayout)
                    break;
                lastOne = viewAttr;
            }
            float maxY = 0;
            for (ViewProperty_FlowLayout viewAttr : childList) {
                if (lastOne.location.y == viewAttr.location.y && viewAttr.width2Height2Margin.y > maxY)
                    maxY = viewAttr.width2Height2Margin.y;
            }
            if (center_vertical)
                calculateCente(childList);
            return new PointF(mMeasureSpecMy.widthSize, lastOne.location.y + maxY);
        } else {
            return new PointF(0, 0);
        }
    }

    private void calculateCente(List<ViewProperty_FlowLayout> childList) {
        List<Float> maxYlist = new ArrayList<>();
        for (ViewProperty_FlowLayout viewProperty : childList) {
            if (viewProperty.horizontalNum == 0 && viewProperty.verticalNum != 0)
                maxYlist.add((float) viewProperty.view.getHeight());
        }
        for (int i = 0; i < maxYlist.size(); i++) {
            for (ViewProperty_FlowLayout viewProperty : childList) {
                if (viewProperty.horizontalNum == i)
                    viewProperty.offsetExtra = new PointF(0, (maxYlist.get(i) - viewProperty.view.getHeight()) / 2);
            }
        }
    }


}
