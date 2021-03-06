package com.simon.dribbble.ui.user;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.ui.CommListFragment;
import com.simon.dribbble.ui.CommListPresenter;
import com.simon.dribbble.ui.shots.ShotDetailActivity;
import com.simon.dribbble.ui.shots.ShotsAdapter;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 13:52
 */

public class UserLikesFragment extends CommListFragment<LikeShotsPresenter, ShotsAdapter> {

    private int mPageNo = 1;

    public static UserLikesFragment newInstance() {
        UserLikesFragment fragment = new UserLikesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.loadList(Api.EVENT_BEGIN, 0, "", mPageNo);
    }

    protected void itemClick(View view, int position) {
        ShotEntity shot = mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putLong("shotId", shot.getId());
        startIntent(ShotDetailActivity.class, bundle);
    }

    @Override
    protected LikeShotsPresenter getPresenter() {
        return new LikeShotsPresenter(this);
    }

    @Override
    public void setPresenter(CommListPresenter presenter) {

    }

    @Override
    protected ShotsAdapter getListAdapter() {
        return new ShotsAdapter();
    }

    @Override
    public void refreshData() {
        mPageNo = 1;
        mPresenter.loadList(Api.EVENT_REFRESH, 0, "", mPageNo);
    }

    @Override
    public void moreData() {
        mPageNo++;
        mPresenter.loadList(Api.EVENT_MORE, 0, "", mPageNo);
    }

    @Override
    protected void retry(int action) {
        super.retry(action);
        mPresenter.loadList(action, 0, "", mPageNo);
    }
}
