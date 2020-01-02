package view;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.example.mylib_test.R;
import com.zone.lib.utils.image.compress2sample.SampleUtils;
import com.zone.lib.utils.view.DrawUtils;
import com.zone.lib.utils.view.graphics.basic.MatrixUtils;

/**
 * Created by fuzhipeng on 16/9/30.
 */

public class FlodLayout extends LinearLayout {

    private int clipNums = 8;
    private float foldProgress = 0.8F;
    private float depthFactor = 0.5F;
    private final Bitmap bt;
    private final Paint mShadowPaint;
    private final Paint mSolidPaint;
    private final LinearGradient linearShader;

    public FlodLayout(Context context) {
        this(context, null);
    }

    public FlodLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlodLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        bt = SampleUtils.load(context, R.drawable.aaaaaaaaaaaab).bitmap();

        mSolidPaint = DrawUtils.getBtPaint();
        mShadowPaint = DrawUtils.getStrokePaint(Paint.Style.FILL);
        mShadowPaint.setShader(linearShader = new LinearGradient(0, 0, 0.5F, 0,
                Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(100, 100);
        if (foldProgress != 0) {
            drawFold(canvas);
        }else{
            canvas.drawColor(Color.TRANSPARENT);
        }
        canvas.restore();
    }

    private void drawFold(Canvas canvas) {
        float mFlodWidth = Math.round(bt.getWidth() / clipNums);
        float mFlodTransalteWidth = Math.round(bt.getWidth() * foldProgress / clipNums);

        //进度更新 shader 与透明度;
        Matrix matrix = new Matrix();
        matrix.setScale(mFlodWidth, 1);
        linearShader.setLocalMatrix(matrix);

        int alpha = (int) (255 * (1-foldProgress));
        mSolidPaint.setColor(Color.argb((int) (alpha * 0.8F), 0, 0, 0));
        mShadowPaint.setAlpha(alpha);

        //纵轴减小的那个高度，用勾股定理计算下
        int depth = (int) ((Math.sqrt(mFlodWidth * mFlodWidth
                - mFlodTransalteWidth * mFlodTransalteWidth)) * depthFactor);
        //1.所有的变形matrix
        Matrix[] matrixs = new Matrix[clipNums];
        for (int i = 0; i < matrixs.length; i++) {
            matrixs[i] = new Matrix();
            float[] src = MatrixUtils.getVertices(i * mFlodWidth, 0,
                    (i + 1) * mFlodWidth, bt.getHeight());
            float[] dst = MatrixUtils.getVertices(i * mFlodTransalteWidth, 0,
                    (i + 1) * mFlodTransalteWidth, bt.getHeight());
            if (i % 2 == 0) {//右边下坠
                dst[3] += depth;
                dst[5] -= depth;
            } else {//左边下坠
                dst[1] += depth;
                dst[7] -= depth;
            }
            matrixs[i].setPolyToPoly(src, 0, dst, 0, 4);
        }
        //2.clipCanvas
        for (int i = 0; i < matrixs.length; i++) {
            canvas.save();
            //polytopoly矩阵变换只对后面的图形剪切才有效。前面的剪切不会影响后面原图的变换。只是固定了显示区域。
            canvas.concat(matrixs[i]);
            //解释:  matrixs[i]只是保证某一块 的变化反求的marix 然后 cavas应用这个matrix
            // 导致那一块一定是 我们setPolyToPoly的那一块
            // 我们clip 和draw等的操作都是基于这个matrix所以可以无视 当成最开始的matrix
            canvas.clipRect(i * mFlodWidth, 0,
                    (i + 1) * mFlodWidth, bt.getHeight());
            canvas.drawBitmap(bt, 0, 0, DrawUtils.getBtPaint());
            //因为clip后  原点 还是左上点  需要位移到 clip的位置才能显示;
            canvas.translate(i * mFlodWidth, 0);
//            所有的阴影?
            if (i % 2 == 0)
                //绘制阴影
                canvas.drawRect(0, 0,
                        mFlodWidth, bt.getHeight(), mSolidPaint);
            else
                //绘制阴影
                canvas.drawRect(0, 0,
                        mFlodWidth, bt.getHeight(), mShadowPaint);
            canvas.restore();
        }
    }

    public void setDepth(int progress) {
        this.depthFactor = progress * 1F / 100;
        invalidate();
    }

    public void setProgress(int progress) {
        this.foldProgress = progress * 1F / 100;
        invalidate();
    }
}
