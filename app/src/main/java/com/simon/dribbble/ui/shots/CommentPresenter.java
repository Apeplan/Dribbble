package com.simon.dribbble.ui.shots;

import com.simon.agiledevelop.MvpRxPresenter;
import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.CommentEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/1 17:12
 */

public class CommentPresenter extends MvpRxPresenter<CommentContract.View, List<CommentEntity>> {

    public CommentPresenter(CommentContract.View shotsView) {
        attachView(shotsView);
        shotsView.setPresenter(this);
    }

    public void loadComments(long shotId, String type, int page, final int event) {

        Observable<List<CommentEntity>> comments = DribbbleDataManger.getInstance().getComments
                (shotId, type, page);

        subscribe(comments, new ResultSubscriber<List<CommentEntity>>() {
            @Override
            public void onStartRequest() {
                getView().showLoading(event, "");
            }

            @Override
            public void onEndRequest() {
                LLog.d("onCompleted: 获取评论");
                getView().onCompleted(event);
            }

            @Override
            public void onFailed(Throwable e) {
                LLog.d("onCompleted: 获取评论失败\t" + e.getMessage());
                getView().onFailed(event, e.getMessage());
            }

            @Override
            public void onResult(List<CommentEntity> commentEntities) {
                LLog.d("onCompleted: 获取评论成功");
                if (null != commentEntities) {

                    if (event == Api.EVENT_BEGIN) {
                        if (commentEntities.isEmpty()) {
                            getView().onEmpty("");
                        } else {
                            getView().showComments(commentEntities);
                        }
                    } else if (event == Api.EVENT_REFRESH) {
                        getView().refreshComments(commentEntities);
                    } else {
                        getView().moreComments(commentEntities);
                    }

                } else {
                    getView().onFailed(0, "获取数据失败");
                }
            }
        });

    }
}
