package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import and.utils.view.DrawUtils;

public class RemoteControlMenu extends View {
    private  Paint mDeafultPaint;
    int mViewWidth, mViewHeight;
    Path up_p, down_p, left_p, right_p, center_p;
    Region up, down, left, right, center;

    Matrix mMapMatrix = null;

    int CENTER = 0;
    int UP = 1;
    int RIGHT = 2;
    int DOWN = 3;
    int LEFT = 4;
    int touchFlag = -1;
    int currentFlag = -1;

    MenuListener mListener = null;

    int mDefauColor = 0xFF4E5268;
    int mTouchedColor = 0xFFDF9C81;


    public RemoteControlMenu(Context context) {
        this(context, null);
    }

    public RemoteControlMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        up_p = new Path();
        down_p = new Path();
        left_p = new Path();
        right_p = new Path();
        center_p = new Path();

        up = new Region();
        down = new Region();
        left = new Region();
        right = new Region();
        center = new Region();

        mDeafultPaint= DrawUtils.getStrokePaint(Paint.Style.FILL);
        mDeafultPaint.setColor(mDefauColor);
        mDeafultPaint.setAntiAlias(true);

        mMapMatrix = new Matrix();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mMapMatrix.reset();
		
      	// 注意这个区域的大小
        Region globalRegion = new Region(-w, -h, w, h);
        int minWidth = w > h ? h : w;
        minWidth *= 0.8;

        int br = minWidth / 2;
        RectF bigCircle = new RectF(-br, -br, br, br);

        int sr = minWidth / 4;
        RectF smallCircle = new RectF(-sr, -sr, sr, sr);

        float bigSweepAngle = 84;
        float smallSweepAngle = -80;

        // 根据视图大小，初始化 Path 和 Region
        center_p.addCircle(0, 0, 0.2f * minWidth, Path.Direction.CW);
        center.setPath(center_p, globalRegion);

        right_p.addArc(bigCircle, -40, bigSweepAngle);
        right_p.arcTo(smallCircle, 40, smallSweepAngle);
        right_p.close();
        right.setPath(right_p, globalRegion);

        down_p.addArc(bigCircle, 50, bigSweepAngle);
        down_p.arcTo(smallCircle, 130, smallSweepAngle);
        down_p.close();
        down.setPath(down_p, globalRegion);

        left_p.addArc(bigCircle, 140, bigSweepAngle);
        left_p.arcTo(smallCircle, 220, smallSweepAngle);
        left_p.close();
        left.setPath(left_p, globalRegion);

        up_p.addArc(bigCircle, 230, bigSweepAngle);
        up_p.arcTo(smallCircle, 310, smallSweepAngle);
        up_p.close();
        up.setPath(up_p, globalRegion);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float[] pts = new float[2];
        pts[0] = event.getRawX();
        pts[1] = event.getRawY();
        mMapMatrix.mapPoints(pts);

        int x = (int) pts[0];
        int y = (int) pts[1];

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchFlag = getTouchedPath(x, y);
                currentFlag = touchFlag;
                break;
            case MotionEvent.ACTION_MOVE:
                currentFlag = getTouchedPath(x, y);
                break;
            case MotionEvent.ACTION_UP:
                currentFlag = getTouchedPath(x, y);
            	// 如果手指按下区域和抬起区域相同且不为空，则判断点击事件
                if (currentFlag == touchFlag && currentFlag != -1 && mListener != null) {
                    if (currentFlag == CENTER) {
                        mListener.onCenterCliched();
                    } else if (currentFlag == UP) {
                        mListener.onUpCliched();
                    } else if (currentFlag == RIGHT) {
                        mListener.onRightCliched();
                    } else if (currentFlag == DOWN) {
                        mListener.onDownCliched();
                    } else if (currentFlag == LEFT) {
                        mListener.onLeftCliched();
                    }
                }
                touchFlag = currentFlag = -1;
                break;
            case MotionEvent.ACTION_CANCEL:
                touchFlag = currentFlag = -1;
                break;
        }

        invalidate();
        return true;
    }

  	// 获取当前触摸点在哪个区域
    int getTouchedPath(int x, int y) {
        if (center.contains(x, y)) {
            return 0;
        } else if (up.contains(x, y)) {
            return 1;
        } else if (right.contains(x, y)) {
            return 2;
        } else if (down.contains(x, y)) {
            return 3;
        } else if (left.contains(x, y)) {
            return 4;
        }
        return -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mViewWidth / 2, mViewHeight / 2);

        // 获取测量矩阵(逆矩阵)
        if (mMapMatrix.isIdentity()) {
            canvas.getMatrix().invert(mMapMatrix);
        }

      	// 绘制默认颜色
        canvas.drawPath(center_p, mDeafultPaint);
        canvas.drawPath(up_p, mDeafultPaint);
        canvas.drawPath(right_p, mDeafultPaint);
        canvas.drawPath(down_p, mDeafultPaint);
        canvas.drawPath(left_p, mDeafultPaint);

      	// 绘制触摸区域颜色
        mDeafultPaint.setColor(mTouchedColor);
        if (currentFlag == CENTER) {
            canvas.drawPath(center_p, mDeafultPaint);
        } else if (currentFlag == UP) {
            canvas.drawPath(up_p, mDeafultPaint);
        } else if (currentFlag == RIGHT) {
            canvas.drawPath(right_p, mDeafultPaint);
        } else if (currentFlag == DOWN) {
            canvas.drawPath(down_p, mDeafultPaint);
        } else if (currentFlag == LEFT) {
            canvas.drawPath(left_p, mDeafultPaint);
        }
        mDeafultPaint.setColor(mDefauColor);
    }

    public void setListener(MenuListener listener) {
        mListener = listener;
    }

   	// 点击事件监听器
    public interface MenuListener {
        void onCenterCliched();

        void onUpCliched();

        void onRightCliched();

        void onDownCliched();

        void onLeftCliched();
    }
}