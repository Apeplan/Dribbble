package com.simon.dribbble.data;

import com.simon.dribbble.data.model.ApiResponse;
import com.simon.dribbble.data.remote.RemoteApi;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Simon Han on 2016/8/20.
 */

public class DataManger {
    private static DataManger INSTANCE;
    private final RemoteApi mDataService;

    public static DataManger getInstance(RemoteApi dataService) {
        if (INSTANCE == null) {
            INSTANCE = new DataManger(dataService);
        }
        return INSTANCE;
    }

    public DataManger(RemoteApi dataService) {
        mDataService = dataService;
    }

    /**
     * 返回菜谱数据
     *
     * @return
     */
    public Observable<ApiResponse> getCooks() {
        return mDataService.getCooks().concatMap(new Func1<ApiResponse, Observable<ApiResponse>>() {
            @Override
            public Observable<ApiResponse> call(final ApiResponse apiResponse) {
                return Observable.create(new Observable.OnSubscribe<ApiResponse>() {
                    @Override
                    public void call(Subscriber<? super ApiResponse> subscriber) {
                        if (subscriber.isUnsubscribed()) return;

                        try {
                            if (null == apiResponse) {
                                subscriber.onError(new NullPointerException("Request failed"));
                            } else {
                                subscriber.onNext(apiResponse);
                                subscriber.onCompleted();
                            }
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }

                    }
                });
            }
        });
    }

}
