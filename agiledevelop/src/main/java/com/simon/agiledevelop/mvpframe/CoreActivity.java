package com.simon.agiledevelop.mvpframe;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.simon.agiledevelop.R;


/**
 * describe: Base Activity, With no special requirements, all activity must extends
 *
 * @param <P> {@link Presenter} subclass
 * @author Simon Han
 * @date 2016.10.20
 * @email hanzx1024@gmail.com
 */
public abstract class CoreActivity<P extends Presenter> extends AppCompatActivity {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (isStatusBarTranslucent()) {
            setStatusBarTranslucent();
        }
        mPresenter = getPresenter();
        initView(savedInstanceState);
        initEventAndData();
    }


    /**
     * 返回页面的布局资源ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 返回 Presenter
     *
     * @return
     */
    protected abstract P getPresenter();

    /**
     * 返回加载布局要覆盖和替换的View
     *
     * @return
     */
    protected abstract View getLoadingView();

    /**
     * 初始化控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化事件，和数据
     */
    protected abstract void initEventAndData();

    /**
     * 状态栏是否设置为透明
     */
    protected boolean isStatusBarTranslucent() {
        return false;
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
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView(false);
            mPresenter = null;
        }
    }

}
