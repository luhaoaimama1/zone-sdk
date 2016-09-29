package com.example.mylib_test.activity.custom_view;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.frag_viewpager_expand.FramentSwitchAcitiviy;
import com.example.mylib_test.activity.frag_viewpager_expand.ViewPagerDisableScrollActivity;
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
            case R.id.frammentSwitch:
                startActivity(new Intent(this, FramentSwitchAcitiviy.class));
                break;
            case R.id.bt_viewPagerIndicator:
//			startActivity(new Intent(this,ViewpagerIndicatorActivity.class));
                break;
            case R.id.label_test:
                startActivity(new Intent(this, LabelTestActivity.class));
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
            case R.id.flowZone:
                startActivity(new Intent(this, FlowZoneActivity.class));
                break;
            case R.id.gridZone:
                startActivity(new Intent(this, GridZoneActivity.class));
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
            default:
                break;
        }
    }

}
