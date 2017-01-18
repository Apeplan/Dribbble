package com.simon.dribbble.ui.shots;

import com.simon.agiledevelop.mvpframe.RxPresenter;
import com.simon.agiledevelop.mvpframe.MvpView;
import com.simon.dribbble.data.model.ShotEntity;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 11:24
 */

public interface SearchContract {

    interface View extends MvpView<RxPresenter> {
        void showSearch(List<ShotEntity> shots);
    }
}
