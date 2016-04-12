package com.zone.view;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.zone.view.base.ViewGroup_Zone;

import java.util.List;
/**
 * Created by Administrator on 2016/4/11.
 * todo 最多显示几行
 */
public class FlowLayout extends ViewGroup_Zone {
    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context) {
        super(context);
    }

    @Override
    public PointF getViewLocation(List<ViewProperty> childList, ViewProperty viewAttr, int index, MeasureSpecMy mMeasureSpecMy) {
        if(index==0){
            return new PointF(0,0);
        }else{
            ViewProperty lastView = childList.get(index - 1);
            PointF viewWillLocation = new  PointF(lastView.location.x+ lastView.width2Height2Margin.x, lastView.location.y);
            if(viewWillLocation.x+viewAttr.width2Height2Margin.x>mMeasureSpecMy.widthSize){
                float maxY=0;
                for (ViewProperty attr : childList)
                    if(attr.location.y== viewWillLocation.y&&attr.width2Height2Margin.y>maxY)
                        maxY=attr.width2Height2Margin.y;
                if (viewAttr.width2Height2Margin.y>maxY)
                    maxY=viewAttr.width2Height2Margin.y;
                return  new PointF(0, viewWillLocation.y+maxY);
            }else
                return viewWillLocation;
        }
    }
    @Override
    public PointF makeSureMeasureSize(List<ViewProperty> childList, MeasureSpecMy mMeasureSpecMy) {
        if (childList.size()!=0) {
            ViewProperty lastOne = childList.get(childList.size() - 1);
            float maxY=0;
            for (ViewProperty viewAttr : childList) {
                if(lastOne.location.y==viewAttr.location.y&&viewAttr.width2Height2Margin.y>maxY)
                        maxY=viewAttr.width2Height2Margin.y;
            }
            return new PointF(mMeasureSpecMy.widthSize,lastOne.location.y+maxY);
        }else{
            return new PointF(0,0);
        }
    }


}
