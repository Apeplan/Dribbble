package com.simon.dribbble.ui.user;

import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.ui.BasePresenter;
import com.simon.dribbble.ui.BaseView;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 18:10
 */

public interface UserShotsContract {

    interface View extends BaseView<Presenter> {
        void showShots(List<ShotEntity> shots);
    }


    interface Presenter extends BasePresenter {
        void loadShots(int page);
    }
}
