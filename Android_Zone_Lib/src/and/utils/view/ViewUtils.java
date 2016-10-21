package and.utils.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by fuzhipeng on 16/9/29.
 */

public class ViewUtils {


    //递归view 所有parent 知道 rootView  设置clipClildren;
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
     *
     * @param view
     * @param flowLayout 因为我的flowLayout 用lib库 lib库要在用flowlayout就循环了 坑爹 所以 加了这个参数;传一个flowLayout即可
     * @param xParts
     * @param yParts
     * @return  ImageView[] 如果为null 则证明 失败;
     */
    public static ImageView[] clipView(ImageView view,ViewGroup flowLayout, int xParts, int yParts) {
        ViewGroup parentView = (ViewGroup) view.getParent();
        if (parentView== null)
            return null;

        Bitmap viewBmp = getCacheBitmap(view);
        int bmpWidth = viewBmp.getWidth() / xParts;
        int bmpHeight = viewBmp.getHeight() / yParts;

        flowLayout.setLayoutParams(getLayoutParams(view));
        ImageView[] imageViews=new ImageView[xParts*yParts];
        int i=0;
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

    public static Bitmap getCacheBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache(true);
    }
}
