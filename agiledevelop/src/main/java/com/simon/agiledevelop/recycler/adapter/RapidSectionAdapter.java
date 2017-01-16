package com.simon.agiledevelop.recycler.adapter;

import android.view.ViewGroup;

import com.simon.agiledevelop.recycler.RapidViewHolder;
import com.simon.agiledevelop.recycler.entity.SectionEntity;

import java.util.List;

/**
 * describe:
 *
 * @author Simon Han
 * @date 2015.09.18
 * @email hanzx1024@gmail.com
 * @param <T> extends SectionEntity
 */

public abstract class RapidSectionAdapter<T extends SectionEntity> extends RapidAdapter {


    protected int mSectionHeadResId;
    protected static final int SECTION_HEADER_VIEW = 0x00000444;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public RapidSectionAdapter(int layoutResId, int sectionHeadResId, List<T> data) {
        super(layoutResId, data);
        this.mSectionHeadResId = sectionHeadResId;
    }

    @Override
    protected RapidViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SECTION_HEADER_VIEW)
            return new RapidViewHolder(getItemView(mSectionHeadResId, parent));

        return super.onCreateDefViewHolder(parent, viewType);
    }

    /**
     * @param holder A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(RapidViewHolder holder, Object item) {
        switch (holder.getItemViewType()) {
            case SECTION_HEADER_VIEW:
                setFullSpan(holder);
                convertHead(holder, (T) item);
                break;
            default:
                convert(holder, (T) item);
                break;
        }
    }

    protected abstract void convertHead(RapidViewHolder helper, T item);

    protected abstract void convert(RapidViewHolder helper, T item);


}
