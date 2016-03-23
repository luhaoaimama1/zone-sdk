package com.zone.zrflist.fully;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Zone on 2016/3/2.
 */
public class FullyListView extends ListView {

    public FullyListView(Context context) {
        super(context);
    }

    public FullyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullyListView(Context context, AttributeSet attrs,
                         int defStyle) {
        super(context, attrs, defStyle);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}