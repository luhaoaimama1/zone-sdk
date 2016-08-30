package com.example.mylib_test.activity.animal;

import android.view.View;
import android.widget.Button;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.animal.helper.PopXfermode;
import com.example.mylib_test.activity.animal.viewa.XfermodeView;

import and.base.activity.BaseActivity;


/**
 * Created by Administrator on 2016/3/21.
 */
public class XfermodeActivity extends BaseActivity {
    private View rl_main;
    private Button bt_pop;
    private PopXfermode popXfermode;
    private XfermodeView xfermodeView;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_animal_xfermode);
    }

    @Override
    public void findIDs() {
        rl_main=findViewById(R.id.rl_main);
        bt_pop=(Button)findViewById(R.id.bt_pop);
        xfermodeView=(XfermodeView)findViewById(R.id.xfermodeView);
    }

    @Override
    public void initData() {
        popXfermode=new PopXfermode(this,xfermodeView,bt_pop);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_pop:
                popXfermode.show();
            break;
        }
        super.onClick(v);
    }
}
