package com.simon.dribbble.ui.user;

import com.simon.agiledevelop.MvpRxPresenter;
import com.simon.agiledevelop.MvpView;
import com.simon.dribbble.data.model.User;

/**
 * Created by Simon Han on 2016/9/17.
 */

public interface UserInfoContract {

    interface View extends MvpView<MvpRxPresenter> {
        void showUserInfo(User user);
    }

}
