package com.simon.dribbble.ui.user;

import com.simon.agiledevelop.mvpframe.Presenter;
import com.simon.agiledevelop.mvpframe.MvpView;
import com.simon.dribbble.data.model.ShotEntity;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 18:10
 */

public interface UserShotsContract {

    interface View extends MvpView<Presenter> {
        void showShots(List<ShotEntity> shots);
    }

}
