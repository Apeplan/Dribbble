package com.simon.dribbble.ui.user;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.UserLikeEntity;
import com.simon.dribbble.ui.baselist.BaseListContract;
import com.simon.dribbble.ui.baselist.BaseListFragment;
import com.simon.dribbble.util.ToastHelper;

import net.quickrecyclerview.show.BaseQuickAdapter;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 13:52
 */

public class UserLikesFragment extends BaseListFragment<UserLikeEntity> {

    public static UserLikesFragment newInstance() {
        UserLikesFragment fragment = new UserLikesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private int mPage = 1;

    @Override
    protected BaseQuickAdapter<UserLikeEntity> getListAdapter() {
        return new LikeShotsAdapter();
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.loadList(0, "", mPage, Api.EVENT_BEGIN);
    }

    @Override
    protected void itemClick(View view, int position) {
        ToastHelper.shortToast("点击事件  " + position);
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        mPresenter.loadList(0, "", mPage, Api.EVENT_REFRESH);
    }

    @Override
    public void onLoadMore() {
        mPage++;
        mPresenter.loadList(0, "", mPage, Api.EVENT_MORE);
    }

    @Override
    protected BaseListContract.Presenter getPresenter() {
        return new LikeShotsPresenter(this);
    }

    @Override
    public void setPresenter(BaseListContract.Presenter presenter) {

    }

}
