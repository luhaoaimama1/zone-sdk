package com.example.mylib_test.activity.custom_view.gui.feedback;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.custom_view.MainGuijiqiuActivity;


public class FbRelativeLayout2 extends RelativeLayout implements IFocus {
    private final String TAG = getClass().getSimpleName();
    private final static int DEFAULT_COLOR = R.color.purple_700;
    private final static int DEFAULT_STROKE_COLOR = R.color.teal_700;
    private final static int DEFAULT_STROKE_WIDTH = 3;
    private final static float DEFAULT_RADIUS = 0;
    private final static float DEFAULT_SCALE = 1.2f;
    private int color;
    private int strokeWidth;
    private int strokeColor;
    private float radius;
    private float scale;

    public FbRelativeLayout2(Context context) {
        this(context, null);
    }

    public FbRelativeLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FbRelativeLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FbRelativeLayout2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.FbRelativeLayout);
            color = typedArray.getColor(R.styleable.FbRelativeLayout_color, getResources().getColor(DEFAULT_COLOR));
            strokeWidth = (int) typedArray.getDimension(R.styleable.FbRelativeLayout_strokeWidth, DEFAULT_STROKE_WIDTH);
            strokeColor = typedArray.getColor(R.styleable.FbRelativeLayout_strokeColor, getResources().getColor(DEFAULT_STROKE_COLOR));
            radius = typedArray.getDimension(R.styleable.FbRelativeLayout_radius2, DEFAULT_RADIUS);
            scale = typedArray.getFloat(R.styleable.FbRelativeLayout_scale, DEFAULT_SCALE);
            String str = color +
                    ", " +
                    strokeWidth +
                    ", " +
                    strokeColor +
                    ", " +
                    radius +
                    ", " +
                    scale;
            Log.e(TAG, "color, strokeWidth, strokeColor, radius, scale : " + str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }

        initCallback(context);
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {

        return super.dispatchHoverEvent(event);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return super.onInterceptHoverEvent(event);
//        return true;
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        return super.onHoverEvent(event);
    }

    // 这里进行轨迹球的回调监听
    private void initCallback(Context context) {
        setOnHoverListener(new OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                int what = event.getAction();
                switch(what){
                    case MotionEvent.ACTION_HOVER_ENTER: //鼠标进入view
                        fbFocus();
                        System.out.println("FbRelativeLayout bottom ACTION_HOVER_ENTER");
                        break;
                    case MotionEvent.ACTION_HOVER_MOVE: //鼠标在view上
                        System.out.println("FbRelativeLayout bottom ACTION_HOVER_MOVE");
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT: //鼠标离开view
                        fbNormal();
                        System.out.println("FbRelativeLayout bottom ACTION_HOVER_EXIT");
                        break;
                }
                return true;
            }
        });

        // 不规范的代码，只是为了方便测试。正式环境下不能这样写。
        MainGuijiqiuActivity.WatchFeedback watchFeedback = new MainGuijiqiuActivity.WatchFeedback() {
            @Override
            public void focus() {
                fbFocus();
            }

            @Override
            public void normal() {
                fbNormal();
            }
        };
        if (context instanceof MainGuijiqiuActivity) {
            MainGuijiqiuActivity mainActivity = (MainGuijiqiuActivity) context;
            mainActivity.addWatchFeedback(watchFeedback);
        }

        // 正式环境代码，测试焦点反馈监听
        setOnFocusChangeListener((view, b) -> {
            System.out.println("FbRelativeLayout setOnFocusChangeListener");
        });
    }

    @Override
    public void fbFocus() {
        View begin=this;
        while (true){
            boolean isViewGroup = begin.getParent() instanceof ViewGroup;
            if(!isViewGroup) break;
            ViewGroup parent = (ViewGroup) begin.getParent();
            if(parent.getClipChildren()){
                parent.setClipChildren(false);
            }
            begin=parent;
        }
        FocusUtil.focus(this, color, strokeWidth, strokeColor, radius, scale);
    }

    @Override
    public void fbNormal() {
        View begin=this;
        while (true){
            boolean isViewGroup = begin.getParent() instanceof ViewGroup;
            if(!isViewGroup) break;
            ViewGroup parent = (ViewGroup) begin.getParent();
            if(!parent.getClipChildren()){
                parent.setClipChildren(true);
            }
            begin=parent;
        }
        FocusUtil.normal(this);
    }
}
