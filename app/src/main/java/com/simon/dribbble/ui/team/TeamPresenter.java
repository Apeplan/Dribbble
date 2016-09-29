package com.simon.dribbble.ui.team;

import com.simon.dribbble.data.model.TeamEntity;
import com.simon.dribbble.ui.BasePresenterImpl;
import com.simon.dribbble.ui.baselist.BaseListContract;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/13 9:36
 */

public class TeamPresenter extends BasePresenterImpl implements BaseListContract.Presenter {

    private BaseListContract.View mView;

    public TeamPresenter(BaseListContract.View view) {
        mView = view;
    }

    @Override
    public void loadList(long id, String type, int page, int event) {

        Subscription subscription = mDataManger.getUserTeams()
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<TeamEntity>>() {
                    @Override
                    public void onCompleted() {
                        mView.onCompleted();
                        LLog.d("Simon", "onCompleted: 用户Team 请求完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFailed(0, e.getMessage());
                    }

                    @Override
                    public void onNext(List<TeamEntity> teams) {
                        if (teams.isEmpty()) {
                            mView.onEmpty();
                        } else {
                            mView.showList(teams);
                        }
                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void subscribe() {

    }

}
