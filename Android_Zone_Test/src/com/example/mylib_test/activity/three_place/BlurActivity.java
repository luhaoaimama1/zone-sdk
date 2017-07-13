package com.example.mylib_test.activity.three_place;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.mylib_test.R;
import com.zone.lib.utils.image.BitmapUtils;
import jp.wasabeef.blurry.Blurry;

public class BlurActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_fastblur);
        ((ImageView) findViewById(R.id.right_bottom)).setImageBitmap(
                BitmapUtils.fastblur(BlurActivity.this, BitmapFactory
                        .decodeResource(getResources(), R.drawable.demo), 20));
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long startMs = System.currentTimeMillis();
                Blurry.with(BlurActivity.this)
                        .radius(25)
                        .sampling(1)
                        .color(Color.argb(66, 0, 255, 255))//Color Filter
                        .async()
                        .capture(findViewById(R.id.right_top))
                        .into((ImageView) findViewById(R.id.right_top));

                Blurry.with(BlurActivity.this)
                        .radius(10)//模糊半径
                        .sampling(8)//采样 原图像素的 几分之一
                        .async()//会不耗时间？
                        .capture(findViewById(R.id.right_bottom))//把一个控件的图照 下来  background 还src都一样他是截下来的
                        .into((ImageView) findViewById(R.id.right_bottom));//放到另一个控件(ImageView)

                Blurry.with(BlurActivity.this)
                        .radius(25)
                        .sampling(1)
                        .color(Color.argb(66, 255, 255, 0))
//            .async()
                        .capture(findViewById(R.id.left_bottom))
                        .into((ImageView) findViewById(R.id.left_bottom));

                Log.d(getString(R.string.app_name),
                        "TIME " + String.valueOf(System.currentTimeMillis() - startMs) + "ms");
            }
        });

        findViewById(R.id.button).setOnLongClickListener(new View.OnLongClickListener() {

            private boolean blurred = false;

            @Override
            public boolean onLongClick(View v) {
                if (blurred) {
                    Blurry.delete((ViewGroup) findViewById(R.id.content));
                } else {
                    long startMs = System.currentTimeMillis();
                    Blurry.with(BlurActivity.this)
                            .radius(25)
                            .sampling(2)
                            .async()
                            .animate(500)
                            .onto((ViewGroup) findViewById(R.id.content));//这个则是这个父控件里的任何控件(不仅仅是ImageView)都变模糊了
                    Log.d(getString(R.string.app_name),
                            "TIME " + String.valueOf(System.currentTimeMillis() - startMs) + "ms");
                }

                blurred = !blurred;
                return true;
            }
        });
    }
}
