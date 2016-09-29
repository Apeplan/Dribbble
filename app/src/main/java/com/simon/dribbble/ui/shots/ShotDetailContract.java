package com.simon.dribbble.ui.shots;

import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.ui.BasePresenter;
import com.simon.dribbble.ui.BaseView;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/31 17:45
 */

public interface ShotDetailContract {

    interface View extends BaseView<Presenter> {
        void showShot(ShotEntity shotsEntity);

        void hintMsg(String msg);

        void showLoadDia();
    }

    interface Presenter extends BasePresenter {
        void loadShot(long id);
    }
}
