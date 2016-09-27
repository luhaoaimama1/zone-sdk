package com.zone.view.ninegridview;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.zone.view.base.ViewGroup_Zone;
import com.zone.view.base.ViewProperty;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class ZGridView extends ViewGroup_Zone<ViewProperty> {
    private final Context context;
    /**
     * 图片之间的间隔
     */
    private int gap = 5;
    private int columns = 1;//列数
    private ZGridViewAdapter adapter;

    public ZGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public ZGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZGridView(Context context) {
        this(context, null);
    }

    //margin只考虑 之间
    @Override
    public PointF getViewLocation(List<ViewProperty> childList, ViewProperty viewAttr, int index, MeasureSpecMy mMeasureSpecMy) {
        System.out.println("mbd :" + index);
        if (adapter == null) {
            return new PointF(0, 0);
        } else {
            int viewWidth = (mMeasureSpecMy.widthSize - (columns - 1) * gap) / columns;
            LinearLayout ll = (LinearLayout) viewAttr.view;
            LayoutParams lp = (LayoutParams) ll.getLayoutParams();
            if (lp.height != viewWidth || lp.width != viewWidth) {
                lp.height = viewWidth;
                lp.width = viewWidth;
                ll.setLayoutParams(lp);
            }
            if (ll.getChildCount() == 0) {
                View itemView;
                ll.addView(itemView = adapter.getView(context, index));
                final int indexFinal = index;
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        adapter.onItemImageClick(context, indexFinal, adapter.list.get(indexFinal));
                    }
                });
                itemView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        adapter.onItemImageLongClick(context, indexFinal, adapter.list.get(indexFinal));
                        return true;
                    }
                });
            }
            viewAttr.width2Height2Margin = new PointF(viewWidth + lp.leftMargin + lp.rightMargin,
                    viewWidth + lp.topMargin + lp.bottomMargin);
            int columnsNum = index % columns;
            int rowsNum = index / columns;
            return new PointF(+(columnsNum) * (viewWidth + gap), (rowsNum) * (viewWidth + gap));
        }
    }

    @Override
    public PointF makeSureMeasureSize(List<ViewProperty> childList, MeasureSpecMy mMeasureSpecMy) {
        if (adapter == null || childList.size() == 0) {
            return new PointF(0, 0);
        } else {
            ViewProperty lastView = childList.get(childList.size() - 1);
            return new PointF(mMeasureSpecMy.widthSize, lastView.location.y + lastView.width2Height2Margin.y);
        }
    }

    public void setAdapter(ZGridViewAdapter adapter) {
        adapter.setGridViewZone(this);
        if (adapter.list.size() > 0) {
            setVisibility(View.VISIBLE);
            //改变了
            removeAllViews();
            this.adapter = adapter;
            for (int i = 0; i < adapter.list.size(); i++) {
                LinearLayout ll = new LinearLayout(context);
                addView(ll);
            }
            requestLayout();
        } else
            setVisibility(View.GONE);
    }

    public ZGridViewAdapter getAdapter() {
        return adapter;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
}
