package and.utils.draw;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/5/10.
 *
 * get specially point from View or Bitmap;
 */
public class DrawBind {
    private WeakReference <View> softView;
    private WeakReference <Bitmap> softBt;
    private int mode=0;
    private static final int VIEW=1,BITMAP=2;

    public void bingView(View view){
        softView=new WeakReference<View>(view);
        mode=VIEW;
    }
    public void bingBitmap(Bitmap bt){
        softBt=new WeakReference<Bitmap>(bt);
        mode=BITMAP;
    }

    public float[] leftTop(){
        return calculate(0);
    }
    public float[] rightTop(){
        return calculate(2);
    }
    public float[] rightBottom(){
        return calculate(4);
    }

    public float[] leftBottom(){
        return calculate(6);
    }

    public float[] centerTop(){
        return calculate(1);
    }
    public float[] centerRight(){
        return calculate(3);
    }
    public float[] centerLeft(){
        return calculate(5);
    }
    public float[] centerBottom(){
        return calculate(7);
    }
    public float[] center(){
        return calculate(8);
    }
    public RectF getRect(){
        float[] leftTop = leftTop();
        float[] rightBottom=rightBottom();
        return new RectF(leftTop[0],leftTop[1],rightBottom[0],rightBottom[1]);
    }
    /**
     * 0为00
     * 0---1---2
     * |       |
     * 7   8   3
     * |       |
     * 6---5---4
     */
    private float[] calculate(int point){
        switch (mode){
            case VIEW:
                if(softView!=null){
                    View result = softView.get();
                    return calculate(point,result.getMeasuredWidth(),result.getMeasuredHeight());
                }
                break;
            case BITMAP:
                if(softBt!=null){
                    Bitmap result = softBt.get();
                    return calculate(point,result.getWidth(),result.getHeight());
                }
                break;
        }
        return null;
    }
    /**
     * 0为00
     * 0---1---2
     * |       |
     * 7   8   3
     * |       |
     * 6---5---4
     */
    private float[] calculate(int point,int width,int height){
        float[] result=new float[2];
        switch (point){
            case 0:
                result[0]=0;
                result[1]=0;
                break;
            case 1:
                result[0]=width/2;
                result[1]=0;
                break;
            case 2:
                result[0]=width;
                result[1]=0;
                break;
            case 3:
                result[0]=width;
                result[1]=height/2;
                break;
            case 4:
                result[0]=width;
                result[1]=height;
                break;
            case 5:
                result[0]=width/2;
                result[1]=height;
                break;
            case 6:
                result[0]=0;
                result[1]=height;
                break;
            case 7:
                result[0]=0;
                result[1]=height/2;
                break;
            case 8:
                result[0]=width/2;
                result[1]=height/2;
                break;
        }
        return result;
    }
}
