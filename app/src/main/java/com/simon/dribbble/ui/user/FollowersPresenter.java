package com.simon.dribbble.ui.user;

import com.simon.dribbble.data.model.FollowersEntity;
import com.simon.dribbble.ui.BasePresenterImpl;
import com.simon.dribbble.ui.baselist.BaseListContract;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 15:58
 */

public class FollowersPresenter extends BasePresenterImpl implements BaseListContract.Presenter {

    private BaseListContract.View mView;

    public FollowersPresenter(BaseListContract.View view) {
        mView = view;
    }

    @Override
    public void loadList(long id, String type, int page,int event) {

        Subscription subscription = mDataManger.getUserFollowers(page)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<FollowersEntity>>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: 用户 Followers 请求完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFailed(0, e.getMessage());
                    }

                    @Override
                    public void onNext(List<FollowersEntity> followersEntities) {
                        if (followersEntities.isEmpty()) {
                            mView.onEmpty();
                        } else {
                            mView.showList(followersEntities);
                        }
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void subscribe() {

    }
}
