package com.zone.view.ninegridview.preview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zone.customview.ninegridview.R;
import com.zone.zbanner.PagerAdapterCycle;
import com.zone.zbanner.ViewPagerCircle;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/12.
 */
public class ImagePreviewActivity extends Activity implements ViewTreeObserver.OnPreDrawListener {
    public static final String IMAGE_INFO = "IMAGE_INFO";
    public static final String INDEX = "index";

    private int currentItem;
    private List<ImageInfoInner> imageInfo;

    private ViewPagerCircle vpc;
    private TextView tv_pager;
    private RelativeLayout rootView;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.a_preview);

        rootView = (RelativeLayout) findViewById(R.id.rootView);
        vpc = (ViewPagerCircle) findViewById(R.id.vpc);
        tv_pager = (TextView) findViewById(R.id.tv_pager);


        Intent intent = getIntent();
        imageInfo = (List<ImageInfoInner>) intent.getSerializableExtra(IMAGE_INFO);
        currentItem = intent.getIntExtra(INDEX, 0);

        vpc.setAdapter(new PagerAdapterCycle(this, imageInfo, false) {
            @Override
            public View getView(Context context, int position) {
                return null;
            }
        },currentItem);

    }

    @Override
    public boolean onPreDraw() {
        return true;
    }
}
