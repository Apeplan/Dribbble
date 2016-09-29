package com.simon.dribbble.ui.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.simon.dribbble.R;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.ui.BaseFragment;
import com.simon.dribbble.ui.shots.ShotsAdapter;
import com.simon.dribbble.widget.statelayout.StateLayout;

import net.quickrecyclerview.XRecyclerView;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 18:10
 */

public class UserShotsFragment extends BaseFragment<UserShotsContract.Presenter> implements
        UserShotsContract.View {


    public static UserShotsFragment newInstance() {
        UserShotsFragment fragment = new UserShotsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private StateLayout mStateLayout;
    private XRecyclerView mXRecyclerView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_shots;
    }

    @Override
    protected UserShotsContract.Presenter getPresenter() {
        return new UserShotsPresenter(this);
    }

    @Override
    protected void initView(View view) {
        mStateLayout = (StateLayout) view.findViewById(R.id.progress_loading);
        mXRecyclerView = (XRecyclerView) view.findViewById(R.id.xrv_shots);
        LinearLayoutManager rlm = new LinearLayoutManager(getActivity());
        mXRecyclerView.setLayoutManager(rlm);
    }

    @Override
    protected void initEventAndData() {
        mStateLayout.showProgressView();
        mPresenter.loadShots(1);
    }

    @Override
    public void showShots(List<ShotEntity> shots) {
        mStateLayout.showContentView();
        ShotsAdapter adapter = new ShotsAdapter(shots);
        mXRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onEmpty() {
        mStateLayout.showEmptyView();
    }

    @Override
    public void onFailed(int action, String msg) {
        mStateLayout.showErrorView();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void setPresenter(UserShotsContract.Presenter presenter) {

    }
}
