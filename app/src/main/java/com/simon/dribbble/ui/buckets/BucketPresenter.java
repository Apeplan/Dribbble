package com.simon.dribbble.ui.buckets;

import com.simon.dribbble.data.model.BucketEntity;
import com.simon.dribbble.ui.BasePresenterImpl;
import com.simon.dribbble.ui.baselist.BaseListContract;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/2 14:28
 */

public class BucketPresenter extends BasePresenterImpl implements BaseListContract.Presenter {

    private BaseListContract.View mBucketsView;

    public BucketPresenter(BaseListContract.View bucketsView) {
        mBucketsView = bucketsView;
        mBucketsView.setPresenter(this);
    }

    @Override
    public void loadList(long id, String type, int page, int event) {

        Subscription subscription = mDataManger.getUserBuckets()
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<BucketEntity>>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: 请求 Buckets 执行完成");
                        mBucketsView.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.d("Simon", "onCompleted: 请求 Buckets 失败\t" + e.getMessage());
                        mBucketsView.onFailed(0, "请求失败");
                    }

                    @Override
                    public void onNext(List<BucketEntity> bucketEntities) {
                        if (bucketEntities.isEmpty()) {
                            mBucketsView.onEmpty();
                        } else {
                            mBucketsView.showList(bucketEntities);
                        }
                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void subscribe() {

    }

}
