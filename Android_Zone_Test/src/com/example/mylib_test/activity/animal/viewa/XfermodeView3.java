package com.example.mylib_test.activity.animal.viewa;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.zone.lib.utils.view.DrawUtils;

/**
 * Created by Administrator on 2016/3/21.
 */
public class XfermodeView3 extends View {
    Paint paint = DrawUtils.getBtPaint();
    int height=200;
    Integer[] colors=new Integer[]{Color.BLUE,Color.RED,Color.BLACK,Color.YELLOW,Color.CYAN};

    List<Integer> saves=new ArrayList<>();
    public XfermodeView3(Context context) {
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        saves.clear();
        Canvas canvas2 = new Canvas();
        Bitmap bt = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        canvas2.setBitmap(bt);

        paint.setColor(Color.RED);
        canvas2.drawCircle(0,0,400,paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        for (int i = 0; i < colors.length; i++) {
            Rect rect = new Rect(0, 0, getWidth()-i*100, height-i*30);
            paint.setColor(colors[i]);
            saves.add(canvas2.saveLayer(new RectF(0F, 0F, getWidth(), getHeight()), paint,Canvas.ALL_SAVE_FLAG));
            System.out.println("save"+i+":"+saves.get(i));
            canvas2.drawRect(rect,paint);
//            canvas2.restore();
        }
        System.out.println("saveCounts:"+canvas2.getSaveCount());

//        for (int i = saves.size() - 1; i >= 0; i--) {
//            canvas2.translate(0,(saves.size()-i-1)*height);
//            canvas2.restoreToCount(saves.get(i));
//        }
//        canvas2.restoreToCount(saves.get(saves.size()-1));
//        canvas2.restoreToCount(saves.get(saves.size()-2));
//        canvas2.restoreToCount(saves.get(saves.size()-3));
        canvas2.restoreToCount(saves.get(0));
        System.out.println("save final:"+saves.get(0));
        canvas.drawBitmap(bt,0,0,null);

    }
}
