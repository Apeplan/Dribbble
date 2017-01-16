package com.simon.agiledevelop;


import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceHelper {

    /**
     * 这一部分配置常量，可以抽取出常量类
     */
    private static final long DEFAULT_TIMEOUT = 10000;//默认超时时间(毫秒)

    private Retrofit mRetrofit;

    private Interceptor mInterceptor;

    private ServiceHelper() {
    }

    /**
     * 单例控制器
     */
    private static class SingletonHolder {
        private static final ServiceHelper INSTANCE = new ServiceHelper();
    }

    /**
     * 获取单例对象
     *
     * @return
     */
    public static ServiceHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取拦截器
     */
    public Interceptor getInterceptor() {
        return mInterceptor;
    }

    /**
     * 设置拦截器
     */
    public ServiceHelper setInterceptor(Interceptor mInterceptor) {
        this.mInterceptor = mInterceptor;
        return this;
    }

    /**
     * 初始化
     */
    public <I> I creator(String baseUrl, Class<I> clazz) {
        return creator(baseUrl, clazz, DEFAULT_TIMEOUT);
    }

    /**
     * 初始化
     */
    public <I> I creator(String baseUrl, Class<I> clazz, long timeOut) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (mInterceptor != null) {
            okHttpClient.addNetworkInterceptor(mInterceptor);
        } else {
            okHttpClient.addNetworkInterceptor(new HttpInterceptor());
        }
        okHttpClient.connectTimeout(timeOut, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(timeOut, TimeUnit.MILLISECONDS);//等待服务器响应的时间
        okHttpClient.writeTimeout(timeOut, TimeUnit.MILLISECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        I mNetService = mRetrofit.create(clazz);
        return mNetService;
    }

    /**
     * @param baseUrl host地址
     * @param clazz   接口类
     * @param headers 封装的请求头(okHttp3)
     * @param <I>
     * @return 返回接口实体类
     */
    public <I> I creator(String baseUrl, Class<I> clazz, Headers headers) {
        return creator(baseUrl, clazz, DEFAULT_TIMEOUT, headers);
    }

    /**
     * @param baseUrl host地址
     * @param clazz   接口类
     * @param timeOut 超时时间
     * @param headers 封装的请求头(okHttp3)
     * @param <I>
     * @return 返回接口实体类
     */
    public <I> I creator(String baseUrl, Class<I> clazz, long timeOut, Headers headers) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (mInterceptor != null) {
            okHttpClient.addNetworkInterceptor(mInterceptor);
        } else {
            HttpInterceptor interceptor = new HttpInterceptor();
            interceptor.setHeaders(headers);
            okHttpClient.addNetworkInterceptor(interceptor);
        }
        okHttpClient.connectTimeout(timeOut, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(timeOut, TimeUnit.MILLISECONDS);//等待服务器响应的时间
        okHttpClient.writeTimeout(timeOut, TimeUnit.MILLISECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        I mNetService = mRetrofit.create(clazz);
        return mNetService;
    }

    /**
     * @param baseUrl host地址
     * @param clazz   接口类
     * @param headers 封装的请求头(okHttp3)
     * @param <I>
     * @return 返回接口实体类
     */
    public <I> I creator(String baseUrl, Class<I> clazz, Map<String, String> headers) {
        return creator(baseUrl, clazz, DEFAULT_TIMEOUT, headers);
    }

    /**
     * @param baseUrl host地址
     * @param clazz   接口类
     * @param timeOut 超时时间
     * @param headers 封装的请求头(okHttp3)
     * @param <I>
     * @return 返回接口实体类
     */
    public <I> I creator(String baseUrl, Class<I> clazz, long timeOut, Map<String, String>
            headers) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (mInterceptor != null) {
            okHttpClient.addNetworkInterceptor(mInterceptor);
        } else {
            HttpInterceptor interceptor = new HttpInterceptor();
            interceptor.setMapHeaders(headers);
            okHttpClient.addNetworkInterceptor(interceptor);
        }
        okHttpClient.connectTimeout(timeOut, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(timeOut, TimeUnit.MILLISECONDS);//等待服务器响应的时间
        okHttpClient.writeTimeout(timeOut, TimeUnit.MILLISECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        I mNetService = mRetrofit.create(clazz);
        return mNetService;
    }

}
