package com.simon.dribbble.ui.shots;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.simon.dribbble.R;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.ui.BaseActivity;
import com.simon.dribbble.widget.statelayout.StateLayout;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 11:24
 */

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {

    private ShotsAdapter mShotsAdapter;
    private StateLayout mStateLayout;

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchPresenter getPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected void initView() {
        mStateLayout = (StateLayout) findViewById(R.id.progress_loading);
        Button button = (Button) findViewById(R.id.btn_search);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rlv_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        button.setOnClickListener(this);

        mShotsAdapter = new ShotsAdapter();
        recyclerView.setAdapter(mShotsAdapter);
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        mStateLayout.showProgressView();
        btn_test();
    }

    public void btn_test() {
        mPresenter.searchShot("1", 1, DribbbleApi.SORT_POPULAR);
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
    public void setPresenter(SearchContract.Presenter presenter) {

    }

    @Override
    public void showSearch(List<ShotEntity> shots) {
        mStateLayout.showContentView();
        if (null != mShotsAdapter) {
            mShotsAdapter.addData(shots);
        }
    }
}
