package com.simon.dribbble.ui.sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simon.dribbble.data.model.ApiResponse;
import com.simon.dribbble.data.model.Cook;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.util.schedulers.BaseSchedulerProvider;
import com.simon.dribbble.util.schedulers.SchedulerProvider;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/19 9:52
 */

public class SamplePresenter implements SampleContract.Presenter {

    private final SampleContract.View mSampleView;
    private boolean mFirstLoad = true;
    private final DribbbleApi mDribbbleApi;
    protected final BaseSchedulerProvider mSchedulerProvider;
    protected CompositeSubscription mSubscriptions;

    public SamplePresenter(SampleContract.View sampleView) {
        mSampleView = sampleView;
        mDribbbleApi = newDataService();
        mSchedulerProvider = SchedulerProvider.getInstance();
        mSubscriptions = new CompositeSubscription();
        mSampleView.setPresenter(this);

    }

    @Override
    public void loadData(boolean forceUpdate) {
        loadData(forceUpdate || mFirstLoad, true);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void subscribe() {
        loadData(false);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.unsubscribe();
        mSubscriptions.clear();
    }

    private void loadData(final boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mSampleView.setLoading(true);
        }

        Subscription subscription = mDribbbleApi.getCooks()
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<ApiResponse>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: 执行完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSampleView.onFailed(0, e.getMessage());
                    }

                    @Override
                    public void onNext(ApiResponse apiResponse) {
                        processTasks(apiResponse.getTngou());
                    }
                });
        mSubscriptions.add(subscription);
    }

    private void processTasks(List<Cook> tasks) {
        mSampleView.setLoading(false);
        if (tasks.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            mSampleView.onEmpty();
        } else {
            // Show the list of tasks
            mSampleView.showCooks(tasks);
        }
    }


    public static DribbbleApi newDataService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tngou.net/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(DribbbleApi.class);
    }

}
