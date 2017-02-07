package com.simon.agiledevelop.mvpframe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simon.agiledevelop.R;
import com.simon.agiledevelop.state.StateView;

/**
 * describe: Base Fragment, With no special requirements, all fragment must extends
 *
 * @param <T> {@link Presenter} subclass
 * @author Simon Han
 * @date 2016.10.20
 * @email hanzx1024@gmail.com
 */
public abstract class BaseFragment<T extends Presenter> extends Fragment implements View
        .OnClickListener {
    protected T mPresenter;
    protected OnFragmentInteractionListener mListener;

    protected View rootView;
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    private StateView mStateView;

   /* @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        this.mContext = activity;
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        if (rootView == null && getLayoutId() != 0) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        initView(inflater, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = getPresenter();
        mStateView = getLoadingView(view);

        isViewPrepared = true;
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    protected abstract T getPresenter();

    /**
     * 初始化控件
     */
    protected abstract void initView(LayoutInflater inflater, View view);

    /**
     * 返回加载布局要覆盖和替换的View
     *
     * @return
     */
    protected abstract StateView getLoadingView(View view);

    /**
     * 初始化事件，和数据
     */
    protected abstract void initEventAndData();

    @Override
    public void onClick(View v) {

    }

    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }

    }

    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected void lazyFetchData() {
        Log.v("BaseFragment", getClass().getName() + "------>lazyFetchData");
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
        Intent intent = new Intent(getActivity(), aClass);
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
        Intent intent = new Intent(getActivity(), aClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v("BaseFragment", getClass().getName() + "------>isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("BaseFragment", "------>onDestroyView");
        // view被销毁后，将可以重新触发数据懒加载，因为在viewpager下，fragment不会再次新建并走onCreate的生命周期流程，将从onCreateView开始
        hasFetchData = false;
        isViewPrepared = false;
        mPresenter = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView(false);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Bundle action);
    }
}
