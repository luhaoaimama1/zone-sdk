package view.utils;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
//方法理解：https://www.jianshu.com/p/51d8dd99d27d
public class MeshHelper {
    public interface Callback {
        void mapPoint(int xIndex, int yIndex, float x, float y, PointF point);

        default void iterateY(int yIndex, float y) {
        }
    }

    private final int meshColumnsNumber;
    private final int meshRowsNumber;
    private final Bitmap bitmap;
    private final float[] verts;
    private final float[] orgs;

    private final int heightInterval;
    private final int widthInterval;

    public MeshHelper(Bitmap bitmap, int meshColumnsNumber, int meshRowsNumber, Callback callback) {
        this.bitmap = bitmap;
        this.meshColumnsNumber = meshColumnsNumber;
        this.meshRowsNumber = meshRowsNumber;
        verts = new float[(meshColumnsNumber + 1) * (meshRowsNumber + 1) * 2];
        orgs = new float[verts.length];

        heightInterval = (int) (bitmap.getHeight() * 1f / meshRowsNumber);
        widthInterval = (int) (bitmap.getWidth() * 1f / meshColumnsNumber);

        PointF point = new PointF();

        int pointIndex = 0;
        for (int yIndex = 0; yIndex < meshRowsNumber + 1; yIndex++) {
            float fy = yIndex * heightInterval;
            callback.iterateY(yIndex,fy);
            for (int xIndex = 0; xIndex < meshColumnsNumber + 1; xIndex++) {
                float fx = xIndex * widthInterval;

                int x = pointIndex * 2;
                int y = pointIndex * 2 + 1;
                orgs[x] = fx;//x
                orgs[y] = fy;//y

                callback.mapPoint(xIndex, yIndex, fx, fy, point);

                verts[x] = point.x;
                verts[y] = point.y;
                pointIndex += 1;
            }
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmapMesh(bitmap, meshColumnsNumber, meshRowsNumber, verts, 0, null, 0, null);
    }

    public float[] getVerts() {
        return verts;
    }

    public float[] getOrgs() {
        return orgs;
    }

    public int getHeightInterval() {
        return heightInterval;
    }

    public int getWidthInterval() {
        return widthInterval;
    }

    public int getMeshColumnsNumber() {
        return meshColumnsNumber;
    }

    public int getMeshRowsNumber() {
        return meshRowsNumber;
    }
}
