package com.simon.dribbble.ui;

/**
 * Created by Simon Han on 2016/8/20.
 */

public interface BaseView<T> {
    /**
     * 请求成功，但是没有数据
     */
    void onEmpty();

    /**
     * 请求失败
     *
     * @param action 可以区分不同类型的失败
     * @param msg    失败信息
     */
    void onFailed(int action, String msg);

    /**
     * 请求执行完成，不管成功与否，都会调用这个方法
     */
    void onCompleted();

//    void showLoading();


    /**
     * 将 View 中持有的 Presenter 赋值
     *
     * @param presenter
     */
    void setPresenter(T presenter);
}
