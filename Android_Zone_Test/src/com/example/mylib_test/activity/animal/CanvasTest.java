package com.example.mylib_test.activity.animal;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.animal.utils.test.MatrixStudy;
import com.example.mylib_test.activity.animal.viewa.BaseDraw;
import com.example.mylib_test.activity.animal.viewa.Canvas1;
import com.example.mylib_test.activity.animal.viewa.DrawTextView;
import com.example.mylib_test.activity.animal.viewa.MatrixMethod;
import com.example.mylib_test.activity.animal.viewa.MatrixView;
import com.example.mylib_test.activity.animal.viewa.SimpleDraw;
import com.example.mylib_test.activity.animal.viewa.XfermodeView2;

import and.utils.image.BitmapUtils;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class CanvasTest extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String type = getIntent().getStringExtra("type");
        if ("layer".equals(type)) {
            setContentView(new Canvas1(this));
        }
        if ("porterDuff".equals(type)) {
            setContentView(R.layout.a_porterduff_xfermode);
        }

        if ("shader".equals(type)) {
            setContentView(R.layout.a_shader);
        }
        if ("bt_surface".equals(type)) {
            setContentView(new SimpleDraw(this));
        }
        if ("bt_matrixMethod".equals(type)) {
            setContentView(new MatrixMethod(this));
        }
        if ("bt_MatrixPre".equals(type)) {
            setContentView(new MatrixView(this));
        }
        if ("bt_MatrixStudy".equals(type)) {
            setContentView(R.layout.a_animal_matrixstudy);
            initMatrixStudy();
        }
        if ("bt_bitmap".equals(type)) {
            setContentView(R.layout.a_btimap_copy);
        }
        if ("bt_draw".equals(type)) {
            setContentView(new BaseDraw(this));
        }
        if ("bt_bitmaptoRound".equals(type)) {
            ImageView iv = new ImageView(this);
            iv.setImageBitmap(BitmapUtils.toRoundBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.abcd), true));
            iv.setBackgroundColor(Color.YELLOW);
            setContentView(iv);
        }
        if ("bt_bitmaptoRorate".equals(type)) {
            ImageView iv = new ImageView(this);
            iv.setImageBitmap(BitmapUtils.rotateBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.abcd), 45, true));
            iv.setBackgroundColor(Color.YELLOW);
            setContentView(iv);
        }
        if ("bt_bitmaptoScale".equals(type)) {
            ImageView iv = new ImageView(this);
            iv.setImageBitmap(BitmapUtils.scaleBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.abcd), 0.5F, 0.5F, true));
            iv.setScaleType(ScaleType.CENTER);
            iv.setBackgroundColor(Color.YELLOW);
            setContentView(iv);
        }
        if ("bt_XfermodeUtils".equals(type)) {
            setContentView(new XfermodeView2(this));
        }
        if ("bt_drawText".equals(type)) {
            setContentView(new DrawTextView(this));
        }


    }

    float[] values = new float[9];
    EditText[] ed = new EditText[9];

    private void initMatrixStudy() {
        Matrix matrix = new Matrix();
        matrix.getValues(values);

        for (int i = 0; i < values.length; i++) {
            try {
                int id = R.id.class.getField("ed_" + i).getInt(null);
                ed[i] = (EditText) findViewById(id);
                final int finalI = i;
                ed[i].addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        try {
                            float number = Float.parseFloat(s.toString());
                            ((MatrixStudy)findViewById(R.id.ms)).set(finalI,number);
                        } catch (Exception e) {
                        };
                    }
                });
                ed[i].setText(values[i] + "");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset:
                reset();
                break;

            default:
                break;
        }
    }

    private void reset() {
        Matrix matrix = new Matrix();
        matrix.getValues(values);
        for (int i = 0; i < values.length; i++) {
            ed[i].setText(values[i] + "");
        }
    }
}
