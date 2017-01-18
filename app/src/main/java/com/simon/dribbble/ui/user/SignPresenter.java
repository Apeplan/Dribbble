package com.simon.dribbble.ui.user;

import com.simon.agiledevelop.mvpframe.RxPresenter;
import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.User;
import com.simon.dribbble.util.DribbblePrefs;

import rx.Observable;

/**
 * Created by Simon Han on 2016/8/20.
 */

public class SignPresenter extends RxPresenter<SignInContract.View, User> {


    public SignPresenter(SignInContract.View view) {
        attachView(view);
        view.setPresenter(this);
    }

    public void getUserToken(final String token) {
        Observable<User> tokenAndUser = DribbbleDataManger.getInstance().getTokenAndUser(token);
        subscribe(tokenAndUser, new ResultSubscriber<User>() {
            @Override
            public void onStartRequest() {

            }

            @Override
            public void onEndRequest() {
                LLog.d("onCompleted: 请求用户信息执行完成");
                getView().onCompleted(0);
            }

            @Override
            public void onFailed(Throwable e) {
                getView().onFailed(0, e.getMessage());
                LLog.d("onError: 请求用户信息  " + e.getMessage());
            }

            @Override
            public void onResult(User user) {
                if (null != user) {
                    DribbblePrefs.getInstance().setLoggedInUser(user);
                    getView().signSuccess();
                } else {
                    getView().onFailed(0, "");
                }
            }
        });

    }
}
