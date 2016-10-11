package com.simon.dribbble.ui.shots;

import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.ui.BasePresenterImpl;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/29 16:54
 */

public class ShotsPresenter extends BasePresenterImpl implements ShotsContract.Presenter {

    private ShotsContract.View mShotsView;

    public ShotsPresenter(ShotsContract.View shotsView) {
        mShotsView = shotsView;
        mShotsView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void loadShotsList(int page, @DribbbleApi.ShotType String list, @DribbbleApi
            .ShotTimeframe String timeframe, @DribbbleApi.ShotSort String sort, final int event) {
        Subscription subscription = mDataManger.getShotsList(page, list, timeframe, sort)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<ShotEntity>>() {
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
                    public void onNext(List<ShotEntity> shotsEntities) {
                        if (event == DribbbleApi.EVENT_REFRESH) {
                            mShotsView.renderRefrshShotsList(shotsEntities);
                        } else if (event == DribbbleApi.EVENT_MORE) {
                            mShotsView.renderMoreShotsList(shotsEntities);
                        } else {
                            mShotsView.renderShotsList(shotsEntities);
                        }
                        LLog.d("Simon", "onNext: Shots List 请求成功" + shotsEntities.size());
                    }
                });
        addSubscription(subscription);
    }

   /* @Override
    public void loadShotsList(int page, String list, String timeframe, String sort, final int
            event) {

        Subscription subscription = mDataManger.getShotsList(page, list, timeframe, sort)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<ShotEntity>>() {
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
                    public void onNext(List<ShotEntity> shotsEntities) {
                        if (event == Api.EVENT_REFRESH) {
                            mShotsView.renderRefrshShotsList(shotsEntities);
                        } else if (event == Api.EVENT_MORE) {
                            mShotsView.renderMoreShotsList(shotsEntities);
                        } else {
                            mShotsView.renderShotsList(shotsEntities);
                        }
                        LLog.d("Simon", "onNext: Shots List 请求成功" + shotsEntities.size());
                    }
                });
        addSubscription(subscription);
    }*/
}
