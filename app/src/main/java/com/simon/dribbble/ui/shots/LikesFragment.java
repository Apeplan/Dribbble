package com.simon.dribbble.ui.shots;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.model.LikeEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.ui.baselist.BaseListContract;
import com.simon.dribbble.ui.baselist.BaseListFragment;
import com.simon.dribbble.ui.user.UserInfoActivity;

import net.quickrecyclerview.show.BaseQuickAdapter;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 13:52
 */

public class LikesFragment extends BaseListFragment<LikeEntity> {

    private long mShotId;

    public static LikesFragment newInstance() {
        LikesFragment fragment = new LikesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private int mPage = 1;

    @Override
    protected BaseQuickAdapter<LikeEntity> getListAdapter() {
        return new LikesAdapter();
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        Bundle args = getArguments();
        if (null != args) {
            mShotId = args.getLong("shotId");
            mPresenter.loadList(mShotId, "", mPage, DribbbleApi.EVENT_BEGIN);
        } else {
            onEmpty();
        }
    }

    @Override
    protected void itemClick(View view, int position) {
//        ToastHelper.shortToast("点击事件  " + position);
        LikeEntity item = mAdapter.getItem(position);
        long id = item.getUser().id;
        String name = item.getUser().name;
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putLong("userId", id);
        startIntent(UserInfoActivity.class, bundle);
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        mPresenter.loadList(mShotId, "", mPage, DribbbleApi.EVENT_REFRESH);
    }

    @Override
    public void onLoadMore() {
        mPage++;
        mPresenter.loadList(mShotId, "", mPage, DribbbleApi.EVENT_MORE);
    }

    @Override
    protected BaseListContract.Presenter getPresenter() {
        return new LikesPresenter(this);
    }

    @Override
    public void setPresenter(BaseListContract.Presenter presenter) {

    }

}
