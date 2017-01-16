package com.simon.dribbble.ui.shots;

import com.simon.agiledevelop.MvpRxPresenter;
import com.simon.agiledevelop.MvpView;
import com.simon.dribbble.data.model.ShotEntity;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 11:24
 */

public interface SearchContract {

    interface View extends MvpView<MvpRxPresenter> {
        void showSearch(List<ShotEntity> shots);
    }
}
