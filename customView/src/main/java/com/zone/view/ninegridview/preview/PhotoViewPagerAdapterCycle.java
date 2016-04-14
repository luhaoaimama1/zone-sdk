package com.zone.view.ninegridview.preview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zone.customview.ninegridview.R;
import com.zone.zbanner.PagerAdapterCycle;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016/4/12.
 */
public class PhotoViewPagerAdapterCycle extends PagerAdapterCycle<ImageInfoInner> {
    private final List<ImageInfoInner> data;
    public PhotoView pv;

    public PhotoViewPagerAdapterCycle(Context context, List<ImageInfoInner> data, boolean isCircle) {
        super(context, data, isCircle);
        this.data=data;
    }

    @Override
    public View getView(Context context, int position) {
        View item = LayoutInflater.from(context).inflate(R.layout.grid_item_pic_scale, null);
        pv=(PhotoView)item.findViewById(R.id.pv);
        pv.setImageResource(R.drawable.ic_default_image);
        final ProgressBar pb=(ProgressBar)item.findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        Glide.with(context).load(data.get(position).getBigImageUrl())//
                .placeholder(R.drawable.ic_default_image)//
                .error(R.drawable.ic_default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        pb.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pb.setVisibility(View.GONE);
                        return false;
                    }
                }).into(pv);
        return item;
    }
}
