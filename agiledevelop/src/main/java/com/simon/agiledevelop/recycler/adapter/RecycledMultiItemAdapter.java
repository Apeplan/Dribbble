package com.simon.agiledevelop.recycler.adapter;

import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.simon.agiledevelop.recycler.RecycledViewHolder;
import com.simon.agiledevelop.recycler.entity.MultiItemEntity;

import java.util.List;

/**
 * describe:
 *
 * @param <T> Implements MultiItemEntity
 * @param <H>
 * @author Simon Han
 * @date 2015.09.18
 * @email hanzx1024@gmail.com
 */

public abstract class RecycledMultiItemAdapter<T extends MultiItemEntity, H extends
        RecycledViewHolder> extends RecycledAdapter<T, H> {

    /**
     * layouts indexed with their types
     */
    private SparseArray<Integer> layouts;

    private static final int DEFAULT_VIEW_TYPE = -0xff;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public RecycledMultiItemAdapter(List<T> data) {
        super(data);
    }

    @Override
    protected int getDefItemViewType(int position) {
        Object item = mData.get(position);
        if (item instanceof MultiItemEntity) {
            return ((MultiItemEntity) item).getItemType();
        }
        return DEFAULT_VIEW_TYPE;
    }

    protected void setDefaultViewTypeLayout(@LayoutRes int layoutResId) {
        addItemType(DEFAULT_VIEW_TYPE, layoutResId);
    }

    @Override
    protected H onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return layouts.get(viewType);
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseArray<>();
        }
        layouts.put(type, layoutResId);
    }


}


