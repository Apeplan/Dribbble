package com.simon.agiledevelop.recycler.adapter;

import android.animation.Animator;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.simon.agiledevelop.R;
import com.simon.agiledevelop.log.LLog;
import com.simon.agiledevelop.recycler.RecycledViewHolder;
import com.simon.agiledevelop.recycler.animation.AlphaInAnimation;
import com.simon.agiledevelop.recycler.animation.BaseAnimation;
import com.simon.agiledevelop.recycler.animation.ScaleInAnimation;
import com.simon.agiledevelop.recycler.animation.SlideInBottomAnimation;
import com.simon.agiledevelop.recycler.animation.SlideInLeftAnimation;
import com.simon.agiledevelop.recycler.animation.SlideInRightAnimation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * describe: Base Adapter for RecyclerView
 *
 * @param <T>
 * @param <H>
 * @author Simon Han
 * @date 2015.09.18
 * @email hanzx1024@gmail.com
 */

public abstract class RecycledAdapter<T, H extends RecycledViewHolder> extends RecyclerView
        .Adapter<H> {

    public static final int TYPE_HEADER = 0xBFFD0D8; // 头部标识
    public static final int TYPE_ITEM = -0xBFFD0D8; // 头部标识
    public static final int TYPE_FOOTER = 0xBFFD0D9; // 尾部标识
    public static final int TYPE_LOADING = 0xBFFD0DA; // 加载标识
    public static final int TYPE_EMPTY = 0xBFFD0DB; // 加载标识

    private static final int STATE_LOADING = 0x4F5dA2; // 正在加载
    private static final int STATE_NOMORE = 0x4F5dA3; // 没有更多数据
    private static final int STATE_FAILED = 0x4F5dA4; // 加载更多失败
    public final static int STATE_COMPLETE = 0x4F5dA5; // 加载完成


    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;
    private View loadMoreFailedView;

    private View mLoadingView;
    private int mLoading_state = STATE_COMPLETE;
    private LinearLayout mCopyHeaderLayout = null;
    private LinearLayout mCopyFooterLayout = null;

    private boolean mLoadingMoreEnable = false;
    private boolean mFirstOnlyAnim = true; // 是否是只有第一次进入有动画
    private boolean mOpenAnimation = false; // 是否有加载动画
    private BaseAnimation mCurrentAnim;
    private BaseAnimation mDefAnim = new ScaleInAnimation();
    private int mDuration = 300;// 默认动画时间
    private Interpolator mInterpolator = new LinearInterpolator();

    public static final int ALPHAIN = 0x957200;
    public static final int SCALEIN = 0x957201;
    public static final int SLIDEIN_BOTTOM = 0x957202;
    public static final int SLIDEIN_LEFT = 0x957203;
    public static final int SLIDEIN_RIGHT = 0x957204;

    @IntDef({ALPHAIN, SCALEIN, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    private int mLastPosition = -1;

    private LoadMoreListener mLoadMoreListener;

    protected List<T> mData;
    protected int mLayoutId;
//    private View mContentView;
//    private LayoutInflater mInflater;


    public RecycledAdapter(List<T> data) {
        this(0, data);
    }

    public RecycledAdapter(int layoutId) {
        this(layoutId, null);
    }

    public RecycledAdapter(int layoutId, List<T> data) {
        mData = data == null ? new ArrayList<T>() : data;
        if (layoutId != 0) {
            mLayoutId = layoutId;
        }
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        H baseViewHolder;
        switch (viewType) {
            case TYPE_HEADER:
                baseViewHolder = createBaseViewHolder(mHeaderLayout);
                break;
            case TYPE_FOOTER:
                baseViewHolder = createBaseViewHolder(mFooterLayout);
                break;
            case TYPE_LOADING:
                baseViewHolder = getLoadingView(parent);
                break;
            case TYPE_EMPTY:
                baseViewHolder = null;
                break;
            default:
                baseViewHolder = onCreateDefViewHolder(parent, viewType);

        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(RecycledViewHolder holder, int position) {
        int viewType = holder.getItemViewType();

        switch (viewType) {
//            case 0:
//                if (mData.size() != 0)
//                    convert(holder, mData.get(holder.getLayoutPosition() - getHeaderLayoutCount
// ()));
//                break;
            case TYPE_LOADING:
                addLoadMore(holder);
                LLog.d(": type= " + TYPE_LOADING);
                break;
            case TYPE_HEADER:
                LLog.d(": type= " + TYPE_HEADER);
                break;
            case TYPE_EMPTY:
                LLog.d(": type= " + TYPE_EMPTY);
                break;

            case TYPE_FOOTER:
                LLog.d(": type= " + TYPE_FOOTER);
                break;
            default:
                convert(holder, mData.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
                break;

        }
    }

    protected H onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, mLayoutId);
    }

    @Override
    public void onViewAttachedToWindow(H holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == TYPE_HEADER || type == TYPE_FOOTER || type == TYPE_LOADING) {
            setFullSpan(holder);
        } else {
            addAnimation(holder);
        }
    }

    protected void setFullSpan(RecycledViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams slp = (StaggeredGridLayoutManager
                    .LayoutParams) layoutParams;
            slp.setFullSpan(true);
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
//                    Log.d("Simon Han", "mSpanSizeLookup= " + mSpanSizeLookup);

                    if (mSpanSizeLookup == null) {
                        int i = (type == TYPE_HEADER || type == TYPE_FOOTER || type ==
                                TYPE_LOADING) ? gridManager.getSpanCount() : 1;
                        Log.d("Simon Han", "span= " + i);
                        return i;
                    } else {
                        int i = (type == TYPE_HEADER || type == TYPE_FOOTER || type ==
                                TYPE_LOADING) ? gridManager.getSpanCount() : mSpanSizeLookup
                                .getSpanSize(gridManager, position - getHeaderLayoutCount());
//                        Log.d("Simon Han", "span-span= " + i);
                        return i;
                    }
                }
            });
        }

    }

    private SpanSizeLookup mSpanSizeLookup;

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int position);
    }

    /**
     * @param spanSizeLookup instance to be used to query number of spans occupied by each item
     */
    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    private H getLoadingView(ViewGroup parent) {
        if (mLoadingView == null) {
            H baseViewHolder = createBaseViewHolder(parent, R.layout.def_loading);
            return baseViewHolder;
        }

        H baseViewHolder = createBaseViewHolder(mLoadingView);
        return baseViewHolder;
    }

    protected H createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return createBaseViewHolder(getItemView(layoutResId, parent));
    }

    protected H createBaseViewHolder(View view) {
        return (H) new RecycledViewHolder(view);
    }

    protected View getItemView(int layoutResId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
    }

    /*@Override
    public int getItemViewType(int position) {
        if (mHeaderLayout != null && position < getHeaderLayoutCount()) {
            return TYPE_HEADER;
        }

        if (mData.size() == 0) {
            if (getHeaderLayoutCount() != 0) {
                return TYPE_HEADER;
            } else if (getFooterLayoutCount() != 0) {
                return TYPE_FOOTER;
            } else {
                return TYPE_EMPTY;
            }
        } else if (mFooterLayout != null && position == mData.size() + getHeaderLayoutCount()) {
            return TYPE_FOOTER;
        } else if (mData.size() != 0 && position == mData.size() + getHeaderLayoutCount() +
                getFooterLayoutCount()) {
            return TYPE_LOADING;
        } else {
            return getDefItemViewType(position - getHeaderLayoutCount());
        }

       *//* if (mData.size() == 0 && (getHeaderLayoutCount() != 0 || getFooterLayoutCount() != 0)) {
            if (getHeaderLayoutCount() != 0) {
                return TYPE_HEADER;
            } else {
                return TYPE_FOOTER;
            }
        } else if (mFooterLayout != null && position == mData.size() + getHeaderLayoutCount()) {
            return TYPE_FOOTER;
        } else if (mData.size() != 0 && position == mData.size() + getHeaderLayoutCount() +
                getFooterLayoutCount()) {
            return TYPE_LOADING;
        } else {
            return getDefItemViewType(position - getHeaderLayoutCount());
        }*//*

    }*/
    @Override
    public int getItemViewType(int position) {

        if (isHeaderType(position)) {
            return TYPE_HEADER;
        }

        if (isLoadMoreType(position)) {
            return TYPE_LOADING;
        }

        if (isFooterType(position)) {
            return TYPE_FOOTER;
        }

        if (isEmpty()) {
            return TYPE_EMPTY;
        }

        return getDefItemViewType(position - getHeaderLayoutCount());

       /* if (mData.size() == 0 && (getHeaderLayoutCount() != 0 || getFooterLayoutCount() != 0)) {
            if (getHeaderLayoutCount() != 0) {
                return TYPE_HEADER;
            } else {
                return TYPE_FOOTER;
            }
        } else if (mFooterLayout != null && position == mData.size() + getHeaderLayoutCount()) {
            return TYPE_FOOTER;
        } else if (mData.size() != 0 && position == mData.size() + getHeaderLayoutCount() +
                getFooterLayoutCount()) {
            return TYPE_LOADING;
        } else {
            return getDefItemViewType(position - getHeaderLayoutCount());
        }*/

    }

    /**
     * whether it is header position
     */
    private boolean isHeaderType(int position) {
        return getHeaderLayoutCount() != 0 && position < getHeaderLayoutCount();
    }

    /**
     * whether it is footer position
     */
    private boolean isFooterType(int position) {
        return getFooterLayoutCount() != 0 && position >= mData.size() + getHeaderLayoutCount();
    }

    /**
     * Whether it is loaded position
     */
    private boolean isLoadMoreType(int position) {
        return mLoadingMoreEnable && mData.size() != 0 && position == mData.size() +
                getHeaderLayoutCount() + getFooterLayoutCount();
    }

    private boolean isEmpty() {
        return mData.size() == 0;
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
//        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        int loadMore = isLoadMore() ? 1 : 0;
        int count = mData.size() + loadMore + getHeaderLayoutCount() + getFooterLayoutCount();

        return count;
    }

    public List<T> getData() {
        return mData;
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    /**
     * 追加数据
     *
     * @param appendData
     */
    public void appendData(List<T> appendData) {
        if (appendData == null || appendData.isEmpty()) return;

        mData.addAll(appendData);
        int i = mData.size() - appendData.size() + getHeaderLayoutCount();
        notifyItemRangeChanged(i, appendData.size());
    }

    /**
     * 向指定位置加入一条数据
     *
     * @param position
     * @param data
     */
    public void add(int position, T data) {
        mData.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * 向指定位置加入一条数据
     *
     * @param data
     */
    public void add(T data) {
        mData.add(data);
        notifyItemInserted(getItemCount() - getHeaderLayoutCount());
    }

    /**
     * 刷新后设置新的数据
     *
     * @param newData
     */
    public void setNewData(List<T> newData) {
        if (newData == null || newData.isEmpty()) return;

        this.mData = newData;

        if (loadMoreFailedView != null) {
            removeFooterView(loadMoreFailedView);
        }
        mLastPosition = -1;
        notifyDataSetChanged();
    }

    /**
     * 移除指定位置的数据
     *
     * @param position
     */
    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public int getHeaderLayoutCount() {
        return mHeaderLayout == null ? 0 : 1;
    }

    public int getFooterLayoutCount() {
        return mFooterLayout == null ? 0 : 1;
    }

    public void loadComplete() {
        mLoading_state = STATE_COMPLETE;
        this.notifyItemChanged(getItemCount());
    }

    private void addLoadMore(RecycledViewHolder holder) {
        if (mLoadingMoreEnable && mLoading_state == STATE_COMPLETE) {
            mLoading_state = STATE_LOADING;
            mLoadMoreListener.onLoadMore();
            holder.setVisible(R.id.loading_progress, true);
            holder.setText(R.id.loading_text, "正在加载...");
            holder.getView(R.id.loading_view).setOnClickListener(null);
        } else if (mLoading_state == STATE_FAILED) {
            holder.setVisible(R.id.loading_progress, false);
            holder.setText(R.id.loading_text, "加载失败，点击重试");
            holder.getView(R.id.loading_view).setOnClickListener(new View.OnClickListener
                    () {

                @Override
                public void onClick(View v) {
                    mLoading_state = STATE_COMPLETE;
                    notifyItemChanged(getHeaderLayoutCount() + mData.size() +
                            getFooterLayoutCount());
                }
            });
        } else if (mLoading_state == STATE_NOMORE) {
            holder.setVisible(R.id.loading_progress, false);
            holder.setText(R.id.loading_text, "已加载全部");
            holder.getView(R.id.loading_view).setOnClickListener(null);
        }
    }

    private boolean isLoadMore() {
        return mLoadingMoreEnable;
    }

    public void setLoadMoreEnable(boolean isLoadMore) {
        mLoadingMoreEnable = isLoadMore;
    }

    public void addHeaderView(View header) {
        addHeaderView(header, -1);
    }

    public void addHeaderView(View header, int index) {
        if (mHeaderLayout == null) {
            if (mCopyHeaderLayout == null) {
                mHeaderLayout = new LinearLayout(header.getContext());
                mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT,
                        WRAP_CONTENT));
                mCopyHeaderLayout = mHeaderLayout;
            } else {
                mHeaderLayout = mCopyHeaderLayout;
            }
        }
        index = index >= mHeaderLayout.getChildCount() ? -1 : index;
        mHeaderLayout.addView(header, index);
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        addFooterView(footer, -1);
    }

    public void addFooterView(View footer, int index) {
        if (mFooterLayout == null) {
            if (mCopyFooterLayout == null) {
                mFooterLayout = new LinearLayout(footer.getContext());
                mFooterLayout.setOrientation(LinearLayout.VERTICAL);
                mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT,
                        WRAP_CONTENT));
                mCopyFooterLayout = mFooterLayout;
            } else {
                mFooterLayout = mCopyFooterLayout;
            }
        }
        index = index >= mFooterLayout.getChildCount() ? -1 : index;
        mFooterLayout.addView(footer, index);
        this.notifyItemChanged(getItemCount());
    }

    public void addLoadingMoreView(View loadMoreView) {
        mLoadingView = loadMoreView;
        this.notifyItemChanged(getItemCount());
    }

    public void removeHeaderView(View header) {
        if (mHeaderLayout == null) return;

        mHeaderLayout.removeView(header);
        if (mHeaderLayout.getChildCount() == 0) {
            mHeaderLayout = null;
        }
        this.notifyDataSetChanged();
    }

    public void removeFooterView(View footer) {
        if (mFooterLayout == null) return;

        mFooterLayout.removeView(footer);
        if (mFooterLayout.getChildCount() == 0) {
            mFooterLayout = null;
        }
        int l = isLoadMore() ? 1 : 0;
        this.notifyItemChanged(getItemCount() - l);
    }

    public void removeAllHeaderView() {
        if (mHeaderLayout == null) return;

        mHeaderLayout.removeAllViews();
        mHeaderLayout = null;
    }

    public void removeAllFooterView() {
        if (mFooterLayout == null) return;

        mFooterLayout.removeAllViews();
        mFooterLayout = null;
    }

    public void setLoadMoreFailedView(View view) {
        loadMoreFailedView = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFooterView(loadMoreFailedView);
                mLoading_state = STATE_COMPLETE;
            }
        });
    }

    public void showLoadMoreFailedView() {
        mLoading_state = STATE_FAILED;
        notifyItemChanged(getHeaderLayoutCount() + mData.size() + getFooterLayoutCount());
    }

    public void showNOMoreView() {
        mLoading_state = STATE_NOMORE;
        notifyItemChanged(getHeaderLayoutCount() + mData.size() + getFooterLayoutCount());
    }

    /**
     * 添加动画
     *
     * @param holder
     */
    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimation) {
            if (!mFirstOnlyAnim || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation baseAnimation = null;
                if (null != mCurrentAnim) {
                    baseAnimation = mCurrentAnim;
                } else {
                    baseAnimation = mDefAnim;
                }
                for (Animator animator : baseAnimation.getAnimators(holder.itemView)) {
                    startAnim(animator, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    protected void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public void setFirstOnlyAnim(boolean firstOnlyAnim) {
        mFirstOnlyAnim = firstOnlyAnim;
    }

    public void openAnimation(@AnimationType int animType) {
        mOpenAnimation = true;
        mCurrentAnim = null;
        switch (animType) {
            case ALPHAIN:
                mDefAnim = new AlphaInAnimation();
                break;
            case SCALEIN:
                mDefAnim = new ScaleInAnimation();
                break;
            case SLIDEIN_LEFT:
                mDefAnim = new SlideInLeftAnimation();
                break;
            case SLIDEIN_RIGHT:
                mDefAnim = new SlideInRightAnimation();
                break;
            case SLIDEIN_BOTTOM:
                mDefAnim = new SlideInBottomAnimation();
                break;

            default:

                break;
        }

    }

    public void resetState() {
        mLoading_state = STATE_COMPLETE;
    }

    public void openAnimation(BaseAnimation animation) {
        mOpenAnimation = true;
        mCurrentAnim = animation;
    }

    public void openAnimation() {
        mOpenAnimation = true;

    }

    protected abstract void convert(RecycledViewHolder holder, T item);

    public void setOnLoadMoreListener(LoadMoreListener onLoadMoreListener) {
        this.mLoadMoreListener = onLoadMoreListener;
    }

    public interface LoadMoreListener {

        void onLoadMore();

    }
}
