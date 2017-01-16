package com.simon.dribbble.ui.shots;

import com.simon.agiledevelop.MvpRxPresenter;
import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.remote.DribbbleService;

import java.util.List;

import rx.Observable;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 11:48
 */

public class SearchPresenter extends MvpRxPresenter<SearchContract.View, List<ShotEntity>> {

    public SearchPresenter(SearchContract.View view) {
        attachView(view);
        view.setPresenter(this);
    }

    public void searchShot(String key, int page, @DribbbleService.SortOrder String sort) {


        Observable<List<ShotEntity>> search = DribbbleDataManger.getInstance().search(key, page,
                sort);
        subscribe(search, new ResultSubscriber<List<ShotEntity>>() {
            @Override
            public void onStartRequest() {

            }

            @Override
            public void onEndRequest() {
                LLog.d("onCompleted: ");
                getView().onCompleted(0);
            }

            @Override
            public void onFailed(Throwable e) {
                LLog.d("onError: " + e.getMessage());
                getView().onFailed(0, e.getMessage());
            }

            @Override
            public void onResult(List<ShotEntity> shotEntities) {
                LLog.d("onNext: " + shotEntities.size());
                if (shotEntities.isEmpty()) {
                    getView().onEmpty("");
                } else {
                    getView().showSearch(shotEntities);
                }
            }
        });

    }
}
