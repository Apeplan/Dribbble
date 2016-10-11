package com.simon.dribbble.ui.projects;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.model.ProjectEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.ui.baselist.BaseListContract;
import com.simon.dribbble.ui.baselist.BaseListFragment;

import net.quickrecyclerview.show.BaseQuickAdapter;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/31 11:41
 */

public class ProjectsFragment extends BaseListFragment<ProjectEntity> {

    public static ProjectsFragment newInstance() {
        ProjectsFragment fragment = new ProjectsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseListContract.Presenter getPresenter() {
        return new ProjectPresenter(this);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.loadList(0,"", 1, DribbbleApi.EVENT_BEGIN);
    }

    @Override
    protected BaseQuickAdapter<ProjectEntity> getListAdapter() {
        return new ProjectAdapter();
    }

    @Override
    protected void itemClick(View view, int position) {

    }

    @Override
    public void setPresenter(BaseListContract.Presenter presenter) {

    }

}
