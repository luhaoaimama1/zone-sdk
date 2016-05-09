package com.example.mylib_test.activity.three_place;

import other_project.pinyin_sidebar.SideBarActivity;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.custom_view.SwtichButtonActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ThirdParty_MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_thirdparty);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageLoader:
                startActivity(new Intent(this, ImageLoaderActivity.class));
                break;
            case R.id.imageLoaderGrid:
                startActivity(new Intent(this, ImageLoaderGridActivity.class));
                break;
            case R.id.sideBar:
                startActivity(new Intent(this, SideBarActivity.class));
                break;
            case R.id.google_pull:
                startActivity(new Intent(this, GooglePullActvity.class));
                break;
            case R.id.bt_recycler_Linear:
                startActivity(new Intent(this, RecyclerActivity.class).putExtra("type", "Linear"));
                break;
            case R.id.bt_recycler_Grid:
                startActivity(new Intent(this, RecyclerActivity.class).putExtra("type", "Grid"));
                break;
            case R.id.bt_recycler_StaggeredGrid:
                startActivity(new Intent(this, RecyclerActivity.class).putExtra("type", "StaggeredGrid"));
                break;
            case R.id.bt_pull2recycler:
                startActivity(new Intent(this, GooglePull2RecyclerActivity.class));
                break;

            case R.id.bt_UltraRefresh:
                startActivity(new Intent(this, UltraRefresh.class));
                break;

            case R.id.bt_glide:
                startActivity(new Intent(this, GildeActivity.class));
                break;
            case R.id.bt_swtichButton:
                startActivity(new Intent(this, SwtichButtonActivity.class));
                break;

            case R.id.bt_blur:
                startActivity(new Intent(this, BlurActivity.class));
                break;


            default:
                break;
        }
    }

}
