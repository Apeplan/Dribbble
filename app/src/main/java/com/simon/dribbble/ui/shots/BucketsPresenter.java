package com.simon.dribbble.ui.shots;

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
 * Created on: 2016/9/14 17:30
 */

public class BucketsPresenter extends CommListPresenter<CommListContract.View, List<BucketEntity>> {

    public BucketsPresenter(CommListContract.View view) {
        attachView(view);
        view.setPresenter(this);
    }

    @Override
    public void loadList(final int action, long id, String type, int page) {

        Observable<List<BucketEntity>> buckets = DribbbleDataManger.getInstance().getBuckets
                (type, id, page);
        subscribe(buckets, new ResultSubscriber<List<BucketEntity>>() {
            @Override
            public void onStartRequest() {
                getView().showLoading(action, "");
            }

            @Override
            public void onEndRequest() {
                getView().onCompleted(action);
                LLog.d("onCompleted: 请求Buckets 请求成功");
            }

            @Override
            public void onFailed(Throwable e) {
                getView().onFailed(action, e.getMessage());
            }

            @Override
            public void onResult(List<BucketEntity> bucketEntities) {
                if (action == Api.EVENT_BEGIN) {
                    if (bucketEntities.isEmpty()) {
                        getView().onEmpty("");
                    } else {
                        getView().showList(bucketEntities);
                    }
                } else if (action == Api.EVENT_REFRESH) {
                    getView().refreshComments(bucketEntities);
                } else {
                    getView().moreComments(bucketEntities);
                }
            }
        });

    }

}
