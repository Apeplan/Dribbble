package com.simon.dribbble.ui.user;

import com.simon.dribbble.ui.BasePresenter;
import com.simon.dribbble.ui.BaseView;

/**
 * Created by Simon Han on 2016/8/20.
 */

public interface SignInContract {

    interface View extends BaseView<Presenter> {
        /**
         * 登录成功
         */
        void signSuccess();

    }

    interface Presenter extends BasePresenter {
        /**
         * 返回用户 token
         *
         * @param token
         */
        void getUserToken(String token);

    }

}
