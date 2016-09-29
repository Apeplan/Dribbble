package com.simon.dribbble.ui.shots;

import com.simon.dribbble.ui.BasePresenter;
import com.simon.dribbble.ui.BaseView;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/6 17:45
 */

public interface CreateContract {

    interface View extends BaseView<Presenter> {
        void onSuccess();
    }

    interface Presenter extends BasePresenter {
        void createShot();
    }

}
