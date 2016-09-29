package com.simon.dribbble.ui.user;

import com.simon.dribbble.data.model.User;
import com.simon.dribbble.ui.BasePresenterImpl;

import net.quickrecyclerview.utils.log.LLog;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Simon Han on 2016/9/17.
 */

public class UserInfoPresenter extends BasePresenterImpl implements UserInfoContract.Presenter {

    private UserInfoContract.View mView;

    public UserInfoPresenter(UserInfoContract.View view) {
        mView = view;
    }

    @Override
    public void loadUserInfo(long userId) {
        Subscription subscription = mDataManger.getUsersInfo(userId)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: 用户信息请求完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFailed(0, e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        mView.showUserInfo(user);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void subscribe() {

    }
}
