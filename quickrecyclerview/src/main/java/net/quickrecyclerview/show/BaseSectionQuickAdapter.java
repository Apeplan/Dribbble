package net.quickrecyclerview.show;

import android.view.ViewGroup;

import net.quickrecyclerview.show.entity.SectionEntity;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class BaseSectionQuickAdapter<T extends SectionEntity> extends BaseQuickAdapter {


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
    public BaseSectionQuickAdapter(int layoutResId, int sectionHeadResId, List<T> data) {
        super(layoutResId, data);
        this.mSectionHeadResId = sectionHeadResId;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SECTION_HEADER_VIEW)
            return new BaseViewHolder(getItemView(mSectionHeadResId, parent));

        return super.onCreateDefViewHolder(parent, viewType);
    }

    /**
     * @param holder A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder holder, Object item) {
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

    protected abstract void convertHead(BaseViewHolder helper, T item);

    protected abstract void convert(BaseViewHolder helper, T item);


}
