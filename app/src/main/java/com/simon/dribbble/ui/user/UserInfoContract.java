package com.simon.dribbble.ui.user;

import com.simon.dribbble.data.model.UserEntity;
import com.simon.dribbble.ui.BasePresenter;
import com.simon.dribbble.ui.BaseView;

/**
 * Created by Simon Han on 2016/9/17.
 */

public interface UserInfoContract {

    interface View extends BaseView<Presenter> {
        void showUserInfo(UserEntity user);
    }


    interface Presenter extends BasePresenter {
        void loadUserInfo(long userId);
    }

}
