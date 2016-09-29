package com.simon.dribbble.ui.user;

import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.util.schedulers.BaseSchedulerProvider;
import com.simon.dribbble.util.schedulers.SchedulerProvider;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 18:12
 */

public class UserShotsPresenter implements UserShotsContract.Presenter {

    private UserShotsContract.View mView;
    private DribbbleDataManger mDataManger;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeSubscription mSubscription;

    public UserShotsPresenter(UserShotsContract.View view) {
        mView = view;
        mDataManger = DribbbleDataManger.getInstance(DribbbleApi.Creator.dribbbleApi());
        mSchedulerProvider = SchedulerProvider.getInstance();
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void loadShots(int page) {
        mSubscription.clear();

        Subscription subscription = mDataManger.getUserShots(page)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<ShotEntity>>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: 用户 Shots 请求完成");
                        mView.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFailed(0, e.getMessage());
                    }

                    @Override
                    public void onNext(List<ShotEntity> shotEntities) {
                        if (shotEntities.isEmpty()) {
                            mView.onEmpty();
                        } else {
                            mView.showShots(shotEntities);
                        }
                    }
                });

        mSubscription.add(subscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscription.clear();
    }
}
