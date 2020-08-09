package android.widget;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mylib_test.app.Apps;

public class CursorDrawable extends ShapeDrawable {
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
    }
}
