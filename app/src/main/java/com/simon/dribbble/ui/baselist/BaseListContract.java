package com.simon.dribbble.ui.baselist;

import com.simon.dribbble.ui.BasePresenter;
import com.simon.dribbble.ui.BaseView;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/13 10:28
 */

public interface BaseListContract {

    interface View extends BaseView<Presenter> {
        void showList(List lists);

        void refreshComments(List lists);

        void moreComments(List lists);
    }

    interface Presenter extends BasePresenter {
        void loadList(long id, String type, int page, int event);
    }
}
