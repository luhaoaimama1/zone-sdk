package com.example.mylib_test.activity.custom_view;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.frag_viewpager_expand.FramentSwitchAcitiviy;
import com.example.mylib_test.activity.frag_viewpager_expand.ViewPagerDisableScrollActivity;
import com.example.mylib_test.activity.frag_viewpager_expand.ViewPagerDisableScrollActivity2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomView_MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_custom_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_disable_scroll:
                startActivity(new Intent(this, ViewPagerDisableScrollActivity.class));
                break;
            case R.id.frg_scroll_:
                startActivity(new Intent(this, ViewPagerDisableScrollActivity2.class));
                break;
            case R.id.frammentSwitch:
                startActivity(new Intent(this, FramentSwitchAcitiviy.class));
                break;
            case R.id.bt_viewPagerIndicator:
//			startActivity(new Intent(this,ViewpagerIndicatorActivity.class));
                break;
            case R.id.arcMenu:
                startActivity(new Intent(this, ArcMenuTestActivity.class));
                break;
            case R.id.square_test:
                startActivity(new Intent(this, SquareTestActivity.class));
                break;
            case R.id.bt_hero1:
                startActivity(new Intent(this, AndroidHeroActivity.class).putExtra("type", "circle"));
                break;
            case R.id.bt_hero2:
                startActivity(new Intent(this, AndroidHeroActivity.class).putExtra("type", "circle2"));
                break;
            case R.id.bt_scroll:
                startActivity(new Intent(this, AndroidHeroActivity.class).putExtra("type", "scroll"));
                break;
            case R.id.chengcheng:
                startActivity(new Intent(this, ChengChengActivity.class));
                break;
            case R.id.ges:
                startActivity(new Intent(this, ChengChengActivity.class).putExtra("type", "ges"));
                break;
            case R.id.ges2:
                startActivity(new Intent(this, ChengChengActivity.class).putExtra("type", "ges2"));
                break;
            case R.id.par:
                startActivity(new Intent(this, ChengChengActivity.class).putExtra("type", "par"));
                break;
            case R.id.bt_fold:
                startActivity(new Intent(this, ChengChengActivity.class).putExtra("type", "bt_fold"));
                break;
            case R.id.bt_foldViewGroup:
                startActivity(new Intent(this, ChengChengActivity.class).putExtra("type", "bt_foldViewGroup"));
                break;
            case R.id.bt_wheel:
                startActivity(new Intent(this, ChengChengActivity.class).putExtra("type", "bt_wheel"));
                break;
            case R.id.bt_ScrollerView:
                startActivity(new Intent(this, ScrollerViewActivity.class));
                break;
            case R.id.bt_FramePaddding:
                startActivity(new Intent(this, ChengChengActivity.class).putExtra("type", "FrameLayoutPadding"));
                break;
            default:
                break;
        }
    }

}
