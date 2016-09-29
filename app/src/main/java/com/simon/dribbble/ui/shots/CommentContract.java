package com.simon.dribbble.ui.shots;

import com.simon.dribbble.data.model.CommentEntity;
import com.simon.dribbble.ui.BasePresenter;
import com.simon.dribbble.ui.BaseView;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/1 16:53
 */

public interface CommentContract {

    interface View extends BaseView<Presenter> {
        void showComments(List<CommentEntity> comments);

        void refreshComments(List<CommentEntity> comments);

        void moreComments(List<CommentEntity> comments);
    }

    interface Presenter extends BasePresenter {
        void loadComments(long shotId, String type, int page, int event);
    }
}
