package com.simon.dribbble.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.simon.dribbble.R;
import com.simon.dribbble.ui.shots.DetailBigPicActivity;

/**
 * Created by Simon Han on 2016/8/20.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements
        View.OnClickListener {

    protected Activity mContext;
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        setStatusBarTranslucent();

        mContext = this;
        mPresenter = getPresenter();
        initView();
        initEventAndData();
    }

    /**
     * 设置通用的 ToolBar
     *
     * @param toolbar
     * @param title
     */
    protected void setCommonBackToolBack(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new Toolbar.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // TODO:适配4.4
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarTranslucent() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                !(this instanceof DetailBigPicActivity))) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        }
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 跳转到另一个 Activity
     *
     * @param aClass 要跳转页面的 Class
     */
    protected void startIntent(Class aClass) {
        startIntent(aClass, null);
    }

    /**
     * 跳转到另一个 Activity，并携带数据
     *
     * @param aClass 要跳转页面的 Class
     * @param bundle 携带的数据
     */
    protected void startIntent(Class aClass, Bundle bundle) {
        Intent intent = new Intent(this, aClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转到另一个 Activity，携带数据，并且需要接收到返回信息
     *
     * @param aClass      要跳转页面的 Class
     * @param bundle      携带的数据
     * @param requestCode 请求 code
     */
    protected void startForResultIntent(Class aClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, aClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 返回 携带的数据
     *
     * @return
     */
    protected Bundle getBundle() {
        return getIntent().getExtras();
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
    protected abstract void initView();

    /**
     * 初始化事件，和数据
     */
    protected abstract void initEventAndData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.unsubscribe();
        }
    }
}
