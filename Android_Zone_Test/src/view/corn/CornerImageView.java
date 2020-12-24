package view.corn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.mylib_test.R;
import com.zone.lib.utils.view.DrawUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 因为canvas clipPath边缘有锯齿的缘故，改成此方式
 *
 * border用的path只能图片的path ,如果border的path是 图片的path+border的宽的话 由于圆角的问题会导致漏底的问题
 * 不过图片的rect是根据view的rect-padding-border/2
 *
 */
public class CornerImageView extends AppCompatImageView {
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_CIRLCE = 1;
    public static final int TYPE_ROUNDER = 2;

    public static final int BORDER_TYPE_HALF_CONTENT_EDGE = 0;
    public static final int  BORDER_TYPE_EXCLUDE_CONTENT  = 1;
    public static final int  BORDER_TYPE_INCLUDE_CONTENT  = 2;

    @IntDef({TYPE_DEFAULT, TYPE_CIRLCE, TYPE_ROUNDER})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type { }

    @Type
    private int type;

    @IntDef({BORDER_TYPE_HALF_CONTENT_EDGE, BORDER_TYPE_EXCLUDE_CONTENT, BORDER_TYPE_INCLUDE_CONTENT})
    @Retention(RetentionPolicy.SOURCE)
    @interface BorderType { }
    @BorderType
    private int borderType=BORDER_TYPE_HALF_CONTENT_EDGE;

    @ColorInt
    private int borderColor;
    private float borderWith;

    Paint paintBorder = DrawUtils.getStrokePaint(Paint.Style.STROKE);

    private CornerImageViewI cornerImageViewI;

    public CornerImageView(Context context) {
        this(context, null);
    }

    public CornerImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CornerImageView, 0, 0);
            if (typedArray != null) {
                try {
                    type = typedArray.getInteger(R.styleable.CornerImageView_civ_type, TYPE_DEFAULT);
                    borderType = typedArray.getInteger(R.styleable.CornerImageView_border_type, BORDER_TYPE_HALF_CONTENT_EDGE);
                    initType();
                    if (cornerImageViewI != null) {
                        cornerImageViewI.readTypedArray(typedArray);
                    }

                    borderWith = typedArray.getDimension(R.styleable.CornerImageView_border_width, 0F);
                    borderColor = typedArray.getColor(R.styleable.CornerImageView_border_color, Color.BLUE);

                    updateBorderWithInValidate();
                } finally {
                    typedArray.recycle();
                }
            }
        }
    }


    private void initType() {
        switch (type) {
            case TYPE_CIRLCE:
                cornerImageViewI = new CirclerCornerImageViewImpl(this);
                break;
            case TYPE_ROUNDER:
                cornerImageViewI = new RounderCornerImageViewImpl(this);
                break;
            case TYPE_DEFAULT:
            default:
                cornerImageViewI = new DefaultCornerImageViewImpl(this);
                break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initPath();
    }

    private void initPath() {
        if (cornerImageViewI != null) {
            cornerImageViewI.initPath();
        }
    }

    private void updateBorderWithInValidate() {
        paintBorder.setStrokeWidth(borderWith);
        paintBorder.setColor(borderColor);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (cornerImageViewI != null) {
            cornerImageViewI.onDraw(canvas);
            cornerImageViewI.onBorderDraw(canvas);
        }
    }

    @SuppressLint("WrongCall")
    public void superOnDraw(Canvas canvas) {
        if (canvas != null) {
            super.onDraw(canvas);
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        initType();

        initPath();
        invalidate();
    }

    public float getBorderWith() {
        return borderWith;
    }

    public void setBorderWith(float borderWith) {
        this.borderWith = borderWith;
        updateBorderWithInValidate();
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        updateBorderWithInValidate();
    }

    public int getBorderType() {
        return borderType;
    }

    public void setBorderType(int borderType) {
        this.borderType = borderType;
        initPath();
        invalidate();
    }
}
