package com.simon.dribbble.ui.team;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.TeamEntity;
import com.simon.dribbble.ui.baselist.BaseListContract;
import com.simon.dribbble.ui.baselist.BaseListFragment;

import net.quickrecyclerview.show.BaseQuickAdapter;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/31 12:29
 */

public class TeamFragment extends BaseListFragment<TeamEntity> {

    public static TeamFragment newInstance() {
        TeamFragment fragment = new TeamFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseListContract.Presenter getPresenter() {
        return new TeamPresenter(this);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.loadList(0,"", 1, Api.EVENT_BEGIN);
    }

    @Override
    protected BaseQuickAdapter<TeamEntity> getListAdapter() {
        return new TeamAdapter();
    }

    @Override
    protected void itemClick(View view, int position) {

    }

    @Override
    public void setPresenter(BaseListContract.Presenter presenter) {

    }
}
