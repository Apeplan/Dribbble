package com.simon.dribbble.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.simon.agiledevelop.mvpframe.BaseActivity;
import com.simon.agiledevelop.mvpframe.Presenter;
import com.simon.agiledevelop.state.StateView;
import com.simon.dribbble.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected StateView getLoadingView() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setCommonBackToolBack(toolbar, "关于");
    }

    @Override
    protected void initEventAndData() {

    }
}
