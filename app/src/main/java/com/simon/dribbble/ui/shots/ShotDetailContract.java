package com.simon.dribbble.ui.shots;

import com.simon.agiledevelop.MvpView;
import com.simon.dribbble.data.model.ShotEntity;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/31 17:45
 */

public interface ShotDetailContract {

    interface View extends MvpView<ShotDetailPresenter> {
        void showShot(ShotEntity shotsEntity);
    }
}
