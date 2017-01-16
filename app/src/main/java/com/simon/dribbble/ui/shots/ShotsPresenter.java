package com.simon.dribbble.ui.shots;

import com.simon.agiledevelop.MvpRxPresenter;
import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.remote.DribbbleService;

import java.util.List;

import rx.Observable;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/29 16:54
 */

public class ShotsPresenter extends MvpRxPresenter<ShotsContract.View, List<ShotEntity>> {

    public ShotsPresenter(ShotsContract.View shotsView) {
        attachView(shotsView);
        shotsView.setPresenter(this);
    }

    public void loadShotsList(int page, @DribbbleService.ShotType String list, @DribbbleService
            .ShotTimeframe String timeframe, @DribbbleService.ShotSort String sort, final int
                                      action) {

        Observable<List<ShotEntity>> shotsList = DribbbleDataManger.getInstance().getShotsList
                (page, list, timeframe, sort);

        subscribe(shotsList, new ResultSubscriber<List<ShotEntity>>() {
            @Override
            public void onStartRequest() {
                getView().showLoading(action,"");
            }

            @Override
            public void onEndRequest() {
                getView().onCompleted(action);
                LLog.d("onCompleted: Shots List 请求完成");
            }

            @Override
            public void onFailed(Throwable e) {
                LLog.d("onCompleted: Shots List 请求失败" + e.getMessage());
                getView().onFailed(action, e.getMessage());
            }

            @Override
            public void onResult(List<ShotEntity> shotEntities) {
                if (action == Api.EVENT_REFRESH) {
                    getView().renderRefrshShotsList(shotEntities);
                } else if (action == Api.EVENT_MORE) {
                    getView().renderMoreShotsList(shotEntities);
                } else {
                    getView().renderShotsList(shotEntities);
                }
                LLog.d("onNext: Shots List 请求成功" + shotEntities.size());
            }
        });
    }

}
