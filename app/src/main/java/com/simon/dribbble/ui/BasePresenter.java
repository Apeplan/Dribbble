package com.simon.dribbble.ui;

/**
 * Created by Simon Han on 2016/8/20.
 */

public interface BasePresenter {
    /**
     * 通常是在 activity 或者 fragment 的 onResume 中调用，进行订阅
     */
    void subscribe();

    /**
     * 通常是在 activity 或者fragment 的 onPause 中调用，进行退订
     */
    void unsubscribe();
}
