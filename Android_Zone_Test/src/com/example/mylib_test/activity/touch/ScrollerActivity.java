package com.example.mylib_test.activity.touch;

import com.example.mylib_test.R;
import com.nineoldandroids.view.ViewHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class ScrollerActivity extends Activity implements View.OnClickListener {
    private ViewGroup vp;
    private View tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_touch_scroller);
        vp = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        vp.getChildAt(0);
        tv=vp.getChildAt(0).findViewById(R.id.tv);
        findViewById(R.id.ll_main).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.offleftandRight:
                vp.getChildAt(0).findViewById(R.id.tv).offsetLeftAndRight(100);
                break;
            case R.id.offtopandBottom:
                vp.getChildAt(0).findViewById(R.id.tv).offsetTopAndBottom(100);
                break;
            case R.id.setMarginToP:
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) vp.getChildAt(0).findViewById(R.id.tv).getLayoutParams();
                params.setMargins(0, 100, 0, 0);
                tv.setLayoutParams(params);

                break;
            case R.id.setViewHelperX:
                ViewHelper.setY(tv, 200);
                break;
            case R.id.requestLayout:
                ((ViewGroup.MarginLayoutParams)tv.getLayoutParams()).height=100;
                tv.requestLayout();
                tv.scrollTo(100, 100);
                tv.scrollBy(100,50);
                break;
        }
        Property p = new Property(tv);
    }
    class Property{
        public float y;
        public float tY;
        public int top;
        public int topMargin;

        public Property(View view) {
            y=ViewHelper.getY(view);
            tY=ViewHelper.getTranslationY(view);
            top=view.getTop();
            topMargin=((ViewGroup.MarginLayoutParams)view.getLayoutParams()).topMargin;
        }
    }

}
