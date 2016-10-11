package com.simon.dribbble.ui.user;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.ui.baselist.BaseListContract;
import com.simon.dribbble.ui.baselist.BaseListFragment;
import com.simon.dribbble.ui.shots.ShotsAdapter;

import net.quickrecyclerview.show.BaseQuickAdapter;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 18:10
 */

public class UserShotsFragment extends BaseListFragment<ShotEntity> {

    private int mPage = 1;

    public static UserShotsFragment newInstance() {
        UserShotsFragment fragment = new UserShotsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseListContract.Presenter getPresenter() {
        return new UserShotsPresenter(this);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.loadList(0, "", mPage, DribbbleApi.EVENT_BEGIN);
    }

    @Override
    protected BaseQuickAdapter<ShotEntity> getListAdapter() {
        return new ShotsAdapter();
    }

    @Override
    protected void itemClick(View view, int position) {

    }


    @Override
    public void setPresenter(BaseListContract.Presenter presenter) {

    }
}
