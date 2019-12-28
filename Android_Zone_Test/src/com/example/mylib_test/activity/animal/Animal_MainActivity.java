package com.example.mylib_test.activity.animal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
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

import com.example.mylib_test.LogApp;
import com.example.mylib_test.R;

import com.zone.lib.utils.activity_fragment_ui.ActivityTopViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.zone.lib.utils.data.file2io2data.FileUtils;
import com.zone.lib.utils.data.file2io2data.SDCardUtils;
import com.zone.lib.utils.image.BitmapUtils;
import com.zone.lib.utils.image.compress2sample.CompressUtils;
import com.zone.lib.utils.image.compress2sample.SampleUtils;
import com.zone.view.FlowLayout;

import java.io.File;
import java.util.Observable;

public class Animal_MainActivity extends Activity implements OnClickListener {
    @BindView(R.id.bt_clip_drawable)
    Button btClipDrawable;
    private FlowLayout flowLayoutZone1;
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
        flowLayoutZone1 = (FlowLayout) findViewById(R.id.flowLayoutZone1);
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
            case R.id.bt_big_save:
                saveBigBitmap();
                break;
            case R.id.tv:
                int[] loWindow =new int[2];
                int[] loScreen =new int[2];
                flowLayoutZone1.getLocationInWindow(loWindow);
                flowLayoutZone1.getLocationOnScreen(loScreen);
                ActivityTopViewUtils.getActivityRootView(this);
                break;
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
            case R.id.bt_imageCenter:
                startActivity(new Intent(this, ImageCenterActivity.class));
                break;
            case R.id.bt_shader:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "shader"));
                break;
            case R.id.bt_surface:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_surface"));
                break;
            case R.id.bt_MatrixPre:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_MatrixPre"));
                break;
            case R.id.bt_MatrixStudy:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_MatrixStudy"));
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
            case R.id.bt_LightingColorFilter:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_LightingColorFilter"));
                break;
            case R.id.bt_drawText:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_drawText"));
                break;
            case R.id.bt_drawTextUtils:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_drawTextUtils"));
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
            case R.id.bt_glow:
                startActivity(new Intent(this, CanvasTest.class).putExtra("type", "bt_glow"));
                break;
            case R.id.bt_damping:
                startActivity(new Intent(this, DampingActivity.class));
                break;
            case R.id.bt_PathMeasure:
                startActivity(new Intent(this, PathMeasureActivity.class));
                break;
            case R.id.bt_wave:
                startActivity(new Intent(this, WaveActivity.class));
                break;
            case R.id.bt_svg:
                startActivity(new Intent(this, SVGActivity.class));
                break;
            case R.id.bt_Animator:
                startActivity(new Intent(this, AnimatorActivity.class));
                break;
            case R.id.bt_spannable:
                startActivity(new Intent(this, TextViewLinkActivity.class));
                break;

            default:
                break;
        }
    }

    private void saveBigBitmap() {
        Bitmap bt = SampleUtils.load(this, R.drawable.abcd).bitmap();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ActivityManager mActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
                //获得MemoryInfo对象
                ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo() ;
                //获得系统可用内存，保存在MemoryInfo对象上
                mActivityManager.getMemoryInfo(memoryInfo) ;
                long memSize = memoryInfo.availMem;
//                int width = (int) (Math.sqrt(memSize / 4)) - 8000;
                //x的平方*4(rgb888 每个像素 4byte) =可用像素
                int width = (int) (Math.sqrt(memSize / 4))-90000 ;

                //经过测试  在创建的时候就已经是申请很多内存了
                Bitmap bitmap=Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                float scaleWidth = width * 1F / bt.getWidth();
                float scaleHeight = width * 1F / bt.getHeight();
                //用最小的缩放
                float realScale = scaleWidth <= scaleHeight ? scaleWidth : scaleHeight;
                Matrix mar = new Matrix();
                canvas.save();
                mar.postTranslate((width - bt.getWidth()) * 1f / 2, (width - bt.getHeight()) * 1f / 2);
                mar.postScale(realScale, realScale, width * 1f / 2, width * 1f / 2);
                canvas.drawBitmap(bt, mar, null);
                canvas.restore();

                LogApp.INSTANCE.d("bitmap 实例:"+bitmap);
                File file =FileUtils.getFile(SDCardUtils.getSDCardDir(),"Zone", "abc.png");

                CompressUtils.saveBitmap(file.getPath(), bitmap);

            }
        }).start();
    }

    private void bitmapRecyleTest() {
        if (bt != null) {
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

        sv.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("ViewTreeObserver:post");
            }
        });
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
                //todo  true的时候 能看到页面 false直接卡死了
                return true;
            }
        };
        sv.getViewTreeObserver().addOnPreDrawListener(pre);
//        sv.getViewTreeObserver().dispatchOnGlobalLayout();

        sv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                System.out.println("ViewTreeObserver:OnGlobalLayoutListener"
                        + "->可见性：" + (sv.getVisibility() == View.VISIBLE ? "可见" : "不可见"));
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        System.out.println("ViewTreeObserver_onWindowFocusChanged_hasFocus:" + (hasFocus ? "true" : "false"));
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
