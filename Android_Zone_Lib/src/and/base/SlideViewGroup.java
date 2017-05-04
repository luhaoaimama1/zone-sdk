package and.base;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * [2017] by Zone
 */

public class SlideViewGroup extends RelativeLayout {

    private final Callback callback;

    public SlideViewGroup(Context context, Callback callback) {
        super(context);
        this.callback = callback;
    }

    public int downX, downY;
    int offX, offY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                offX = (int) event.getRawX() - downX;
                offY = (int) event.getRawY() - downY;
                if (callback != null)
                    callback.update(offX,  offY);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (callback != null)
                    callback.upCancel(offX,offY);
                break;
        }
        //刷新
        return super.dispatchTouchEvent(event);
    }

    public interface Callback {
        void update(int x, int y);
        void upCancel(int x, int y);
    }

}
