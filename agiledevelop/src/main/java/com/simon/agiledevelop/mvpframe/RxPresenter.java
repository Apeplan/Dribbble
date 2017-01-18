package com.simon.agiledevelop.mvpframe;

import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.utils.schedulers.AndroidSchedulerTransformer;
import com.simon.agiledevelop.utils.schedulers.SchedulerProvider;

import rx.Observable;
import rx.functions.Action0;

/**
 * describe:
 *
 * @author Apeplan
 * @date 2016/12/27
 * @email hanzx1024@gmail.com
 */

public abstract class RxPresenter<V extends MvpView, M> extends BasePresenter<V> {

    protected ResultSubscriber<M> subscriber;

    /**
     * Unsubscribes the subscriber and set it to null
     */
    protected void unSubscribe() {
        if (subscriber != null && !subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
        subscriber = null;
    }

    /**
     * Subscribes the presenter himself as subscriber on the observable
     *
     * @param observable The observable to subscribe
     */
    public void subscribe(Observable<M> observable, ResultSubscriber<M> resultSubscriber) {

        unSubscribe();

        subscriber = resultSubscriber;

        observable = applyScheduler(observable);

        observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {
                subscriber.onStartRequest();
            }
        })
                .unsubscribeOn(SchedulerProvider.getInstance().io())
                .subscribeOn(SchedulerProvider.getInstance().ui())
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribe(subscriber);
    }

    /**
     * Called in {@link #subscribe(Observable, ResultSubscriber)} to set  <code>subscribeOn()
     * </code> and
     * <code>observeOn()</code>. As default it uses {@link AndroidSchedulerTransformer}. Override
     * this
     * method if you want to provide your own scheduling implementation.
     *
     * @param observable The observable
     * @return the observable with the applied scheduler
     */
    protected Observable<M> applyScheduler(Observable<M> observable) {
        return observable.compose(new AndroidSchedulerTransformer<M>());
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            unSubscribe();
        }
    }
}
