package com.simon.dribbble.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.simon.dribbble.DribbbleApp;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2015/7/22 16:50
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements View
        .OnClickListener {

    protected Context mContext = null;
    protected T mPresenter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        if (getLayout() != 0) {
            return inflater.inflate(getLayout(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = getPresenter();
        initView(view);
        initEventAndData();
    }

    /**
     * 返回页面的布局资源ID
     *
     * @return
     */
    protected abstract int getLayout();

    /**
     * 返回 Presenter
     *
     * @return
     */
    protected abstract T getPresenter();

    /**
     * 初始化控件
     */
    protected abstract void initView(View view);

    /**
     * 初始化事件，和数据
     */
    protected abstract void initEventAndData();

    @Override
    public void onClick(View v) {
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // 监听Fragment 内存泄漏
        RefWatcher refWatcher = DribbbleApp.getRefWatcher(getActivity());
        refWatcher.watch(this);

        if (null != mPresenter) {
            mPresenter.unsubscribe();
        }
    }

    protected void startIntent(Class aClass) {
        startIntent(aClass, null);
    }

    protected void startIntent(Class aClass, Bundle bundle) {
        Intent intent = new Intent(getActivity(), aClass);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startForResultIntent(Class aClass, int requestCode) {
        startForResultIntent(aClass, null, requestCode);
    }

    protected void startForResultIntent(Class aClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), aClass);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 隐藏控件
     */
    protected void hideControl(View v) {
        v.setVisibility(View.GONE);
    }

    /**
     * 显示控件
     */
    protected void showControl(View v) {
        v.setVisibility(View.VISIBLE);
    }

    /**
     * 显示键盘
     */
    @SuppressWarnings("static-access")
    protected void showSoftInput(EditText et) {
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext
                .INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, 0);
    }

    /**
     * 隐藏键盘
     */
    @SuppressWarnings("static-access")
    protected void hideSoftInput(EditText et) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
