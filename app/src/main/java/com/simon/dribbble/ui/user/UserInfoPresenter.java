package com.simon.dribbble.ui.user;

import com.simon.agiledevelop.MvpRxPresenter;
import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.User;

import rx.Observable;

/**
 * Created by Simon Han on 2016/9/17.
 */

public class UserInfoPresenter extends MvpRxPresenter<UserInfoContract.View, User> {

    public UserInfoPresenter(UserInfoContract.View view) {
        attachView(view);
        view.setPresenter(this);
    }

    public void loadUserInfo(final int action, long userId) {

        Observable<User> usersInfo = DribbbleDataManger.getInstance().getUsersInfo(userId);
        subscribe(usersInfo, new ResultSubscriber<User>() {
            @Override
            public void onStartRequest() {
                getView().showLoading(action, "");
            }

            @Override
            public void onEndRequest() {
                LLog.d("onCompleted: 用户信息请求完成");
                getView().onCompleted(action);
            }

            @Override
            public void onFailed(Throwable e) {
                getView().onFailed(action, e.getMessage());
            }

            @Override
            public void onResult(User user) {
                getView().showUserInfo(user);
            }
        });
    }
}
