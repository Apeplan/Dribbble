package com.simon.agiledevelop.utils;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * A simple event bus built with RxJava
 */
public class RxEventBus {

    public static final RxEventBus INSTANCE;
    private final PublishSubject<Object> mBusSubject;

    static {
        INSTANCE = new RxEventBus();
    }

    public RxEventBus() {
        mBusSubject = PublishSubject.create();
    }

    /**
     * Posts an object (usually an Event) to the bus
     */
    public void post(Object event) {
        mBusSubject.onNext(event);
    }

    /**
     * Observable that will emmit everything posted to the event bus.
     */
    public Observable<Object> observable() {
        return mBusSubject;
    }

    /**
     * Observable that only emits events of a specific class.
     * Use this if you only want to subscribe to one type of events.
     */
    public <T> Observable<T> filteredObservable(final Class<T> eventClass) {
        return mBusSubject.ofType(eventClass);
    }
}