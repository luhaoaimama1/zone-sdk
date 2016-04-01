package com.zone.zrflist.headercustom;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.zone.zrflist.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by Administrator on 2016/3/31.
 */
public class HeaderCustom  extends FrameLayout implements PtrUIHandler {

    private final TextView tv_head_title;
    private final ImageView iv_windmill;
    HeaderCustomDrawable drawableCustom;

    public HeaderCustom(Context context) {
        this(context, null);
    }

    public HeaderCustom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.windmill_header, this);
        tv_head_title= (TextView) findViewById(R.id.tv_head_title);
        iv_windmill= (ImageView) findViewById(R.id.iv_windmill);
        iv_windmill.setImageDrawable(drawableCustom=new HeaderCustomDrawable(context,iv_windmill));
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        drawableCustom.stop();
        tv_head_title.setText("下拉刷新");
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        drawableCustom.stop();
        tv_head_title.setText("下拉刷新");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        tv_head_title.setText("正在刷新");
        drawableCustom.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        iv_windmill.clearAnimation();
        tv_head_title.setText("刷新完成");
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        if (isUnderTouch&&status == PtrFrameLayout.PTR_STATUS_PREPARE) {
            final int mOffsetToRefresh = frame.getOffsetToRefresh();
            final int currentPos = ptrIndicator.getCurrentPosY();
            final int lastPos = ptrIndicator.getLastPosY();
            if(drawableCustom!=null)
                drawableCustom.postRotation(currentPos-lastPos);
            if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh)
                    tv_head_title.setText("下拉刷新");
            else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh)
                    tv_head_title.setText("松开刷新");
        }
    }
}
