package com.example.mylib_test.activity.custom_view;

import android.widget.SeekBar;

import com.example.mylib_test.R;

import com.zone.lib.base.activity.BaseActivity;
import view.FlodLayout;
import view.FlodLayoutGroup;
import view.GestureView;
import view.GestureView2;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ChengChengActivity extends BaseActivity {
    @Override
    public void setContentView() {
        String type = getIntent().getStringExtra("type");
        if("ges".equals(type)){
            setContentView(new GestureView(this));
        }else if("ges2".equals(type)){
            setContentView(new GestureView2(this));
        } else if("par".equals(type)){
            setContentView(R.layout.nb_test);
        } else if("bt_foldViewGroup".equals(type)){
            setContentView(R.layout.flod_test2);
            flod2();
        } else if("bt_fold".equals(type)){
            setContentView(R.layout.flod_test);
            flod();
        } else if("bt_wheel".equals(type)){
            setContentView(R.layout.a_wheel);
        } else
            setContentView(R.layout.a_cheng);
    }

    private void flod2() {
        FlodLayoutGroup flod= (FlodLayoutGroup) findViewById(R.id.flod);
        SeekBar bar= (SeekBar) findViewById(R.id.seekBar);
        bar.setMax(100);
        bar.setProgress(50);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                flod.setDepth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        SeekBar seekBar2= (SeekBar) findViewById(R.id.seekBar2);
        seekBar2.setMax(100);
        seekBar2.setProgress(50);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                flod.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void flod() {
        FlodLayout flod= (FlodLayout) findViewById(R.id.flod);
        SeekBar bar= (SeekBar) findViewById(R.id.seekBar);
        bar.setMax(100);
        bar.setProgress(50);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                flod.setDepth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        SeekBar seekBar2= (SeekBar) findViewById(R.id.seekBar2);
        seekBar2.setMax(100);
        seekBar2.setProgress(50);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                flod.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
}
