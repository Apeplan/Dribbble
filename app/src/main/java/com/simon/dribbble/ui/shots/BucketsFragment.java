package com.simon.dribbble.ui.shots;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.BucketEntity;
import com.simon.dribbble.ui.baselist.BaseListContract;
import com.simon.dribbble.ui.baselist.BaseListFragment;
import com.simon.dribbble.ui.buckets.UserBucketsAdapter;

import net.quickrecyclerview.show.BaseQuickAdapter;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 17:29
 */

public class BucketsFragment extends BaseListFragment<BucketEntity> {

    public static BucketsFragment newInstance() {
        BucketsFragment fragment = new BucketsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private long mShotId;

    @Override
    protected BaseQuickAdapter<BucketEntity> getListAdapter() {
        return new UserBucketsAdapter();
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        Bundle args = getArguments();
        if (null != args) {
            mShotId = args.getLong("shotId");
            mPresenter.loadList(mShotId, "shots", 1, Api.EVENT_BEGIN);
        } else {
            onEmpty();
        }
    }

    @Override
    protected void itemClick(View view, int position) {

    }

    @Override
    protected BaseListContract.Presenter getPresenter() {
        return new BucketsPresenter(this);
    }

    @Override
    public void setPresenter(BaseListContract.Presenter presenter) {

    }
}
