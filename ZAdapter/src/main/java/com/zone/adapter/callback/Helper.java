package com.zone.adapter.callback;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

/**
 * Created by Administrator on 2016/3/26.
 */
public interface Helper<T> {
    <V extends View> V getView(int viewId);

    Helper setText(int viewId, String value);

    Helper setImageResource(int viewId, int imageResId);

    Helper setBackgroundColor(int viewId, int color);

    Helper setBackgroundRes(int viewId, int backgroundRes);

    Helper setTextColor(int viewId, int textColor);

    Helper setTextColorRes(int viewId, int textColorRes);

    Helper setImageDrawable(int viewId, Drawable drawable);

    //todo　我不习惯用pissco 用gilde到时候
//    BaseAdapterHelper setImageUrl(int viewId, String imageUrl)
    Helper setImageBitmap(int viewId, Bitmap bitmap);

    Helper setAlpha(int viewId, float value);

    Helper setVisible(int viewId, boolean visible);

    Helper linkify(int viewId);

    Helper setTypeface(int viewId, Typeface typeface);

    Helper setTypeface(Typeface typeface, int... viewIds);

    Helper setProgress(int viewId, int progress);

    Helper setProgress(int viewId, int progress, int max);

    Helper setMax(int viewId, int max);

    Helper setRating(int viewId, float rating);

    Helper setRating(int viewId, float rating, int max);

    Helper setOnClickListener(int viewId, View.OnClickListener listener);

    Helper setOnTouchListener(int viewId, View.OnTouchListener listener);

    Helper setOnLongClickListener(int viewId, View.OnLongClickListener listener);

    Helper setOnItemClickListener(int viewId,AdapterView.OnItemClickListener listener);

    Helper setOnItemLongClickListener(int viewId,AdapterView.OnItemLongClickListener listener);

    Helper setOnItemSelectedClickListener(int viewId,AdapterView.OnItemSelectedListener listener);

    Helper setOnClickListenerForce(int viewId, View.OnClickListener listener);

    Helper setOnTouchListenerForce(int viewId, View.OnTouchListener listener);

    Helper setOnLongClickListenerForce(int viewId, View.OnLongClickListener listener);

    Helper setOnItemClickListenerForce(int viewId,AdapterView.OnItemClickListener listener);

    Helper setOnItemLongClickListenerForce(int viewId,AdapterView.OnItemLongClickListener listener);

    Helper setOnItemSelectedClickListenerForce(int viewId,AdapterView.OnItemSelectedListener listener);

    Helper setTag(int viewId, Object tag);


    Helper setTag(int viewId, int key, Object tag);

    Helper setChecked(int viewId, boolean checked);

    Helper setHtml(@IdRes int viewId, String source);

    //todo  这个有点问题貌似
    Helper setAdapter(int viewId, Adapter adapter);

    View getView();

    int getPosition();

    int getLayoutId();

    ViewGroup getParent();
    //todo   这个需要 T比较好
    T getData();

    void setData(T associatedObject, int position);
}
