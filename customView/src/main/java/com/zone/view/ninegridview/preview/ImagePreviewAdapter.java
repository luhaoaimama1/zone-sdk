package com.zone.view.ninegridview.preview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zone.customview.ninegridview.R;
import com.zone.view.ninegridview.TouchGreyImageView;
import com.zone.view.ninegridview.ZGridViewAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/4/12.
 */
public class ImagePreviewAdapter extends ZGridViewAdapter<ImageInfo> {
    private int statusHeight;
    public ImagePreviewAdapter(List<ImageInfo> list) {
        super(list);

    }

    @Override
    public View getView(Context context, int index) {
        View item = LayoutInflater.from(context).inflate(R.layout.grid_item_pic, null);
        TouchGreyImageView tgiv = (TouchGreyImageView) item.findViewById(R.id.tgiv);

        Glide.with(context).load(list.get(index).getThumbnailUrl())//
                .placeholder(R.drawable.ic_default_image)//
                .error(R.drawable.ic_default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(tgiv);

        return item;
    }

    @Override
    public void onItemImageClick(Context context, int index, ImageInfo data) {
        super.onItemImageClick(context, index, data);
        if (statusHeight==0)
            statusHeight = getStatusHeight(context);
        List<ImageInfoInner> listImageInfo=new ArrayList<>();
        for (ImageInfo imageInfo : list) {
            ImageInfoInner imageInner=new ImageInfoInner();
            imageInner.setImageInfo(imageInfo);

            View imageView = gvz.getChildAt(list.indexOf(imageInfo));
            imageInner.setImageViewWidth(imageView.getWidth());
            imageInner.setImageViewHeight(imageView.getHeight());

            int[] points = new int[2];
            imageView.getLocationInWindow(points);

            imageInner.setImageViewX(points[0]);
            imageInner.setImageViewY(points[1] - statusHeight);

            listImageInfo.add(imageInner);
        }
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) listImageInfo);
        bundle.putInt(ImagePreviewActivity.INDEX, index);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(0, 0);
    }
    /**
     * 获得状态栏的高度
     */
    public int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

}
