package com.simon.dribbble.ui.baselist;

import com.simon.agiledevelop.mvpframe.RxPresenter;
import com.simon.agiledevelop.mvpframe.MvpView;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/13 10:28
 */

public interface BaseListContract {

    interface View extends MvpView<RxPresenter> {
        void showList(List lists);

        void refreshComments(List lists);

        void moreComments(List lists);
    }

}
