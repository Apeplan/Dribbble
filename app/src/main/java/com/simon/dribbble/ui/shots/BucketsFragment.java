package com.simon.dribbble.ui.shots;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.ui.CommListFragment;
import com.simon.dribbble.ui.CommListPresenter;
import com.simon.dribbble.ui.buckets.UserBucketsAdapter;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 17:29
 */

public class BucketsFragment extends CommListFragment<BucketsPresenter, UserBucketsAdapter> {

    private int mPage = 1;
    private long mShotId;

    public static BucketsFragment newInstance() {
        BucketsFragment fragment = new BucketsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BucketsPresenter getPresenter() {
        return new BucketsPresenter(this);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        Bundle args = getArguments();
        if (null != args) {
            mShotId = args.getLong("shotId");
            mPresenter.loadList(Api.EVENT_BEGIN, mShotId, "shots", 1);
        } else {
            onEmpty("");
        }
    }

    @Override
    protected UserBucketsAdapter getListAdapter() {
        return new UserBucketsAdapter();
    }

    protected void itemClick(View view, int position) {

    }

    @Override
    public void refreshData() {
        mPage = 1;
        mPresenter.loadList(Api.EVENT_REFRESH, mShotId, "shots", mPage);
    }

    @Override
    public void moreData() {
        mPage++;
        mPresenter.loadList(Api.EVENT_MORE, mShotId, "shots", mPage);
    }

    @Override
    public void setPresenter(CommListPresenter presenter) {

    }

    @Override
    protected void retry(int action) {
        super.retry(action);
        mPresenter.loadList(action, mShotId, "shots", mPage);
    }
}
