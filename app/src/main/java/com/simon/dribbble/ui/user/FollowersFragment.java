package com.simon.dribbble.ui.user;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.ui.CommListFragment;
import com.simon.dribbble.ui.CommListPresenter;

/**
 * Created by Simon Han on 2016/9/11.
 */

public class FollowersFragment extends CommListFragment<FollowersPresenter, FollowersAdapter> {

    private int mPageNo = 1;

    public static FollowersFragment newInstance() {
        FollowersFragment fragment = new FollowersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected FollowersPresenter getPresenter() {
        return new FollowersPresenter(this);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.loadList(Api.EVENT_BEGIN, 0, "", mPageNo);
    }

    protected void itemClick(View view, int position) {

    }

    @Override
    public void setPresenter(CommListPresenter presenter) {

    }


    @Override
    public void refreshData() {
        mPageNo = 1;
        mPresenter.loadList(Api.EVENT_REFRESH, 0, "", mPageNo);
    }

    @Override
    protected FollowersAdapter getListAdapter() {
        return new FollowersAdapter();
    }

    @Override
    protected boolean isLoadMoreEnabled() {
        return false;
    }

    @Override
    protected void retry(int action) {
        super.retry(action);
        mPresenter.loadList(action, 0, "", mPageNo);
    }
}
