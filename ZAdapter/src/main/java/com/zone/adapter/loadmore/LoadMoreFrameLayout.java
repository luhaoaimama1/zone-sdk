package com.zone.adapter.loadmore;
import android.content.Context;
import com.zone.adapter.R;
/**
 * Created by Administrator on 2016/4/1.
 */
public class LoadMoreFrameLayout extends BaseLoadMoreFrameLayout  {

    public LoadMoreFrameLayout(Context context, RecyclerOnLoadMoreListener listener) {
        super(context, listener);
    }

    @Override
    public void loading() {
        removeAllViews();
        inflater.inflate(R.layout.sample_common_list_footer_loading,this);
    }

    @Override
    public int getLoadingLayoutID() {
        return R.layout.sample_common_list_footer_loading;
    }

    @Override
    public int getFailLayoutID() {
        return R.layout.sample_common_list_footer_network_error;
    }

    @Override
    public boolean getFailClickable() {
        return true;
    }

}
