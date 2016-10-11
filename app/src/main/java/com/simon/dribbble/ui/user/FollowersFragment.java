package com.simon.dribbble.ui.user;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.model.FollowersEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.ui.baselist.BaseListContract;
import com.simon.dribbble.ui.baselist.BaseListFragment;

import net.quickrecyclerview.show.BaseQuickAdapter;

/**
 * Created by Simon Han on 2016/9/11.
 */

public class FollowersFragment extends BaseListFragment<FollowersEntity> {

    public static FollowersFragment newInstance() {
        FollowersFragment fragment = new FollowersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseListContract.Presenter getPresenter() {
        return new FollowersPresenter(this);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.loadList(0, "",1, DribbbleApi.EVENT_BEGIN);
    }

    @Override
    protected BaseQuickAdapter<FollowersEntity> getListAdapter() {
        return new FollowersAdapter();
    }

    @Override
    protected void itemClick(View view, int position) {

    }

    @Override
    public void setPresenter(BaseListContract.Presenter presenter) {

    }

}
