package com.zone.lib.utils.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

public class ViewShot {

    /**
     * @param view
     * @return RGB8888
     */
    public static Bitmap getCacheBitmap(View view) {
        return getCacheBitmap(view, null);
    }

    /**
     *
     * @param view
     * @param config
     * @return
     */
    public static Bitmap getCacheBitmap(View view, Bitmap.Config config) {
        view.setDrawingCacheEnabled(true);
        Bitmap cacheBitmap = view.getDrawingCache();
        if (cacheBitmap == null)
            return failLog(view);
        Bitmap bitmap;
        if (config == null)
            bitmap= Bitmap.createBitmap(cacheBitmap);
        else{
            bitmap = Bitmap.createBitmap(cacheBitmap.getWidth(), cacheBitmap.getHeight(),
                    config);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(cacheBitmap,0,0,null);
        }
        view.destroyDrawingCache();
        return bitmap;
    }

    /**
     * @param view
     * @return
     * @deprecated use {@link #getCacheBitmap(View,Bitmap.Config)}
     * <br>感觉这个不好  会清除一些状态</br>
     * 仅仅为了得到的位图是RGB 565吧
     * 但是 结果来看 大部分还是RGB8888 要和  View.mUse32BitDrawingCache相关
     */
    public static Bitmap getCacheBitmap_ConifgByWindow(View view) {
        // 将一个View转化成一张图片
        view.clearFocus(); // 清除视图焦点
        view.setPressed(false);// 将视图设为不可点击

        boolean willNotCache = view.willNotCacheDrawing(); // 返回视图是否可以保存他的画图缓存
        view.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation //将视图在此操作时置为透明
        int color = view.getDrawingCacheBackgroundColor(); // 获得绘制缓存位图的背景颜色
        view.setDrawingCacheBackgroundColor(0); // 设置绘图背景颜色
        if (color != 0) { // 如果获得的背景不是黑色的则释放以前的绘图缓存
            view.destroyDrawingCache(); // 释放绘图资源所使用的缓存
        }
        view.buildDrawingCache(); // 重新创建绘图缓存，此时的背景色是黑色
        Bitmap cacheBitmap = view.getDrawingCache(); // 将绘图缓存得到的,注意这里得到的只是一个图像的引用
        if (cacheBitmap == null)
            return failLog(view);
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap); // 将位图实例化
        // Restore the view //恢复视图
        view.destroyDrawingCache();// 释放位图内存
        view.setWillNotCacheDrawing(willNotCache);// 返回以前缓存设置
        view.setDrawingCacheBackgroundColor(color);// 返回以前的缓存颜色设置
        return bitmap;
    }

    private static Bitmap failLog(View v) {
        Log.e("view.ProcessImageToBlur", "failed getViewBitmap(" + v + ")",
                new RuntimeException());
        return null;

    }

    public static class OtherView {
        /**
         * 截取scrollview的屏幕
         **/
        public static Bitmap getBitmapByScrollView(ScrollView scrollView) {
            int h = 0;
            Bitmap bitmap = null;
            // 获取listView实际高度
            for (int i = 0; i < scrollView.getChildCount(); i++)
                h += scrollView.getChildAt(i).getHeight();
            Log.d(TAG, "实际高度:" + h);
            Log.d(TAG, " 高度:" + scrollView.getHeight());
            // 创建对应大小的bitmap
            bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                    Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            scrollView.draw(canvas);
            return bitmap;
        }

        private static String TAG = "Listview and ScrollView item 截图:";

        /**
         * 截图listview
         **/
        public static Bitmap getBitmapByListView(ListView listView) {
            int h = 0;
            Bitmap bitmap = null;
            // 获取listView实际高度
            for (int i = 0; i < listView.getChildCount(); i++) {
                h += listView.getChildAt(i).getHeight();
            }
            Log.d(TAG, "实际高度:" + h);
            Log.d(TAG, "list 高度:" + listView.getHeight());
            // 创建对应大小的bitmap
            bitmap = Bitmap.createBitmap(listView.getWidth(), h,
                    Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            listView.draw(canvas);
            return bitmap;
        }
    }
}