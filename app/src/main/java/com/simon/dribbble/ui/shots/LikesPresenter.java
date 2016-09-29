package com.simon.dribbble.ui.shots;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.LikeEntity;
import com.simon.dribbble.ui.BasePresenterImpl;
import com.simon.dribbble.ui.baselist.BaseListContract;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/1 17:12
 */

public class LikesPresenter extends BasePresenterImpl implements BaseListContract.Presenter {

    private BaseListContract.View mCommentView;

    public LikesPresenter(BaseListContract.View shotsView) {
        mCommentView = shotsView;
        mCommentView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void loadList(long id, String type, int page, final int event) {
        Subscription subscription = mDataManger.getShotLikes(id, page)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<LikeEntity>>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: 获取评论");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.d("Simon", "onCompleted: 获取评论失败\t" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<LikeEntity> likes) {
                        LLog.d("Simon", "onCompleted: 获取评论成功");

                        if (event == Api.EVENT_BEGIN) {
                            if (likes.isEmpty()) {
                                mCommentView.onEmpty();
                            } else {
                                mCommentView.showList(likes);
                            }
                        } else if (event == Api.EVENT_REFRESH) {
                            mCommentView.refreshComments(likes);
                        } else {
                            mCommentView.moreComments(likes);
                        }

                    }
                });
        addSubscription(subscription);
    }
}
