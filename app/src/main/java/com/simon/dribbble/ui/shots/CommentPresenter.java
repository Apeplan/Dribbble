package com.simon.dribbble.ui.shots;

import com.simon.dribbble.data.model.CommentEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.ui.BasePresenterImpl;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/1 17:12
 */

public class CommentPresenter extends BasePresenterImpl implements CommentContract.Presenter {

    private CommentContract.View mCommentView;

    public CommentPresenter(CommentContract.View shotsView) {
        mCommentView = shotsView;
        mCommentView.setPresenter(this);
    }

    @Override
    public void loadComments(long shotId, String type, int page, final int event) {

        Subscription subscription = mDataManger.getComments(shotId, type, page)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<CommentEntity>>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: 获取评论");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.d("Simon", "onCompleted: 获取评论失败\t" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<CommentEntity> commentEntities) {
                        LLog.d("Simon", "onCompleted: 获取评论成功");
                        if (null != commentEntities) {

                            if (event == DribbbleApi.EVENT_BEGIN) {
                                if (commentEntities.isEmpty()) {
                                    mCommentView.onEmpty();
                                } else {
                                    mCommentView.showComments(commentEntities);
                                }
                            } else if (event == DribbbleApi.EVENT_REFRESH) {
                                mCommentView.refreshComments(commentEntities);
                            } else {
                                mCommentView.moreComments(commentEntities);
                            }

                        } else {
                            mCommentView.onFailed(0, "获取数据失败");
                        }
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void subscribe() {

    }
}
