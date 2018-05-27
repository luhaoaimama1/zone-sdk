package com.example.mylib_test.activity.three_place;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.custom_view.RippleViewActivity;

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
            case R.id.google_pull:
                startActivity(new Intent(this, GooglePullActvity.class));
                break;
            case R.id.bt_pull2recycler:
                startActivity(new Intent(this, GooglePull2RecyclerActivity.class));
                break;


            case R.id.bt_glide:
                startActivity(new Intent(this, GildeActivity.class));
                break;
            case R.id.bt_fresco:
                startActivity(new Intent(this, FrescoActivity.class));
                break;
            case R.id.bt_swtichButton:
                startActivity(new Intent(this, RippleViewActivity.class));
                break;

            case R.id.bt_blur:
                startActivity(new Intent(this, BlurActivity.class));
                break;


            default:
                break;
        }
    }

}
