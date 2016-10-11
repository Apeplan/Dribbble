package com.simon.dribbble.ui.shots;

import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.ui.BasePresenter;
import com.simon.dribbble.ui.BaseView;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/29 16:17
 */

public interface ShotsContract {

    interface View extends BaseView<Presenter> {

        void renderShotsList(List<ShotEntity> shotsList);

        void renderMoreShotsList(List<ShotEntity> shotsList);

        void renderRefrshShotsList(List<ShotEntity> shotsList);

    }

    interface Presenter extends BasePresenter {
        void loadShotsList(int page, @DribbbleApi.ShotType String list, @DribbbleApi
                .ShotTimeframe String timeframe, @DribbbleApi.ShotSort String sort, int event);
    }

}
