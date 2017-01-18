package com.simon.dribbble.ui.user;

import com.simon.agiledevelop.mvpframe.RxPresenter;
import com.simon.agiledevelop.mvpframe.MvpView;
import com.simon.dribbble.data.model.User;

/**
 * Created by Simon Han on 2016/9/17.
 */

public interface UserInfoContract {

    interface View extends MvpView<RxPresenter> {
        void showUserInfo(User user);
    }

}
