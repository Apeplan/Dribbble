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
 * Created on: 2016/9/12 11:48
 */

public class SearchPresenter extends BasePresenterImpl implements SearchContract.Presenter {

    private SearchContract.View mSearchView;

    public SearchPresenter(SearchContract.View view) {
        mSearchView = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void searchShot(String key, int page, @DribbbleApi.SortOrder String sort) {
        Subscription subscription = mDataManger.search(key, page, sort)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<ShotEntity>>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon Han", "onCompleted: ");
                        mSearchView.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.d("Simon Han", "onError: " + e.getMessage());
                        mSearchView.onFailed(0, e.getMessage());
                    }

                    @Override
                    public void onNext(List<ShotEntity> shotEntities) {
                        LLog.d("Simon Han", "onNext: " + shotEntities.size());
                        if (shotEntities.isEmpty()) {
                            mSearchView.onEmpty();
                        } else {
                            mSearchView.showSearch(shotEntities);
                        }
                    }
                });
        addSubscription(subscription);
    }
}
