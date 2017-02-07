package com.simon.agiledevelop.utils.schedulers;

import android.support.annotation.NonNull;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * describe: Provides different types of schedulers
 *
 * @author Simon Han
 * @date 2016.10.20
 * @email hanzx1024@gmail.com
 */

public class SchedulerProvider implements BaseSchedulerProvider {

    private static SchedulerProvider INSTANCE;

    /**
     * Prevent direct instantiation
     */
    private SchedulerProvider() {

    }

    public static SchedulerProvider getInstance() {
        if (INSTANCE == null) {
            synchronized (SchedulerProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SchedulerProvider();
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
