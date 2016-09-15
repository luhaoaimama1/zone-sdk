package com.example.mylib_test.activity.animal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ClipDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.mylib_test.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import view.FlowLayout_Zone;

public class Animal_MainActivity extends Activity implements OnClickListener {
    @Bind(R.id.bt_clip_drawable)
    Button btClipDrawable;
    private FlowLayout_Zone flowLayoutZone1;
    private ImageView iv_iv;
    private Bitmap bt;
    private ScrollView sv;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_animaltest);
        ButterKnife.bind(this);
        flowLayoutZone1 = (FlowLayout_Zone) findViewById(R.id.flowLayoutZone1);
        iv_iv = (ImageView) findViewById(R.id.iv_iv);
        sv = (ScrollView) findViewById(R.id.sv);
        testViewTreeObserverListener();
        bt = BitmapFactory.decodeResource(getResources(), R.drawable.abcd);

        ClipDrawable drawable = (ClipDrawable) btClipDrawable.getBackground();
        drawable.setLevel(5000);//0-10000
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.animal:
                startActivity(new Intent(this, AniPro.class));
                break;
            case R.id.viewHelperTest:
                startActivity(new Intent(this, ViewHelperTestActivity.class));
                break;
            case R.id.color:
                startActivity(new Intent(this, ColorMarixTry.class));
                break;
            case R.id.bt_canvas:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "layer"));
                break;
            case R.id.bt_PorterDuff:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "porterDuff"));
                break;
            case R.id.bt_Xfermode:
                startActivity(new Intent(this, XfermodeActivity.class));
                break;
            case R.id.bt_XfermodeUtils:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_XfermodeUtils"));
                break;
            case R.id.bt_imageBigTest:
                startActivity(new Intent(this, ImageShowBigActivity.class));
                break;
            case R.id.bt_shader:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "shader"));
                break;
            case R.id.bt_surfaceView:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "surfaceView"));
                break;
            case R.id.bt_surface:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_surface"));
                break;
            case R.id.bt_MatrixPre:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_MatrixPre"));
                break;
            case R.id.bt_bitmap:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_bitmap"));
                break;
            case R.id.bt_bitmaptoRound:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_bitmaptoRound"));
                break;
            case R.id.bt_bitmaptoScale:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_bitmaptoScale"));
                break;
            case R.id.bt_bitmaptoRorate:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_bitmaptoRorate"));
                break;
            case R.id.bt_Pixels:
                startActivity(new Intent(this, PixelsAcitivity.class));
                break;
            case R.id.bt_customAni:
                startActivity(new Intent(this, CustomAniActivity.class));
                break;
            case R.id.bt_bitmapRecyle:
                bitmapRecyleTest();
                break;
            case R.id.bt_draw:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_draw"));
                break;
            case R.id.bt_matrixMethod:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_matrixMethod"));
                break;
//            遮罩的几种方式
            case R.id.bt_clip_shader:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "shader"));
                break;
//            path
            case R.id.bt_bezier:
                startActivity(new Intent(this, PathActivity.class).putExtra("type", "QQBizierView"));
                break;
            case R.id.bt_FlexibleBall:
                startActivity(new Intent(this, PathActivity.class).putExtra("type", "FlexibleBall"));
                break;

            default:
                break;
        }
    }

    private void bitmapRecyleTest() {
        if (bt != null) {
            //		ImageLoader.getInstance().loadImageSync(ImageLoaderURIUtils.transformURI(R.drawable.abcd+"", Type.Drawable));
            Log.i("hei", "bitmapRecyleTest" + (bt.isRecycled() == true ? "回收成功" : "回收失败"));
            iv_iv.setImageBitmap(bt);
            rec(bt);
            System.gc();
            Log.i("hei", "bitmapRecyleTest" + (bt.isRecycled() == true ? "回收成功" : "回收失败"));
        }

    }

    private void rec(Bitmap bt1) {
        bt1.recycle();
        Log.i("hei", "rec" + (bt1.isRecycled() == true ? "回收成功" : "回收失败"));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void testViewTreeObserverListener() {
        sv.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                System.out.println("ViewTreeObserver:OnDrawListener");
            }
        });
        ViewTreeObserver.OnPreDrawListener pre = new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                sv.getViewTreeObserver().removeOnPreDrawListener(this);
                System.out.println("ViewTreeObserver:onPreDraw");
                //todo  这个什么鬼
                return false;
            }
        };
        sv.getViewTreeObserver().addOnPreDrawListener(pre);
        sv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                System.out.println("ViewTreeObserver:OnGlobalLayoutListener");
            }
        });
        sv.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                System.out.println("ViewTreeObserver:OnGlobalFocusChangeListener");
            }
        });
        sv.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                System.out.println("ViewTreeObserver:onScrollChanged");
            }
        });
        sv.getViewTreeObserver().addOnTouchModeChangeListener(new ViewTreeObserver.OnTouchModeChangeListener() {
            @Override
            public void onTouchModeChanged(boolean isInTouchMode) {
                System.out.println("ViewTreeObserver:OnTouchModeChangeListener");
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
