package com.simon.dribbble.ui;

import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.util.schedulers.BaseSchedulerProvider;
import com.simon.dribbble.util.schedulers.SchedulerProvider;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 9:52
 */

public abstract class BasePresenterImpl implements BasePresenter {

    protected DribbbleDataManger mDataManger;
    protected final BaseSchedulerProvider mSchedulerProvider;
    private CompositeSubscription mSubscriptions;

    public BasePresenterImpl() {
        mDataManger = DribbbleDataManger.getInstance(DribbbleApi.Creator.dribbbleApi());
        mSchedulerProvider = SchedulerProvider.getInstance();
        mSubscriptions = new CompositeSubscription();
    }

    protected void addSubscription(Subscription s) {
        if (this.mSubscriptions == null) {
            this.mSubscriptions = new CompositeSubscription();
        }
        this.mSubscriptions.add(s);
    }

    /*protected void removeSubscription(Subscription s) {
        if (this.mSubscriptions != null) {
            this.mSubscriptions.remove(s);
        }
    }

    protected void clear() {
        if (this.mSubscriptions != null) {
            this.mSubscriptions.clear();
        }
    }*/


    @Override
    public void unsubscribe() {
        if (this.mSubscriptions != null) {
            this.mSubscriptions.unsubscribe();
        }
    }
}
