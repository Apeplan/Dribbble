package com.simon.dribbble.ui.buckets;

import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.BucketEntity;
import com.simon.dribbble.ui.CommListContract;
import com.simon.dribbble.ui.CommListPresenter;

import java.util.List;

import rx.Observable;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/2 14:28
 */

public class BucketPresenter extends CommListPresenter<CommListContract.View, List<BucketEntity>> {

    public BucketPresenter(CommListContract.View bucketsView) {
        attachView(bucketsView);
        bucketsView.setPresenter(this);
    }

    @Override
    public void loadList(final int action, long id, String type, int page) {

        Observable<List<BucketEntity>> userBuckets = DribbbleDataManger.getInstance()
                .getUserBuckets();
        subscribe(userBuckets, new ResultSubscriber<List<BucketEntity>>() {
            @Override
            public void onStartRequest() {
                getView().showLoading(action, "");
            }

            @Override
            public void onEndRequest() {
                LLog.d("onCompleted: 请求 Buckets 执行完成");
                getView().onCompleted(action);
            }

            @Override
            public void onFailed(Throwable e) {
                LLog.d("onCompleted: 请求 Buckets 失败\t" + e.getMessage());
                getView().onFailed(action, "请求失败");
            }

            @Override
            public void onResult(List<BucketEntity> bucketEntities) {
                if (bucketEntities != null && !bucketEntities.isEmpty()) {
                    if (action == Api.EVENT_BEGIN) {
                        getView().showList(bucketEntities);
                    }
                    if (action == Api.EVENT_REFRESH) {
                        getView().refreshComments(bucketEntities);
                    }
                } else {
                    getView().onEmpty("您还没有作品");
                }

            }
        });
    }

}
