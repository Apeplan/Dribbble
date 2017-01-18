package com.simon.agiledevelop.recycler;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * describe: Base ViewHolder for RecyclerView
 *
 * @author Simon Han
 * @date 2015.09.18
 * @email hanzx1024@gmail.com
 */

public class RecycledViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mView;
    private View convertView;
    private final LinkedHashSet<Integer> childClickViewIds;
    private final LinkedHashSet<Integer> itemChildLongClickViewIds;
    Object associatedObject;

    public RecycledViewHolder(View itemView) {
        super(itemView);
        convertView = itemView;
        mView = new SparseArray<>();
        this.childClickViewIds = new LinkedHashSet<>();
        this.itemChildLongClickViewIds = new LinkedHashSet<>();
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param sequence
     * @return
     */
    public RecycledViewHolder setText(int viewId, CharSequence sequence) {
        TextView textView = getView(viewId);
        textView.setText(sequence);
        return this;
    }

    public RecycledViewHolder setText(int viewId, @StringRes int strId) {
        TextView textView = getView(viewId);
        textView.setText(strId);
        return this;
    }

    public RecycledViewHolder setTextColor(int viewId, int textColor) {
        TextView textView = getView(viewId);
        textView.setTextColor(textColor);
        return this;
    }

    public RecycledViewHolder setTypeface(int viewId, Typeface typeface) {
        TextView textView = getView(viewId);
        textView.setTypeface(typeface);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    public RecycledViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView textView = getView(viewId);
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    /**
     * TextView 中添加 链接
     *
     * @param viewId
     * @return
     */
    public RecycledViewHolder linksIntoTextView(int viewId) {
        TextView textView = getView(viewId);
        Linkify.addLinks(textView, Linkify.ALL);
        return this;
    }

    /**
     * 设置背景
     *
     * @param viewId
     * @param color
     * @return
     */
    public RecycledViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public RecycledViewHolder setBackgroundRes(int viewId, @DrawableRes int imageResId) {
        View view = getView(viewId);
        view.setBackgroundResource(imageResId);
        return this;
    }

    public RecycledViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public RecycledViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置图片
     *
     * @param viewId
     * @param resId
     * @return
     */
    public RecycledViewHolder setImageResource(int viewId, @DrawableRes int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    public RecycledViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public RecycledViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置进度条进度
     *
     * @param viewId
     * @param progress
     * @return
     */
    public RecycledViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public RecycledViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public RecycledViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public RecycledViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public RecycledViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    @Deprecated
    public RecycledViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * add childView id
     *
     * @param viewId add the child view id   can support childview click
     * @return
     */
    public RecycledViewHolder addOnClickListener(int viewId) {
        childClickViewIds.add(viewId);
        return this;
    }

    /**
     * add long click view id
     *
     * @param viewId
     * @return
     */
    public RecycledViewHolder addOnLongClickListener(int viewId) {
        itemChildLongClickViewIds.add(viewId);
        return this;
    }

    public RecycledViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public RecycledViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public RecycledViewHolder setOnItemClickListener(int viewId, AdapterView.OnItemClickListener
            listener) {
        AdapterView view = getView(viewId);
        view.setOnItemClickListener(listener);
        return this;
    }

    public RecycledViewHolder setOnItemLongClickListener(int viewId, AdapterView.OnItemLongClickListener
            listener) {
        AdapterView view = getView(viewId);
        view.setOnItemLongClickListener(listener);
        return this;
    }

    public RecycledViewHolder setOnItemSelectedClickListener(int viewId, AdapterView
            .OnItemSelectedListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemSelectedListener(listener);
        return this;
    }

    public RecycledViewHolder setOnCheckedChangeListener(int viewId, CompoundButton
            .OnCheckedChangeListener listener) {
        CompoundButton view = getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    public RecycledViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public RecycledViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public RecycledViewHolder setChecked(int viewId, boolean checked) {
        View view = getView(viewId);
        // View unable cast to Checkable
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setChecked(checked);
        } else if (view instanceof CheckedTextView) {
            ((CheckedTextView) view).setChecked(checked);
        }
        return this;
    }

    public RecycledViewHolder setAdapter(int viewId, Adapter adapter) {
        AdapterView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    public HashSet<Integer> getItemChildLongClickViewIds() {
        return itemChildLongClickViewIds;
    }

    public HashSet<Integer> getChildClickViewIds() {
        return  childClickViewIds;
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 根据 view id 获取View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mView.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            mView.put(viewId, view);
        }
        return (T) view;
    }

    public Object getAssociatedObject() {
        return associatedObject;
    }

    public void setAssociatedObject(Object associatedObject) {
        this.associatedObject = associatedObject;
    }
}
