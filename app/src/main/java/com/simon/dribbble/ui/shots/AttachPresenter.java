package com.simon.dribbble.ui.shots;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.AttachmentEntity;
import com.simon.dribbble.ui.BasePresenterImpl;
import com.simon.dribbble.ui.baselist.BaseListContract;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 16:20
 */

public class AttachPresenter extends BasePresenterImpl implements
        BaseListContract.Presenter {

    private BaseListContract.View mView;

    public AttachPresenter(BaseListContract.View view) {
        mView = view;
    }

    @Override
    public void loadList(long id, String type, int page, final int event) {
        Subscription subscription = mDataManger.getShotAttach(id, page)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<AttachmentEntity>>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: 获取评论");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.d("Simon", "onCompleted: 获取评论失败\t" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<AttachmentEntity> likes) {
                        LLog.d("Simon", "onCompleted: 获取评论成功");
                        if (null != likes) {

                            if (event == Api.EVENT_BEGIN) {
                                if (likes.isEmpty()) {
                                    mView.onEmpty();
                                } else {
                                    mView.showList(likes);
                                }
                            } else if (event == Api.EVENT_REFRESH) {
                                mView.refreshComments(likes);
                            } else {
                                mView.moreComments(likes);
                            }

                        } else {
                            mView.onFailed(0, "获取数据失败");
                        }
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void subscribe() {

    }
}
