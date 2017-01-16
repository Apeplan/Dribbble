package com.simon.dribbble.ui.projects;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.ui.CommListFragment;
import com.simon.dribbble.ui.CommListPresenter;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/31 11:41
 */

public class ProjectsFragment extends CommListFragment<ProjectPresenter, ProjectAdapter> {

    private int mPageNo = 1;

    public static ProjectsFragment newInstance() {
        ProjectsFragment fragment = new ProjectsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ProjectPresenter getPresenter() {
        return new ProjectPresenter(this);
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
    protected ProjectAdapter getListAdapter() {
        return new ProjectAdapter();
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
}
