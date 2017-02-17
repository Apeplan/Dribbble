package com.simon.dribbble.ui.user;

import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.agiledevelop.utils.App;
import com.simon.agiledevelop.utils.NetHelper;
import com.simon.agiledevelop.utils.ResHelper;
import com.simon.dribbble.R;
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
 * Created on: 2016/9/12 18:12
 */

public class UserShotsPresenter extends CommListPresenter<CommListContract.View, List<ShotEntity>> {

    public UserShotsPresenter(CommListContract.View view) {
        attachView(view);
        view.setPresenter(this);
    }

    @Override
    public void loadList(final int action, long id, String type, int page) {

        if (!NetHelper.isNetworkConnected(App.INSTANCE)) {
            getView().onFailed(action, ResHelper.getStrByResid(R.string.network_exception));
            return;
        }

        Observable<List<ShotEntity>> userShots = DribbbleDataManger.getInstance().getUserShots
                (page);
        subscribe(userShots, new ResultSubscriber<List<ShotEntity>>() {
            @Override
            public void onStartRequest() {
                getView().showLoading(action, "");
            }

            @Override
            public void onEndRequest() {
                LLog.d("onCompleted: 用户 Shots 请求完成");
                getView().onCompleted(action);
            }

            @Override
            public void onFailed(Throwable e) {
                getView().onFailed(action, e.getMessage());
            }

            @Override
            public void onResult(List<ShotEntity> shotEntities) {
                if (shotEntities.isEmpty()) {
                    getView().onEmpty("");
                } else {
                    if (action == Api.EVENT_BEGIN) {
                        getView().showList(shotEntities);
                    }
                    if (action == Api.EVENT_REFRESH) {
                        getView().refreshComments(shotEntities);
                    }
                    if (action == Api.EVENT_MORE) {
                        getView().moreComments(shotEntities);
                    }
                }
            }
        });

    }

}
