package com.example.mylib_test.activity.animal;
import android.widget.SeekBar;

import com.example.mylib_test.R;
import com.zone.lib.base.activity.BaseActivity;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;
import com.zone.lib.utils.data.check.EmptyCheck;
import butterknife.BindView;
import butterknife.ButterKnife;
import view.DampingView;


/**
 * Created by fuzhipeng on 16/7/29.
 */
public class DampingActivity extends BaseActivity {
    SeekBar maxXSeekBar;
    SeekBar maxYSeekBar;
    SeekBar dampingRadioSeekBar;
    @BindView(R.id.bt_wave)
    DampingView dampingView;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_animal_damping);
        ButterKnife.bind(this);
        maxXSeekBar = (SeekBar) findViewById(R.id.maxX);
        maxXSeekBar.setOnSeekBarChangeListener(new WaveActivity.SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dampingView.setMaxX(progress);
            }
        });

        maxYSeekBar = (SeekBar) findViewById(R.id.maxY);
        maxYSeekBar.setOnSeekBarChangeListener(new WaveActivity.SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dampingView.setMaxY(progress);
            }
        });

        dampingRadioSeekBar = (SeekBar) findViewById(R.id.dampingRadio);
        dampingRadioSeekBar.setOnSeekBarChangeListener(new WaveActivity.SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dampingView.setDampingRadio(progress);
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
