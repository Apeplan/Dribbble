package com.simon.dribbble.ui.baselist;

import com.simon.agiledevelop.MvpRxPresenter;
import com.simon.agiledevelop.MvpView;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/13 10:28
 */

public interface BaseListContract {

    interface View extends MvpView<MvpRxPresenter> {
        void showList(List lists);

        void refreshComments(List lists);

        void moreComments(List lists);
    }

}
