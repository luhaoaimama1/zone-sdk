package view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zone.lib.utils.view.DrawUtils;

import view.utils.MeshHelper;

/**
 * 就是把高度卷成一个半圆。所以高度就是圆2*pi*r的一半 从而求出半径；
 * 把正向图变成侧向图。从上到下 就是过程后的点。 通过弧度求出角度。角度与半径求出Y。
 */
public class WheelViewGroup extends FrameLayout {
    private Paint filPaint;
    private Paint verPaint;
    private Canvas canvasLocal;
    private Bitmap bitmap;
    private int circleRadius;
    private final int meshWidthNumber = 1;
    private int meshHeightNumber;
    private boolean isDebug;
    private MeshHelper mMeshHelper;

    public WheelViewGroup(@NonNull Context context) {
        this(context, null);
    }

    public WheelViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            int miniLength = getHeight();
            meshHeightNumber = miniLength;
            circleRadius = (int) (miniLength / Math.PI);//反向求半径
            bitmap = Bitmap.createBitmap(miniLength, miniLength, Bitmap.Config.RGB_565);
            canvasLocal = new Canvas(bitmap);

            mMeshHelper = new MeshHelper(bitmap, meshWidthNumber, meshHeightNumber, new MeshHelper.Callback() {
                float newY;

                @Override
                public void mapPoint(int xIndex, int yIndex, float x, float y, PointF point) {
                    point.x = x;
                    point.y = newY;
                }

                @Override
                public void iterateY(int yIndex, float y) {
                    MeshHelper.Callback.super.iterateY(yIndex, y);
                    float degrees = degrees((int) y, circleRadius);
                    float yOther = (float) (Math.sin(Math.toRadians(90 - degrees)) * circleRadius);
                    newY = (miniLength * 1f / 2) - yOther;
                }

                //长度转弧度 弧度转角度
                public float degrees(int length, int r) {
                    float radians = length * 1f / r; //
                    return (float) (Math.toDegrees(radians));
                }
            });
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvasLocal);
        float[] verts = mMeshHelper.getVerts();
        canvas.drawBitmapMesh(bitmap, meshWidthNumber, meshHeightNumber, verts, 0, null, 0, null);

        if (isDebug) {
            float[] orgs = mMeshHelper.getOrgs();
            for (int i = 0; i < orgs.length; i = i + 2) {
                canvas.drawPoint(orgs[i], orgs[i + 1], filPaint);
            }
            for (int i = 0; i < verts.length; i = i + 2) {
                canvas.drawPoint(verts[i], verts[i + 1], verPaint);
            }
        }
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
        if (isDebug) {
            filPaint = DrawUtils.getStrokePaint(Paint.Style.FILL);
            verPaint = DrawUtils.getStrokePaint(Paint.Style.FILL);
            filPaint.setColor(Color.RED);
            filPaint.setStrokeWidth(20);
            verPaint.setColor(Color.BLUE);
            verPaint.setStrokeWidth(20);
        }
    }
}
