package com.simon.dribbble.ui;

import com.simon.agiledevelop.mvpframe.MvpView;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/13 10:28
 */

public interface CommListContract {

    interface View extends MvpView<CommListPresenter> {
        void showList(List lists);

        void refreshComments(List lists);

        void moreComments(List lists);
    }

}
