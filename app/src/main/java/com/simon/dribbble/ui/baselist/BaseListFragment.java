package com.simon.dribbble.ui.baselist;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.simon.dribbble.R;
import com.simon.dribbble.ui.BaseFragment;
import com.simon.dribbble.widget.statelayout.StateLayout;

import net.quickrecyclerview.XRecyclerView;
import net.quickrecyclerview.show.BaseQuickAdapter;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/13 9:51
 */

public abstract class BaseListFragment<T> extends BaseFragment<BaseListContract.Presenter>
        implements BaseListContract.View, XRecyclerView.LoadingListener {

    private StateLayout mStateLayout;
    private XRecyclerView mXRecyclerView;
    protected BaseQuickAdapter<T> mAdapter;
    private int mPage = 1;

    @Override
    protected int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(View view) {
        mStateLayout = (StateLayout) view.findViewById(R.id.stateLayout_list);
        mXRecyclerView = (XRecyclerView) view.findViewById(R.id.xrlv_list);
        LinearLayoutManager rlm = new LinearLayoutManager(getActivity());
        mXRecyclerView.setLayoutManager(rlm);
        mXRecyclerView.setLoadingListener(this);

        if (mAdapter == null) {
            mAdapter = getListAdapter();
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        }

        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter
                .OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                itemClick(view, position);
            }
        });
        mXRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEventAndData() {
        mStateLayout.showProgressView();
    }

    protected abstract BaseQuickAdapter<T> getListAdapter();

    protected abstract void itemClick(View view, int position);

    @Override
    public void showList(List lists) {
        mStateLayout.showContentView();
        mAdapter.removeAll();
        mAdapter.addData(lists);
    }

    @Override
    public void refreshComments(List lists) {
        mXRecyclerView.refreshComplete();
        if (!lists.isEmpty()) {
            mAdapter.removeAll();
            mAdapter.addData(lists);
        } else {
            mStateLayout.showEmptyView();
        }
    }

    @Override
    public void moreComments(List lists) {
        mXRecyclerView.loadMoreComplete();
        if (!lists.isEmpty()) {
            mAdapter.addData(lists);
        } else {
            mXRecyclerView.setNoMore(true);
//            ToastHelper.shortToast("没有更多数据了");
        }
    }

    @Override
    public void onEmpty() {
        mStateLayout.showEmptyView();
    }

    @Override
    public void onFailed(int action, String msg) {
        mStateLayout.showErrorView();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
