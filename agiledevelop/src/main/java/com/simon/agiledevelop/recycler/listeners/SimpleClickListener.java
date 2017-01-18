package com.simon.agiledevelop.recycler.listeners;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.simon.agiledevelop.recycler.RecycledViewHolder;
import com.simon.agiledevelop.recycler.adapter.RecycledAdapter;

import java.util.Iterator;
import java.util.Set;

/**
 * describe: simple implement for RecyclerView item click listener
 *
 * @author Simon Han
 * @date 2015.09.17
 * @email hanzx1024@gmail.com
 */

public abstract class SimpleClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetectorCompat mDetectorCompat;
    private RecyclerView mRecyclerView;
    private RecycledAdapter mBaseAdapter;
    private View mPressedView = null;

    private boolean mIsShowPress = false;
    private boolean mIsPrepressed = false;// 是否按下
    private Set<Integer> childClickViewIds;
    private Set<Integer> longClickViewIds;

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mRecyclerView == null) {
            mRecyclerView = rv;
            mBaseAdapter = (RecycledAdapter) mRecyclerView.getAdapter();
            mDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(), new
                    ItemTouchHelperGestureDetectorListener(mRecyclerView));
        }

        if (!mDetectorCompat.onTouchEvent(e) && e.getActionMasked() == MotionEvent.ACTION_UP &&
                mIsShowPress) {
            if (null != mPressedView) {
                mPressedView.setPressed(false);
                mPressedView = null;
            }
            mIsShowPress = false;
            mIsPrepressed = false;
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private class ItemTouchHelperGestureDetectorListener extends GestureDetector
            .SimpleOnGestureListener {
        private RecyclerView mRv;

        public ItemTouchHelperGestureDetectorListener(RecyclerView recyclerView) {
            mRv = recyclerView;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            mIsPrepressed = true;
            mPressedView = mRv.findChildViewUnder(e.getX(), e.getY());
            super.onDown(e);
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            if (mIsPrepressed && mPressedView != null) {
                mPressedView.setPressed(true);
                mIsShowPress = true;
            }
            super.onShowPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mIsPrepressed && mPressedView != null) {
                mPressedView.setPressed(true);
                final View pressedView = mPressedView;
                RecycledViewHolder bvh = (RecycledViewHolder) mRv.getChildViewHolder(pressedView);
                // 判断是不是头、尾、加载更多
                if (isHeaderOrFooterPosition(bvh.getLayoutPosition())) {
                    return false;
                }
                childClickViewIds = bvh.getChildClickViewIds();
                int position = bvh.getLayoutPosition() - mBaseAdapter.getHeaderLayoutCount();
                if (null != childClickViewIds && !childClickViewIds.isEmpty()) {
                    for (Iterator it = childClickViewIds.iterator(); it.hasNext(); ) {
                        View view = pressedView.findViewById((Integer) it.next());
                        if (inRangeOfView(view, e) && view.isEnabled()) {
                            onItemChildClick(mBaseAdapter, mRv, view, position);
                            resetPressedView(view);
                            return true;
                        }
                    }
                    onItemClick(mBaseAdapter, mRv, pressedView, position);
                } else {
                    onItemClick(mBaseAdapter, mRv, pressedView, position);
                }
                resetPressedView(pressedView);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            boolean isLongClick = false;
            if (mIsPrepressed && mPressedView != null) {
                RecycledViewHolder bvh = (RecycledViewHolder) mRv.getChildViewHolder
                        (mPressedView);
                int position = bvh.getLayoutPosition() - mBaseAdapter.getHeaderLayoutCount();

                if (!isHeaderOrFooterPosition(bvh.getLayoutPosition())) {
                    longClickViewIds = bvh.getItemChildLongClickViewIds();
                    if (longClickViewIds != null && longClickViewIds.size() > 0) {
                        for (Iterator it = longClickViewIds.iterator(); it.hasNext(); ) {
                            View childView = mPressedView.findViewById((Integer) it.next());
                            if (inRangeOfView(childView, e) && childView.isEnabled()) {
                                onItemChildLongClick(mBaseAdapter, mRv, childView, position);
                                mPressedView.setPressed(true);
                                mIsShowPress = true;
                                isLongClick = true;
                                break;
                            }
                        }
                    }
                    if (!isLongClick) {
                        onItemLongClick(mBaseAdapter, mRv, mPressedView, position);
                        mPressedView.setPressed(true);
                        mIsShowPress = true;
                    }

                }

            }

        }

        /**
         * 重置 View 状态
         *
         * @param pressedView
         */
        private void resetPressedView(final View pressedView) {
            if (pressedView != null) {
                pressedView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pressedView != null) {
                            pressedView.setPressed(false);
                        }

                    }
                }, 100);
            }

            mIsPrepressed = false;
            mPressedView = null;
        }
    }

    protected abstract void onItemLongClick(RecycledAdapter adapter, RecyclerView recyclerView,
                                            View
            view, int position);

    protected abstract void onItemChildLongClick(RecycledAdapter adapter, RecyclerView
            recyclerView,
                                                 View view, int position);

    protected abstract void onItemClick(RecycledAdapter adapter, RecyclerView recyclerView, View
            view, int position);

    protected abstract void onItemChildClick(RecycledAdapter adapter, RecyclerView recyclerView,
                                             View view, int position);

    /**
     * 判断是否是 View 所在的范围
     *
     * @param view
     * @param ev
     * @return
     */
    public boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        if (view.getVisibility() != View.VISIBLE) {
            return false;
        }
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getRawX() < x
                || ev.getRawX() > (x + view.getWidth())
                || ev.getRawY() < y
                || ev.getRawY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    /**
     * 判断指定位置的 View 是不是头、尾、加载更多
     *
     * @param position 要判断的位置
     * @return
     */
    private boolean isHeaderOrFooterPosition(int position) {
        /**
         *  have a headview and EMPTY_VIEW FOOTER_VIEW LOADING_VIEW
         */
        int type = mBaseAdapter.getItemViewType(position);
        return (type == RecycledAdapter.TYPE_HEADER || type == RecycledAdapter.TYPE_FOOTER ||
                type ==
                RecycledAdapter.TYPE_LOADING);
    }
}
