package and.utils.view;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * Created by fuzhipeng on 16/9/29.
 */

public class ViewUtils {

    // 防止Android过快点击造成多次事件
    public static void clickThrottleFirst(View view, final View.OnClickListener mOnClickListener
            , final long windowDuration, final TimeUnit unit){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long clickTime = 0;
                //因为第一次的值一定大于 你设置的值 所以第一次会走；
                if(System.currentTimeMillis()-clickTime>=unit.toMillis(windowDuration)){
                    mOnClickListener.onClick(v);
                    //todo 有bug 如何能记录下时间
                    clickTime = System.currentTimeMillis();
                }

            }
        });
    }

    /**
     * 递归view 所有parent 知道 rootView  设置clipClildren;
     * <br>如果遇到 某个一parent找不到为null 那么说明 布局没加载好；在布局加载好的位置使用此方法 ;</br>
     * 不建议在这里用 如果实在没办法在用:actvitiy:onWindowFocusChanged这个生命周期
     * @param view
     * @param clipChildren  false就是 超越边界也能显示  true超越边界的会裁剪吊
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

        Bitmap viewBmp = ViewShot.getCacheBitmap(view);
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

    /**
     * 迭代找到最顶层  如果是在Activity则等于DectorView;
     * @param view
     * @return
     */
    public static View getRootView(View view){
        return view.getRootView();
    }

}
