package com.zone.adapter.Helper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.text.Html;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zone.adapter.R;
import com.zone.adapter.callback.Helper;

/**
 *
 * Allows an abstraction of the ViewHolder pattern.<br>
 * <br>
 * <p/>
 * <b>Usage</b>
 * <p/>
 * <pre>
 * return Helper.get(context, convertView, parent, R.layout.item)
 *         .setText(R.id.tvName, contact.getName())
 *         .setText(R.id.tvEmails, contact.getEmails().toString())
 *         .setText(R.id.tvNumbers, contact.getNumbers().toString())
 *         .getView();
 * </pre>
 * Created by Administrator on 2016/3/27.
 */
public class AbHelper implements Helper {
    /** Views indexed with their IDs */
    protected final SparseArray<View> views;

    protected final Context context;

    private final int layoutId;

    private final ViewGroup parent;

    protected final View convertView;

    protected int position;

    /** Package private field to retain the associated user object and detect a change */
    protected Object associatedObject;


    public AbHelper( Context context,View convertView, ViewGroup parent, int layoutId) {
        this.views = new SparseArray<>();
        this.context = context;
        this.layoutId=layoutId;
        this.parent=parent;
        this.convertView=convertView;
    }

    /**
     * This method allows you to retrieve a view and perform custom
     * operations on it, not covered by the Helper.<br/>
     * If you think it's a common use case, please consider creating
     * a new issue at https://github.com/JoanZapata/base-adapter-helper/issues.
     * @param viewId The id of the view you want to retrieve.
     */
    public <T extends View> T getView(int viewId) {
        return retrieveView(viewId);
    }

    /**
     * Will set the text of a TextView.
     * @param viewId The view id.
     * @param value  The text to put in the text view.
     * @return The Helper for chaining.
     */
    public Helper setText(int viewId, String value) {
        TextView view = retrieveView(viewId);
        view.setText(value);
        return this;
    }

    /**
     * Will set the image of an ImageView from a resource id.
     * @param viewId     The view id.
     * @param imageResId The image resource id.
     * @return The Helper for chaining.
     */
    public Helper setImageResource(int viewId, int imageResId) {
        ImageView view = retrieveView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * Will set background color of a view.
     * @param viewId The view id.
     * @param color  A color, not a resource id.
     * @return The Helper for chaining.
     */
    public Helper setBackgroundColor(int viewId, int color) {
        View view = retrieveView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * Will set background of a view.
     * @param viewId        The view id.
     * @param backgroundRes A resource to use as a background.
     * @return The Helper for chaining.
     */
    public Helper setBackgroundRes(int viewId, int backgroundRes) {
        View view = retrieveView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * Will set text color of a TextView.
     * @param viewId    The view id.
     * @param textColor The text color (not a resource id).
     * @return The Helper for chaining.
     */
    public Helper setTextColor(int viewId, int textColor) {
        TextView view = retrieveView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    /**
     * Will set text color of a TextView.
     * @param viewId       The view id.
     * @param textColorRes The text color resource id.
     * @return The Helper for chaining.
     */
    public Helper setTextColorRes(int viewId, int textColorRes) {
        TextView view = retrieveView(viewId);
        view.setTextColor(context.getResources().getColor(textColorRes));
        return this;
    }

    /**
     * Will set the image of an ImageView from a drawable.
     * @param viewId   The view id.
     * @param drawable The image drawable.
     * @return The Helper for chaining.
     */
    public Helper setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = retrieveView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    //todo　我不习惯用pissco 用gilde到时候
//    /**
//     * Will download an image from a URL and put it in an ImageView.<br/>
//     * It uses Square's Picasso library to download the image asynchronously and put the result into the ImageView.<br/>
//     * Picasso manages recycling of views in a ListView.<br/>
//     * If you need more control over the Picasso settings, use {Helper#setImageBuilder}.
//     * @param viewId   The view id.
//     * @param imageUrl The image URL.
//     * @return The Helper for chaining.
//     */
//    public Helper setImageUrl(int viewId, String imageUrl) {
//        ImageView view = retrieveView(viewId);
//        Picasso.with(context).load(imageUrl).into(view);
//        return this;
//    }
//
//    /**
//     * Will download an image from a URL and put it in an ImageView.<br/>
//     * @param viewId         The view id.
//     * @param requestBuilder The Picasso request builder. (e.g. Picasso.with(context).load(imageUrl))
//     * @return The Helper for chaining.
//     */
//    public Helper setImageBuilder(int viewId, RequestCreator requestBuilder) {
//        ImageView view = retrieveView(viewId);
//        requestBuilder.into(view);
//        return this;
//    }

    /** Add an action to set the image of an image view. Can be called multiple times. */
    public Helper setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = retrieveView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * Add an action to set the alpha of a view. Can be called multiple times.
     * Alpha between 0-1.
     */
    public Helper setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            retrieveView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            retrieveView(viewId).startAnimation(alpha);
        }
        return this;
    }

    /**
     * Set a view visibility to VISIBLE (true) or GONE (false).
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for GONE.
     * @return The Helper for chaining.
     */
    public Helper setVisible(int viewId, boolean visible) {
        View view = retrieveView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * Add links into a TextView.
     * @param viewId The id of the TextView to linkify.
     * @return The Helper for chaining.
     */
    public Helper linkify(int viewId) {
        TextView view = retrieveView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /** Apply the typeface to the given viewId, and enable subpixel rendering. */
    public Helper setTypeface(int viewId, Typeface typeface) {
        TextView view = retrieveView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    /** Apply the typeface to all the given viewIds, and enable subpixel rendering. */
    public Helper setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = retrieveView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    /**
     * Sets the progress of a ProgressBar.
     * @param viewId   The view id.
     * @param progress The progress.
     * @return The Helper for chaining.
     */
    public Helper setProgress(int viewId, int progress) {
        ProgressBar view = retrieveView(viewId);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the progress and max of a ProgressBar.
     * @param viewId   The view id.
     * @param progress The progress.
     * @param max      The max value of a ProgressBar.
     * @return The Helper for chaining.
     */
    public Helper setProgress(int viewId, int progress, int max) {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the range of a ProgressBar to 0...max.
     * @param viewId The view id.
     * @param max    The max value of a ProgressBar.
     * @return The Helper for chaining.
     */
    public Helper setMax(int viewId, int max) {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) of a RatingBar.
     * @param viewId The view id.
     * @param rating The rating.
     * @return The Helper for chaining.
     */
    public Helper setRating(int viewId, float rating) {
        RatingBar view = retrieveView(viewId);
        view.setRating(rating);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) and max of a RatingBar.
     * @param viewId The view id.
     * @param rating The rating.
     * @param max    The range of the RatingBar to 0...max.
     * @return The Helper for chaining.
     */
    public Helper setRating(int viewId, float rating, int max) {
        RatingBar view = retrieveView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    /**
     * Sets the on click listener of the view.
     * @param viewId   The view id.
     * @param listener The on click listener;
     * @return The Helper for chaining.
     */
    public Helper setOnClickListener(int viewId, View.OnClickListener listener) {
        return setOnClickListener(viewId,listener,false);
    }

    @Override
    public Helper setOnClickListenerForce(int viewId, View.OnClickListener listener) {
        return setOnClickListener(viewId,listener,true);
    }
    private Helper setOnClickListener(int viewId, View.OnClickListener listener,boolean force) {
        View view = retrieveView(viewId);
        if (force) {
            view.setOnClickListener(listener);
            view.setTag(R.id.OnClickListener_KEY, listener);
        }else{
            if (view.getTag(R.id.OnClickListener_KEY)==null) {
                view.setOnClickListener(listener);
                view.setTag(R.id.OnClickListener_KEY, listener);
            }
        }
        return this;
    }

    /**
     * Sets the on touch listener of the view.
     * @param viewId   The view id.
     * @param listener The on touch listener;
     * @return The Helper for chaining.
     */
    public Helper setOnTouchListener(int viewId, View.OnTouchListener listener) {
        return setOnTouchListener(viewId,listener,false);
    }

    @Override
    public Helper setOnTouchListenerForce(int viewId, View.OnTouchListener listener) {
        return  setOnTouchListener(viewId,listener,true);
    }

    private Helper setOnTouchListener(int viewId, View.OnTouchListener listener,boolean force) {
        View view = retrieveView(viewId);
        if (force) {
            view.setOnTouchListener(listener);
            view.setTag(R.id.OnTouchListener_KEY, listener);
        }else{
            if (view.getTag(R.id.OnTouchListener_KEY)==null) {
                view.setOnTouchListener(listener);
                view.setTag(R.id.OnTouchListener_KEY, listener);
            }
        }
        return this;
    }

    /**
     * Sets the on long click listener of the view.
     * @param viewId   The view id.
     * @param listener The on long click listener;
     * @return The Helper for chaining.
     */
    public Helper setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        return setOnLongClickListener(viewId,listener,false);
    }

    @Override
    public Helper setOnLongClickListenerForce(int viewId, View.OnLongClickListener listener) {
        return setOnLongClickListener(viewId,listener,true);
    }

    private Helper setOnLongClickListener(int viewId, View.OnLongClickListener listener,boolean force) {
        View view = retrieveView(viewId);
        if (force) {
            view.setOnLongClickListener(listener);
            view.setTag(R.id.OnLongClickListener_KEY, listener);
        }else{
            if (view.getTag(R.id.OnLongClickListener_KEY)==null) {
                view.setOnLongClickListener(listener);
                view.setTag(R.id.OnLongClickListener_KEY, listener);
            }
        }
        return this;
    }


    /**
     * Sets the listview or gridview's item click listener of the view
     * @param viewId  The view id.
     * @param listener The item on click listener;
     * @return The Helper for chaining.
     */
    public Helper setOnItemClickListener(int viewId,AdapterView.OnItemClickListener listener) {
        return setOnItemClickListener(viewId,listener,false);
    }

    @Override
    public Helper setOnItemClickListenerForce(int viewId, AdapterView.OnItemClickListener listener) {
        return setOnItemClickListener(viewId,listener,true);
    }
    private Helper setOnItemClickListener(int viewId,AdapterView.OnItemClickListener listener,boolean force) {
        AdapterView view = retrieveView(viewId);
        if (force) {
            view.setOnItemClickListener(listener);
            view.setTag(R.id.OnItemClickListener_KEY, listener);
        }else{
            if (view.getTag(R.id.OnItemClickListener_KEY)==null) {
                view.setOnItemClickListener(listener);
                view.setTag(R.id.OnItemClickListener_KEY, listener);
            }
        }
        return this;
    }


    /**
     * Sets the listview or gridview's item long click listener of the view
     * @param viewId The view id.
     * @param listener   The item long click listener;
     * @return The Helper for chaining.
     */
    public Helper setOnItemLongClickListener(int viewId,AdapterView.OnItemLongClickListener listener) {
        return setOnItemLongClickListener(viewId,listener,false);
    }

    @Override
    public Helper setOnItemLongClickListenerForce(int viewId, AdapterView.OnItemLongClickListener listener) {
        return setOnItemLongClickListener(viewId,listener,true);
    }
    private Helper setOnItemLongClickListener(int viewId,AdapterView.OnItemLongClickListener listener,boolean force) {
        AdapterView view = retrieveView(viewId);
        if (force) {
            view.setOnItemLongClickListener(listener);
            view.setTag(R.id.OnItemLongClickListener_KEY, listener);
        }else{
            if (view.getTag(R.id.OnItemLongClickListener_KEY)==null) {
                view.setOnItemLongClickListener(listener);
                view.setTag(R.id.OnItemLongClickListener_KEY, listener);
            }
        }
        return this;
    }

    /**
     * Sets the listview or gridview's item selected click listener of the view
     * @param viewId The view id.
     * @param listener The item selected click listener;
     * @return The Helper for chaining.
     */
    public Helper setOnItemSelectedClickListener(int viewId,AdapterView.OnItemSelectedListener listener) {
        return setOnItemSelectedClickListener(viewId,listener,false);
    }

    @Override
    public Helper setOnItemSelectedClickListenerForce(int viewId, AdapterView.OnItemSelectedListener listener) {
        return  setOnItemSelectedClickListener(viewId,listener,true);
    }
    private Helper setOnItemSelectedClickListener(int viewId,AdapterView.OnItemSelectedListener listener,boolean force) {
        AdapterView view = retrieveView(viewId);
        if (force) {
            view.setOnItemSelectedListener(listener);
            view.setTag(R.id.OnItemSelectedClickListener_KEY, listener);
        }else{
            if (view.getTag(R.id.OnItemSelectedClickListener_KEY)==null) {
                view.setOnItemSelectedListener(listener);
                view.setTag(R.id.OnItemSelectedClickListener_KEY, listener);
            }
        }
        return this;
    }

    /**
     * Sets the tag of the view.
     * @param viewId The view id.
     * @param tag    The tag;
     * @return The Helper for chaining.
     */
    public Helper setTag(int viewId, Object tag) {
        View view = retrieveView(viewId);
        view.setTag(tag);
        return this;
    }

    /**
     * Sets the tag of the view.
     * @param viewId The view id.
     * @param key    The key of tag;
     * @param tag    The tag;
     * @return The Helper for chaining.
     */
    public Helper setTag(int viewId, int key, Object tag) {
        View view = retrieveView(viewId);
        view.setTag(key, tag);
        return this;
    }

    /**
     * Sets the checked status of a checkable.
     * @param viewId  The view id.
     * @param checked The checked status;
     * @return The Helper for chaining.
     */
    public Helper setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) retrieveView(viewId);
        view.setChecked(checked);
        return this;
    }

    public Helper setHtml(@IdRes int viewId, String source) {
        TextView view = getView(viewId);
        view.setText(Html.fromHtml(source));
        return this;
    }
    /**
     * Sets the adapter of a adapter view.
     * @param viewId  The view id.
     * @param adapter The adapter;
     * @return The Helper for chaining.
     */
    public Helper setAdapter(int viewId, Adapter adapter) {
        AdapterView view = retrieveView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    /** Retrieve the convertView */
    public View getView() {
        return convertView;
    }

    /**
     * Retrieve the overall position of the data in the list.
     * @throws IllegalArgumentException If the position hasn't been set at the construction of the this helper.
     */
    public int getPosition() {
        if (position == -1)
            throw new IllegalStateException("Use Helper constructor " +
                    "with position if you need to retrieve the position.");
        return position;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /** Retrieves the last converted object on this view. */
    public Object getAssociatedObject() {
        return associatedObject;
    }
    /**
     * Retrieve the overall position of the data in the list.
     * @throws IllegalArgumentException If the position hasn't been set at the construction of the this helper.
     */
    /** Should be called during fillData */
    public void setAssociatedObject(Object associatedObject,int position) {
        this.associatedObject = associatedObject;
        this.position=position;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    public ViewGroup getParent() {
        return parent;
    }
}
