package com.simon.dribbble.ui.shots;

import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.ui.BasePresenter;
import com.simon.dribbble.ui.BaseView;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 11:24
 */

public interface SearchContract {

    interface View extends BaseView<Presenter> {
        void showSearch(List<ShotEntity> shots);
    }

    interface Presenter extends BasePresenter {
        void searchShot(String key, int page, @DribbbleApi.SortOrder String sort);
    }
}
