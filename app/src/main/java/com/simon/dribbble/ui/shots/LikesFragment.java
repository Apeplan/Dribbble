package com.simon.dribbble.ui.shots;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.LikeEntity;
import com.simon.dribbble.ui.CommListFragment;
import com.simon.dribbble.ui.CommListPresenter;
import com.simon.dribbble.ui.user.UserInfoActivity;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 13:52
 */

public class LikesFragment extends CommListFragment<LikesPresenter,LikesAdapter> {

    private long mShotId;

    public static LikesFragment newInstance() {
        LikesFragment fragment = new LikesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private int mPage = 1;

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        Bundle args = getArguments();
        if (null != args) {
            mShotId = args.getLong("shotId");
            mPresenter.loadList(Api.EVENT_BEGIN, mShotId, "", mPage);
        } else {
            onEmpty("");
        }
    }

    @Override
    protected LikesAdapter getListAdapter() {
        return new LikesAdapter();
    }

    @Override
    protected void itemClick(View view, int position) {
        LikeEntity item = mAdapter.getItem(position);
        long id = item.getUser().id;
        String name = item.getUser().name;
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putLong("userId", id);
        startIntent(UserInfoActivity.class, bundle);
    }

    @Override
    protected LikesPresenter getPresenter() {
        return new LikesPresenter(this);
    }

    @Override
    public void refreshData() {
        mPage = 1;
        mPresenter.loadList(Api.EVENT_REFRESH, mShotId, "", mPage);
    }

    @Override
    public void moreData() {
        mPage++;
        mPresenter.loadList(Api.EVENT_MORE, mShotId, "", mPage);
    }


    @Override
    public void setPresenter(CommListPresenter presenter) {

    }
}
