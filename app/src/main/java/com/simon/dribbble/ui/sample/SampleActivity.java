package com.simon.dribbble.ui.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.simon.dribbble.R;
import com.simon.dribbble.data.model.Cook;
import com.simon.dribbble.widget.statelayout.StateLayout;

import java.util.List;

public class SampleActivity extends AppCompatActivity implements SampleContract.View {

    private SamplePresenter mPresenter;
    private RecyclerView mRecyclerView;
//    private ProgressLayout mProgressLayout;
    private StateLayout mProgressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
//        mProgressLayout = (ProgressLayout) findViewById(R.id.progress_loading);
        mProgressLayout = (StateLayout) findViewById(R.id.progress_loading);
        mRecyclerView = (RecyclerView) findViewById(R.id.rlv_sample);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mPresenter = new SamplePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void setLoading(boolean active) {
//        if (active) {
//            mProgressLayout.showLoading();
//        } else {
//            mProgressLayout.hideLoadingView();
//        }

        mProgressLayout.showProgressView();
    }

    @Override
    public void showCooks(List<Cook> cooks) {
//        mProgressLayout.showContent();
        mProgressLayout.showContentView();
        CookAdapter adapter = new CookAdapter(cooks);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onEmpty() {
//        mProgressLayout.showNotDta(null);
        mProgressLayout.showEmptyView();
    }

    @Override
    public void onFailed(int action, String msg) {
        mProgressLayout.showErrorView();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void setPresenter(SampleContract.Presenter presenter) {

    }

}
