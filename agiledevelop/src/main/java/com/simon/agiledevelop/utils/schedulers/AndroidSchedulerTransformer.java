package com.simon.agiledevelop.utils.schedulers;

import rx.Observable;

/**
 * describe:
 *
 * @author Apeplan
 * @date 2016/12/27
 * @email hanzx1024@gmail.com
 */

public class AndroidSchedulerTransformer<T> implements Observable.Transformer<T, T> {
    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable.subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(SchedulerProvider.getInstance().ui());
    }
}
