package com.simon.dribbble.ui.shots;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.simon.dribbble.R;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.ui.BaseActivity;
import com.simon.dribbble.widget.statelayout.StateLayout;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 11:24
 */

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {

    private ShotsAdapter mShotsAdapter;
    private StateLayout mStateLayout;
    private SearchView mSearchView;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setCommonBackToolBack(toolbar, "搜索");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mStateLayout = (StateLayout) findViewById(R.id.progress_loading);
        mSearchView = (SearchView) findViewById(R.id.search_view);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rlv_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        mShotsAdapter = new ShotsAdapter();
        recyclerView.setAdapter(mShotsAdapter);
    }

    @Override
    protected void initEventAndData() {

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LLog.d("Simon Han", "onQueryTextSubmit: " + query);
                if (!TextUtils.isEmpty(query)) {
                    mStateLayout.showProgressView();
                    search(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LLog.d("Simon Han", "onQueryTextChange: " + newText);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    public void search(String query) {
        mSearchView.clearFocus();
        mPresenter.searchShot(query, 1, DribbbleApi.SORT_POPULAR);
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
