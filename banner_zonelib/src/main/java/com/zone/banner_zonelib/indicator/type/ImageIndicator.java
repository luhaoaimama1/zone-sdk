package com.zone.banner_zonelib.indicator.type;
import android.graphics.Bitmap;
import com.zone.banner_zonelib.indicator.type.abstarct.AbstractIndicator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zone on 2016/2/3.
 */
public class ImageIndicator extends AbstractIndicator {
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
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        ivTop.setImageBitmap(selectBitmaps.get(position));
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
