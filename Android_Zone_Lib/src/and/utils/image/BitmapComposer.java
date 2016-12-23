package and.utils.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import and.utils.view.DrawUtils;
import and.utils.view.graphics.MatteUtils;

/**
 * Created by fuzhipeng on 2016/12/23.
 */

public class BitmapComposer {

    private Paint paint;
    private Canvas canvas;
    private Bitmap mComposition;

    private BitmapComposer(Bitmap mComposition) {
        this.mComposition = mComposition;
        canvas = new Canvas(mComposition);
        paint = DrawUtils.getBtPaint();
    }

    public static BitmapComposer newComposition(int width, int height, Bitmap.Config config) {
        return new BitmapComposer(Bitmap.createBitmap(width, height, config));
    }

    public BitmapComposer newLayer(Layer layer) {
        Bitmap maskComposeBitmap = layer.bitmap;
        if (layer.xfermode != null && layer.maskBitmap != null)
            maskComposeBitmap = MatteUtils.setMatte(layer.bitmap, layer.maskBitmap, layer.xfermode);
        paint.setColorFilter(layer.colorFilter);
        if (layer.matrix != null)
            canvas.drawBitmap(maskComposeBitmap, layer.matrix, paint);
        else
            canvas.drawBitmap(maskComposeBitmap, 0, 0, paint);
        paint.setColorFilter(null);
        return this;
    }

    public Bitmap render() {
        return mComposition;
    }

    public BitmapComposer clear() {
        canvas.drawColor(Color.YELLOW, PorterDuff.Mode.CLEAR);
        return this;
    }


    public static class Layer {
        private Bitmap bitmap;
        private Matrix matrix;
        private ColorFilter colorFilter;
        public PorterDuff.Mode xfermode;
        public Bitmap maskBitmap;

        private Layer() {
        }

        public static Layer bitmap(Bitmap bitmap) {
            Layer layer = new Layer();
            layer.bitmap = bitmap;
            return layer;
        }

        public Layer mask(Bitmap maskBitmap, PorterDuff.Mode xfermode) {
            this.maskBitmap = maskBitmap;
            this.xfermode = xfermode;
            return this;
        }

        public Layer matrix(Matrix matrix) {
            this.matrix = matrix;
            return this;
        }

        public Layer colorFilter(ColorFilter colorFilter) {
            this.colorFilter = colorFilter;
            return this;
        }
    }

}
