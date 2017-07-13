package com.zone.lib.utils.view;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by fuzhipeng on 16/9/29.
 */

public class ViewUtils {

    /**
     * 递归view 所有parent 知道 rootView  设置clipClildren;
     * <br>如果遇到 某个一parent找不到为null 那么说明 布局没加载好；在布局加载好的位置使用此方法 ;</br>
     * 不建议在这里用 如果实在没办法在用:actvitiy:onWindowFocusChanged这个生命周期
     *
     * @param view
     * @param clipChildren false就是 超越边界也能显示  true超越边界的会裁剪吊
     */
    public static void recurrenceClipChildren(View view, boolean clipChildren) {
        ViewGroup parentView = (ViewGroup) view.getParent();
        ViewGroup rootView = (ViewGroup) view.getRootView();
        while (!parentView.equals(rootView)) {
            parentView.setClipChildren(clipChildren);
            parentView = (ViewGroup) parentView.getParent();
        }
        rootView.setClipChildren(clipChildren);
    }


    /**
     * @param view
     * @param flowLayout 因为我的flowLayout 用lib库 lib库要在用flowlayout就循环了 坑爹 所以 加了这个参数;传一个flowLayout即可
     * @param xParts
     * @param yParts
     * @return ImageView[] 如果为null 则证明 失败;
     */
    public static ImageView[] clipView(ImageView view, ViewGroup flowLayout, int xParts, int yParts) {
        ViewGroup parentView = (ViewGroup) view.getParent();
        if (parentView == null)
            return null;

        Bitmap viewBmp = ViewShot.getCacheBitmap(view);
        int bmpWidth = viewBmp.getWidth() / xParts;
        int bmpHeight = viewBmp.getHeight() / yParts;

        flowLayout.setLayoutParams(getLayoutParams(view));
        ImageView[] imageViews = new ImageView[xParts * yParts];
        int i = 0;
        for (int y = 0; y < yParts; y++) {
            for (int x = 0; x < xParts; x++) {
                imageViews[i] = new ImageView(view.getContext());
                //截图的感觉;
                imageViews[i].setImageBitmap(
                        Bitmap.createBitmap(viewBmp,
                                bmpWidth * x, bmpHeight * y,
                                bmpWidth, bmpHeight));
                flowLayout.addView(imageViews[i]);
                i++;
            }
        }
        int childIndex = parentView.indexOfChild(view);
        parentView.removeView(view);
        parentView.addView(flowLayout, childIndex);
        return imageViews;
    }


    //包含 margin weight gravity width,height
    //不包含padding 因为width就包含padding了;
    @org.jetbrains.annotations.Nullable
    public static ViewGroup.LayoutParams getLayoutParams(ImageView view) {
//        ViewGroup.LayoutParams marginLayoutParams = view.getLayoutParams();
//        ViewGroup.MarginLayoutParams marginlp = null;
//        if (marginLayoutParams instanceof ViewGroup.MarginLayoutParams)
//            marginlp = (ViewGroup.MarginLayoutParams) marginLayoutParams;
        return view.getLayoutParams();
    }

    /**
     * 迭代找到最顶层  如果是在Activity则等于DectorView;
     *
     * @param view
     * @return
     */
    public static View getRootView(View view) {
        return view.getRootView();
    }

    /**
     * 扩大View的触摸和点击响应范围,最大不超过其父View范围
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public static void expandViewTouchDelegate(final View view, final int top,
                                               final int bottom, final int left, final int right) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }
}
