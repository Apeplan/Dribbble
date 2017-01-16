package com.simon.agiledevelop;


import rx.Subscriber;

public abstract class ResultSubscriber<T> extends Subscriber<T> {
    /**
     * 自定义订阅，参数用来区分网络接口，以用来在不同接口操作过程中，处理不同的逻辑
     */
    public ResultSubscriber() {

    }

    @Override
    public void onCompleted() {
        onEndRequest();
    }

    @Override
    public void onError(Throwable e) {
        try {
            if (e != null) {
                onFailed(e);
                e.printStackTrace();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    @Override
    public void onNext(T t) {
        onResult(t);
    }

    /**
     * 网络请求开始
     */
    public abstract void onStartRequest();

    /**
     * 网络请求结束
     */
    public abstract void onEndRequest();

    /**
     * 网络请求错误
     */
    public abstract void onFailed(Throwable e);

    /**
     * 处理请求结果
     */
    public abstract void onResult(T t);
}
