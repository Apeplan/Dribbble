package com.simon.dribbble.ui.shots;

import com.simon.agiledevelop.MvpRxPresenter;
import com.simon.agiledevelop.MvpView;
import com.simon.dribbble.data.model.CommentEntity;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/1 16:53
 */

public interface CommentContract {

    interface View extends MvpView<MvpRxPresenter> {
        void showComments(List<CommentEntity> comments);

        void refreshComments(List<CommentEntity> comments);

        void moreComments(List<CommentEntity> comments);
    }
}
