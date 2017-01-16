package com.example.mylib_test.activity.animal;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.mylib_test.R;

import and.utils.image.WaveHelper;
import and.utils.image.compress2sample.SampleUtils;
import and.utils.image.BitmapComposer;
import view.WaveView2;

//参考:https://github.com/race604/WaveLoading
public class WaveActivity extends Activity {
    SeekBar mLevelSeekBar;
    SeekBar mAmplitudeSeekBar;
    SeekBar mLengthSeekBar;
    SeekBar mSpeedSeekBar;
    WaveView2 waveView;
    SeekBar speed_OffsetXLength;
    private ImageView image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_animal_wave);
        waveView = (WaveView2) findViewById(R.id.bt_wave);
        mLevelSeekBar = (SeekBar) findViewById(R.id.level_seek);
        mLevelSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveView.setLevel(((float) progress) / seekBar.getMax());
            }
        });

        mAmplitudeSeekBar = (SeekBar) findViewById(R.id.amplitude_seek);
        mAmplitudeSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveView.setWaveAmplitude(progress);
            }
        });

        mLengthSeekBar = (SeekBar) findViewById(R.id.length_seek);
        mLengthSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveView.setWaveLength(progress);
            }
        });

        mSpeedSeekBar = (SeekBar) findViewById(R.id.speed_seek);
        mSpeedSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveView.setWaveSpeed(progress);
            }
        });
        speed_OffsetXLength = (SeekBar) findViewById(R.id.speed_OffsetXLength);
        speed_OffsetXLength.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveView.setOffsetXRadioOfLength(((float) progress) / seekBar.getMax());
            }
        });

        image2 = (ImageView) findViewById(R.id.image2);
        image2.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bt = SampleUtils.load(WaveActivity.this, R.drawable.aaaaaaaaaaaab)
                        .bitmap();

                ColorMatrix colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(0);

                BitmapComposer bitmapComposer = BitmapComposer.newComposition(bt.getWidth(), bt.getHeight(), Bitmap.Config.ARGB_8888);
                Matrix first = new Matrix();
                first.postTranslate(0, -20);
                new WaveHelper(bt.getWidth(), bt.getHeight(), new WaveHelper.RefreshCallback() {
                    public BitmapComposer.Layer mb;
                    public BitmapComposer.Layer kb;

                    @Override
                    public void refresh(Bitmap wave) {
                        Bitmap render = bitmapComposer.clear()
                                .newLayer(kb=BitmapComposer.Layer.bitmap(bt)
                                        .colorFilter(new ColorMatrixColorFilter(colorMatrix)))
                                .newLayer(
                                        mb=BitmapComposer.Layer.bitmap(bt)
//                                                .colorFilter(new ColorMatrixColorFilter(colorMatrix))
                                                .mask(wave, PorterDuff.Mode.DST_IN)
//                                                .matrix(first)
                                )
                                .render();
                        image2.setImageBitmap(render);
                    }
                });

            }
        });


    }

    private static class SimpleOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // Nothing
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // Nothing
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // Nothing
        }
    }

}
