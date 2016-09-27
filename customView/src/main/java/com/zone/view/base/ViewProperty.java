package com.zone.view.base;

import android.graphics.PointF;
import android.view.View;
import android.view.View;
import android.widget.LinearLayout;

import and.utils.data.file2io2data.HashMapZ;

public class ViewProperty {
    public ViewGroup_Zone mViewGroup_Zone;

    public View view;// 收集view

    // 前一个不是gone的ViewProperty  如果是null那证明第一个;
    public ViewProperty preView;

    public int index = -1;

    //标注:为什么不用RectF呢  因为 location 未知  width2Height2Margin已知;

    //location： view在布局中左上角的点  location不从00开始是怕 于开始点重复了
    public PointF location = new PointF(-1, -1);
    //width2Height2Margin：view的宽高带margin的
    public PointF width2Height2Margin = new PointF();

    //offsetExtra ：和padding 和margin无关;  仅仅是给单个view加的偏移量
    public PointF offsetExtra = new PointF();


    public HashMapZ extraHashMap = new HashMapZ();

    //这里是对View显隐性  的support
    public void layoutSupporVisibilty() {
        if (view.getVisibility() == View.VISIBLE) {
            int realX = (int) (location.x + mViewGroup_Zone.offsetX);
            int realY = (int) (location.y + mViewGroup_Zone.offsetY);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
            view.layout((int) (realX + lp.leftMargin + offsetExtra.x)
                    , (int) (realY + lp.topMargin + offsetExtra.y)
                    , (int) (realX + width2Height2Margin.x - lp.rightMargin + offsetExtra.x),
                    (int) (realY + width2Height2Margin.y - lp.bottomMargin + offsetExtra.y));
        }
    }
}