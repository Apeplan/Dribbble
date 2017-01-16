package com.simon.dribbble.ui.team;

import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.TeamEntity;
import com.simon.dribbble.ui.CommListContract;
import com.simon.dribbble.ui.CommListPresenter;

import java.util.List;

import rx.Observable;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/13 9:36
 */

public class TeamPresenter extends CommListPresenter<CommListContract.View, List<TeamEntity>> {

    public TeamPresenter(CommListContract.View view) {
        attachView(view);
        view.setPresenter(this);
    }

    @Override
    public void loadList(final int action, long id, String type, int page) {
        Observable<List<TeamEntity>> userTeams = DribbbleDataManger.getInstance().getUserTeams();
        subscribe(userTeams, new ResultSubscriber<List<TeamEntity>>() {
            @Override
            public void onStartRequest() {
                getView().showLoading(action, "");
            }

            @Override
            public void onEndRequest() {
                getView().onCompleted(action);
                LLog.d("onCompleted: 用户Team 请求完成");
            }

            @Override
            public void onFailed(Throwable e) {
                getView().onFailed(action, e.getMessage());
            }

            @Override
            public void onResult(List<TeamEntity> teamEntities) {
                if (teamEntities.isEmpty()) {
                    getView().onEmpty("");
                } else {
                    getView().showList(teamEntities);
                }
            }
        });

    }
}
