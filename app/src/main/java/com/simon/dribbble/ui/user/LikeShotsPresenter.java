package com.simon.dribbble.ui.user;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.UserLikeEntity;
import com.simon.dribbble.ui.BasePresenterImpl;
import com.simon.dribbble.ui.baselist.BaseListContract;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/29 16:54
 */

public class LikeShotsPresenter extends BasePresenterImpl implements BaseListContract.Presenter {

    private BaseListContract.View mShotsView;

    public LikeShotsPresenter(BaseListContract.View shotsView) {
        mShotsView = shotsView;
        mShotsView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void loadList(long id, String type, int page, final int event) {

        Subscription subscription = mDataManger.getUserLikes(page)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<UserLikeEntity>>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: Shots List 请求完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.d("Simon", "onCompleted: Shots List 请求失败" + e.getMessage());
                        mShotsView.onFailed(event, e.getMessage());
                    }

                    @Override
                    public void onNext(List<UserLikeEntity> shotsEntities) {
                        if (event == Api.EVENT_REFRESH) {
                            mShotsView.refreshComments(shotsEntities);
                        } else if (event == Api.EVENT_MORE) {
                            mShotsView.moreComments(shotsEntities);
                        } else {
                            mShotsView.showList(shotsEntities);
                        }
                        LLog.d("Simon", "onNext: Shots List 请求成功" + shotsEntities.size());
                    }
                });
        addSubscription(subscription);
    }

}
