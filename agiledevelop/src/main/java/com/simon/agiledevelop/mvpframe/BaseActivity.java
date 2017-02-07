package com.simon.agiledevelop.mvpframe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.simon.agiledevelop.R;
import com.simon.agiledevelop.state.StateView;
import com.simon.agiledevelop.utils.PreferencesHelper;


/**
 * describe: Base Activity, With no special requirements, all activity must extends
 *
 * @param <P> {@link Presenter} subclass
 * @author Simon Han
 * @date 2016.10.20
 * @email hanzx1024@gmail.com
 */
public abstract class BaseActivity<P extends Presenter> extends CoreActivity<P>
        implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener {

    private StateView mStateView;
    protected PreferencesHelper mSputil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStateView = getLoadingView();
        mSputil = PreferencesHelper.getInstance();
    }

    /**
     * 返回加载状态View
     *
     * @return
     */
    protected abstract StateView getLoadingView();

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFragmentInteraction(Bundle action) {

    }

    @Override
    protected boolean isStatusBarTranslucent() {
        return true;
    }

    /**
     * 显示正在加载页面
     *
     * @param message
     */
    public void showLoading(String message) {
        if (null == mStateView) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        if (!TextUtils.isEmpty(message)) {
            mStateView.setTitle(message);
        }
        mStateView.setState(StateView.STATE_LOADING);
    }

    /**
     * 显示内容页面
     */
    public void showContent() {
        if (null == mStateView) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        mStateView.setState(StateView.STATE_CONTENT);
    }

    /**
     * 显示空数据页面
     *
     * @param message         提示信息
     * @param onClickListener 是否支持重新加载
     */
    public void showEmtry(String message, View.OnClickListener onClickListener) {
        if (null == mStateView) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        mStateView.setState(StateView.STATE_EMPTY);
        if (!TextUtils.isEmpty(message)) {
            mStateView.setTitle(message);
        }
        if (null != onClickListener) {
            mStateView.setOnClickListener(onClickListener);
        }
    }

    /**
     * 显示错误页面
     *
     * @param error           提示信息
     * @param onClickListener 是否支持重新加载
     */
    public void showError(String error, View.OnClickListener onClickListener) {
        if (null == mStateView) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        mStateView.setState(StateView.STATE_ERROR);
        if (!TextUtils.isEmpty(error)) {
            mStateView.setTitle(error);
        }
        if (null != onClickListener) {
            mStateView.setOnClickListener(onClickListener);
        }
    }

    /**
     * 显示网络错误页面
     *
     * @param error
     * @param onClickListener 是否支持重新加载
     */
    public void showNetworkError(String error, View.OnClickListener onClickListener) {
        if (null == mStateView) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        mStateView.setState(StateView.STATE_ERROR);
        if (!TextUtils.isEmpty(error)) {
            mStateView.setTitle(error);
        }
        mStateView.setIcon(R.drawable.state_net_error);
        if (null != onClickListener) {
            mStateView.setOnClickListener(onClickListener);
        }
    }

}
