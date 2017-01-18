package com.simon.dribbble.ui.shots;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.simon.agiledevelop.mvpframe.BaseFragment;
import com.simon.agiledevelop.mvpframe.RxPresenter;
import com.simon.agiledevelop.recycler.adapter.RecycledAdapter;
import com.simon.agiledevelop.recycler.listeners.OnItemClickListener;
import com.simon.dribbble.R;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.ui.baselist.BaseListContract;
import com.simon.dribbble.util.DialogHelp;
import com.simon.dribbble.widget.loadingdia.SpotsDialog;
import com.simon.dribbble.widget.statelayout.StateLayout;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 16:12
 */

public class AttachmentFragment extends BaseFragment<AttachPresenter> implements BaseListContract
        .View, SwipeRefreshLayout.OnRefreshListener, RecycledAdapter.LoadMoreListener {
    private StateLayout mStateLayout;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private AttachAdapter mAdapter;
    private int mPage = 1;

    private SpotsDialog mLoadingDialog;

    public static AttachmentFragment newInstance() {
        AttachmentFragment fragment = new AttachmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private long mShotId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected AttachPresenter getPresenter() {
        return new AttachPresenter(this);
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRefreshLayout.setColorSchemeResources(R.color.purple_500, R.color.blue_500, R.color
                .orange_500, R.color.pink_500);
        mStateLayout = (StateLayout) view.findViewById(R.id.stateLayout_list);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.xrlv_list);
        LinearLayoutManager rlm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(rlm);
        mRefreshLayout.setOnRefreshListener(this);
        if (mAdapter == null) {
            mAdapter = new AttachAdapter();
            mAdapter.openAnimation(RecycledAdapter.SCALEIN);
            mAdapter.setLoadMoreEnable(true);
            mAdapter.setOnLoadMoreListener(this);
        }

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            protected void onItemClick(RecycledAdapter adapter, RecyclerView recyclerView, View
                    view, int position) {
                itemClick(view, position);
            }
        });

    }

    @Override
    protected void initEventAndData() {
        Bundle args = getArguments();
        if (null != args) {
            mShotId = args.getLong("shotId");
            mPresenter.loadList(mShotId, 1, Api.EVENT_BEGIN);
        } else {
            onEmpty("");
        }
    }


    protected void itemClick(View view, int position) {

    }

    @Override
    protected View getLoadingView() {
        return null;
    }

    @Override
    public void showLoading(int action, String msg) {
        if (Api.EVENT_BEGIN == action) {
            showDialog();
        }
    }

    @Override
    public void onEmpty(String msg) {
        hideDialog();
        mStateLayout.showEmptyView();
    }

    @Override
    public void onFailed(int action, String msg) {
        hideDialog();
        mStateLayout.showErrorView();
    }

    @Override
    public void onCompleted(int action) {

    }

    @Override
    public void setPresenter(RxPresenter presenter) {

    }

    @Override
    public void showList(List lists) {
        mStateLayout.showContentView();
        hideDialog();

        mAdapter.setNewData(lists);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void refreshComments(List lists) {
        mRefreshLayout.setRefreshing(false);
        if (!lists.isEmpty()) {
            mAdapter.setNewData(lists);
        } else {
            mStateLayout.showEmptyView();
        }
    }

    @Override
    public void moreComments(List lists) {
        mAdapter.loadComplete();

        if (!lists.isEmpty()) {
            mAdapter.appendData(lists);
        } else {
            mAdapter.showNOMoreView();
        }
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        mPresenter.loadList(mShotId, mPage, Api.EVENT_REFRESH);
    }

    @Override
    public void onLoadMore() {
        mPage++;
        mPresenter.loadList(mShotId, mPage, Api.EVENT_MORE);
    }


    private void showDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogHelp.getLoadingDialog(getActivity(), "正在加载...");
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void hideDialog() {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
