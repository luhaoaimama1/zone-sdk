package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mylib_test.activity.animal.utils.CameraInvert;
import com.example.mylib_test.activity.animal.utils.Layer;
import com.example.mylib_test.activity.animal.utils.LayerParent;
import com.example.mylib_test.activity.animal.utils.ZCamera;

/**
 * Created by fuzhipeng on 16/10/3.
 */

public class WheelGroup extends ViewGroup {
    private float circleLength = -500;

    public WheelGroup(Context context) {
        super(context);
    }

    public WheelGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WheelGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//        double itemDegress = 2 * Math.toDegrees(Math.atan2(getMeasuredWidth() / 2, circleLength));
        circleLength = (float) ((getChildAt(0).getMeasuredWidth() / 2) * Math.tan(Math.toRadians(15)));
        for (int i = 0; i < getChildCount(); i++) {
            canvas.save();
            Matrix matrix = new Matrix();
            View v = getChildAt(i);
            int center = (int) getChildCount() / 2;
            float rotateX = (float) 30 * (i - center);
            System.out.println("index:" + i + "\t rotateX:" + rotateX);
            CameraInvert matrix3D = new CameraInvert(new ZCamera());
            matrix3D.setPivot(v.getWidth() / 2, v.getHeight() / 2);
            matrix3D.rotateX_3D(rotateX,-circleLength);
            matrix3D.getMatrix(matrix);
//            matrix.set(
//
//            Layer.setPivot(v.getWidth() / 2, v.getHeight() / 2)
//                    .relativeZPosition(-circleLength)
//                    .attach(
//                            LayerParent.setPosition(v.getWidth() / 2, v.getHeight() / 2, circleLength)
//                                    .rotationX(rotateX)
//                    )
//            );
            canvas.concat(matrix);
            drawChild(canvas, v, getDrawingTime());
            canvas.restore();
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int heght = 0;
        for (int i = 0; i < getChildCount(); i++) {
            heght += getChildAt(i).getMeasuredHeight();
        }
        setMeasuredDimension(getChildAt(0).getMeasuredWidth(), heght);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int center = getChildCount() / 2;
        int height = getHeight() * (center ) / getChildCount();
        for (int i = 0; i < getChildCount(); i++) {
//            getChildAt(i).layout(0, getHeight() * i / getChildCount(), getChildAt(i).getMeasuredWidth(),
//                    getHeight() * i / getChildCount() + getChildAt(i).getMeasuredHeight());
            getChildAt(i).layout(0, height, getChildAt(i).getMeasuredWidth(),
                    height + getChildAt(i).getMeasuredHeight());
        }
    }
}
