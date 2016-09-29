package com.simon.dribbble.ui.shots;

import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.ui.user.SignInContract;
import com.simon.dribbble.util.schedulers.BaseSchedulerProvider;
import com.simon.dribbble.util.schedulers.SchedulerProvider;

import net.quickrecyclerview.utils.log.LLog;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 11:48
 */

public class SearchPresenter implements SearchContract.Presenter {

    private SignInContract.View mSignView;
    private DribbbleDataManger mDataManger;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private CompositeSubscription mSubscription;


    public SearchPresenter(SignInContract.View view) {
        mSignView = view;
        mDataManger = DribbbleDataManger.getInstance(DribbbleApi.Creator.dribbbleApi());
        mBaseSchedulerProvider = SchedulerProvider.getInstance();
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }


    public void load() {
        Subscription subscription = mDataManger.search("app")
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribeOn(mBaseSchedulerProvider.io())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.d("Simon", "onError: ");
                    }

                    @Override
                    public void onNext(Object o) {
                        LLog.d("Simon", "onNext: ");
                    }
                });

        mSubscription.add(subscription);
    }

    @Override
    public void unsubscribe() {
        mSubscription.clear();
    }
}
