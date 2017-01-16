package com.simon.dribbble.ui.user;

import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.ui.CommListContract;
import com.simon.dribbble.ui.CommListPresenter;

import java.util.List;

import rx.Observable;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/29 16:54
 */

public class LikeShotsPresenter extends CommListPresenter<CommListContract.View, List<ShotEntity>> {

    public LikeShotsPresenter(CommListContract.View shotsView) {
        attachView(shotsView);
        shotsView.setPresenter(this);
    }

    @Override
    public void loadList(final int action, long id, String type, int page) {

        Observable<List<ShotEntity>> userLikes = DribbbleDataManger.getInstance().getUserLikes
                (page);
        subscribe(userLikes, new ResultSubscriber<List<ShotEntity>>() {
            @Override
            public void onStartRequest() {
                getView().showLoading(action, "");
            }

            @Override
            public void onEndRequest() {
                LLog.d("onCompleted: Shots List 请求完成");
                getView().onCompleted(action);
            }

            @Override
            public void onFailed(Throwable e) {
                LLog.d("onCompleted: Shots List 请求失败" + e.getMessage());
                getView().onFailed(action, e.getMessage());
            }

            @Override
            public void onResult(List<ShotEntity> shotEntities) {
                if (action == Api.EVENT_REFRESH) {
                    getView().refreshComments(shotEntities);
                } else if (action == Api.EVENT_MORE) {
                    getView().moreComments(shotEntities);
                } else {
                    getView().showList(shotEntities);
                }
                LLog.d("onNext: Shots List 请求成功" + shotEntities.size());
            }
        });
    }

}
