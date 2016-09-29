package com.simon.dribbble.ui.shots;

import android.view.View;
import android.widget.Button;

import com.simon.dribbble.R;
import com.simon.dribbble.ui.BaseActivity;
import com.simon.dribbble.ui.user.SignInContract;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 11:24
 */

public class SearchActivity extends BaseActivity<SearchPresenter> implements SignInContract.View {

    private SearchPresenter mPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchPresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        Button button = (Button) findViewById(R.id.btn_search);
        button.setOnClickListener(this);

        mPresenter = new SearchPresenter(this);
    }

    @Override
    protected void initEventAndData() {

        mPresenter.load();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        btn_test();
    }

    public void btn_test() {

    }

    @Override
    public void signSuccess() {

    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void onFailed(int action, String msg) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {

    }
}
