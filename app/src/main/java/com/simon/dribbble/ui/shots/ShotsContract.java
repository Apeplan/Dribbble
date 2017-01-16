package com.simon.dribbble.ui.shots;

import com.simon.agiledevelop.MvpView;
import com.simon.dribbble.data.model.ShotEntity;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/29 16:17
 */

public interface ShotsContract {

    interface View extends MvpView<ShotsPresenter> {

        void renderShotsList(List<ShotEntity> shotsList);

        void renderMoreShotsList(List<ShotEntity> shotsList);

        void renderRefrshShotsList(List<ShotEntity> shotsList);

    }

}
