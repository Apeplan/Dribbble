package com.simon.dribbble.ui.user;

import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.ui.BasePresenterImpl;
import com.simon.dribbble.ui.baselist.BaseListContract;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 18:12
 */

public class UserShotsPresenter extends BasePresenterImpl implements BaseListContract.Presenter {

    private BaseListContract.View mView;

    public UserShotsPresenter(BaseListContract.View view) {
        mView = view;
    }

    @Override
    public void loadList(long id, String type, int page, int event) {

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
                            mView.showList(shotEntities);
                        }
                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void subscribe() {

    }

}
