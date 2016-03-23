package com.zone.zbanner.indicator.type;
import android.graphics.Bitmap;
import com.zone.zbanner.indicator.type.abstarct.BaseIndicator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zone on 2016/2/3.
 */
public class ImageIndicator extends BaseIndicator {
    private List<Bitmap> defaultBitmaps= new  ArrayList<Bitmap>();
    private List<Bitmap> selectBitmaps= new  ArrayList<Bitmap>();

    public ImageIndicator(int width, int height) {
        super(width, height);
    }

    @Override
    public Bitmap getDefaultBitmap(int position) {
        return defaultBitmaps.get(position);
    }

    @Override
    public Bitmap getSelectedBitmap(int position) {
        return selectBitmaps.get(position);
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        indicatorView.getIv_Top().setImageBitmap(getSelectedBitmap(position));
    }
    public List<Bitmap> getDefaultBitmaps() {
        return defaultBitmaps;
    }

    public void setDefaultBitmaps(List<Bitmap> defaultBitmaps) {
        this.defaultBitmaps = defaultBitmaps;
    }

    public List<Bitmap> getSelectBitmaps() {
        return selectBitmaps;
    }

    public void setSelectBitmaps(List<Bitmap> selectBitmaps) {
        this.selectBitmaps = selectBitmaps;
    }
}
