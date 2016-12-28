package com.simon.dribbble.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simon.dribbble.data.model.ApiResponse;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * 网络请求服务
 * <p>
 * Created by Simon Han on 2016/8/20.
 */

public interface RemoteApi {

    String ENDPOINT = "http://www.tngou.net/api/";

    @GET("cook/list")
    Observable<ApiResponse> getCooks();

    /*******
     * Helper class that sets up a new services
     ******/
    class Creator {

        public static RemoteApi newDataService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RemoteApi.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(RemoteApi.class);
        }
    }
}
