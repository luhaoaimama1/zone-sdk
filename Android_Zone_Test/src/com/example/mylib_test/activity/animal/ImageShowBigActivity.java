package com.example.mylib_test.activity.animal;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mylib_test.R;

import and.base.activity.BaseActivity;
import and.utils.activity_fragment_ui.MeasureUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ImageShowBigActivity extends BaseActivity {
    @Bind(R.id.iv)
    ImageView iv;
    ImageView.ScaleType scaleType;
    @Bind(R.id.bt)
    Button bt;
    @Bind(R.id.bt_refresh)
    Button btRefresh;
    private int next;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_imageshowbig);
        ButterKnife.bind(this);
    }

    @Override
    public void findIDs() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt:
                if (scaleType != null)
                    if (scaleType.ordinal() == ImageView.ScaleType.values().length - 1)
                        next = 0;
                    else
                        next = scaleType.ordinal() + 1;
                else
                    next = 0;
                iv.setScaleType(scaleType = ImageView.ScaleType.values()[next]);
                bt.setText(scaleType.toString());
                MeasureUtils.measureImage(iv, new MeasureUtils.ImageListener() {
                    @Override
                    public void imageShowProperty(ImageView iv,float left, float top, int imageShowX, int imageShowY) {
                        System.out.println("scaleType :"+iv.getScaleType()+"\t left:"+left+" \ttop："+top+" \timageShowX："+imageShowX+"\timageShowY："+imageShowY);
                    }
                });
                break;
            case R.id.bt_refresh:
                if (scaleType != null) {
                    iv.setScaleType(scaleType);
                    bt.setText(scaleType.toString());
                    MeasureUtils.measureImage(iv, new MeasureUtils.ImageListener() {
                        @Override
                        public void imageShowProperty(ImageView iv,float left, float top, int imageShowX, int imageShowY) {
                            System.out.println("scaleType :"+iv.getScaleType()+"\t left:"+left+" \ttop："+top+" \timageShowX："+imageShowX+"\timageShowY："+imageShowY);
                        }
                    });
                }
                break;
        }
    }

}
