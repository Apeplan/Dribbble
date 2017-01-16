package com.simon.dribbble.ui.user;

import com.simon.agiledevelop.MvpRxPresenter;
import com.simon.agiledevelop.MvpView;

/**
 * Created by Simon Han on 2016/8/20.
 */

public interface SignInContract {

    interface View extends MvpView<MvpRxPresenter> {
        /**
         * 登录成功
         */
        void signSuccess();

    }
}
