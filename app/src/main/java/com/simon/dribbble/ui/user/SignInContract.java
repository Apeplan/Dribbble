package com.simon.dribbble.ui.user;

import com.simon.agiledevelop.mvpframe.RxPresenter;
import com.simon.agiledevelop.mvpframe.MvpView;

/**
 * Created by Simon Han on 2016/8/20.
 */

public interface SignInContract {

    interface View extends MvpView<RxPresenter> {
        /**
         * 登录成功
         */
        void signSuccess();

    }
}
