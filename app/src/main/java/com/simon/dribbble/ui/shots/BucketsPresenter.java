package com.simon.dribbble.ui.shots;

import com.simon.dribbble.data.Api;
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
 * Created on: 2016/9/14 17:30
 */

public class BucketsPresenter extends BasePresenterImpl implements BaseListContract.Presenter {

    private BaseListContract.View mView;

    public BucketsPresenter(BaseListContract.View view) {
        mView = view;
    }

    @Override
    public void loadList(long id, String type, int page, final int event) {
        Subscription subscription = mDataManger.getBuckets(type, id, page)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<BucketEntity>>() {
                    @Override
                    public void onCompleted() {
                        mView.onCompleted();
                        LLog.d("Simon", "onCompleted: 请求Buckets 请求成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFailed(0, e.getMessage());
                    }

                    @Override
                    public void onNext(List<BucketEntity> buckets) {
                        if (event == Api.EVENT_BEGIN) {
                            if (buckets.isEmpty()) {
                                mView.onEmpty();
                            } else {
                                mView.showList(buckets);
                            }
                        } else if (event == Api.EVENT_REFRESH) {
                            mView.refreshComments(buckets);
                        } else {
                            mView.moreComments(buckets);
                        }
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void subscribe() {

    }
}
