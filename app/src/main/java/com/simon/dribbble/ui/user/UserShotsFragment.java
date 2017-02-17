package com.simon.dribbble.ui.user;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.ui.CommListFragment;
import com.simon.dribbble.ui.CommListPresenter;
import com.simon.dribbble.ui.shots.ShotsAdapter;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 18:10
 */

public class UserShotsFragment extends CommListFragment<UserShotsPresenter, ShotsAdapter> {
    private int mPage = 1;

    public static UserShotsFragment newInstance() {
        UserShotsFragment fragment = new UserShotsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected UserShotsPresenter getPresenter() {
        return new UserShotsPresenter(this);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.loadList(Api.EVENT_BEGIN, 0, "", mPage);
    }


    protected void itemClick(View view, int position) {

    }

    @Override
    public void setPresenter(CommListPresenter presenter) {

    }

    @Override
    public void refreshData() {
        mPage = 1;
        mPresenter.loadList(Api.EVENT_REFRESH, 0, "", mPage);
    }

    @Override
    public void moreData() {
        mPage++;
        mPresenter.loadList(Api.EVENT_MORE, 0, "", mPage);
    }

    @Override
    protected ShotsAdapter getListAdapter() {
        return new ShotsAdapter();
    }

    @Override
    protected void retry(int action) {
        super.retry(action);
        mPresenter.loadList(action, 0, "", mPage);
    }
}
