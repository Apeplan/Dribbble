package com.simon.agiledevelop.mvpframe;

/**
 * describe: All View must extends
 *
 * @author Simon Han
 * @date 2016.10.20
 * @email hanzx1024@gmail.com
 */
public interface MvpView<T> {
    /**
     * 显示加载进度框
     *
     * @param action 可以区分不同类型请求
     * @param msg    提示信息
     */
    void showLoading(int action, String msg);

    /**
     * 请求成功，但是没有数据
     *
     * @param msg 空页面的提示信息
     */
    void onEmpty(String msg);

    /**
     * 请求失败
     *
     * @param action 可以区分不同类型的失败
     * @param msg    失败信息
     */
    void onFailed(int action, String msg);


    /**
     * 请求执行完成，不管成功与否，都会调用这个方法
     *
     * @param action
     */
    void onCompleted(int action);

    /**
     * 将 View 中持有的 Presenter 赋值
     *
     * @param presenter
     */
    void setPresenter(T presenter);

}
