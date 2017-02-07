package com.simon.agiledevelop.utils.schedulers;

import android.support.annotation.NonNull;

import rx.Scheduler;

/**
 * describe: Allow providing different types of {@link Scheduler}s
 *
 * @author Simon Han
 * @date 2016.10.25
 * @email hanzx1024@gmail.com
 */

public interface BaseSchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
