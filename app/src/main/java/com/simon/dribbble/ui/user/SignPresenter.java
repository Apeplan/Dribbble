package com.simon.dribbble.ui.user;

import com.simon.dribbble.DribbbleApp;
import com.simon.dribbble.GlobalConstant;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.UserEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.util.schedulers.BaseSchedulerProvider;
import com.simon.dribbble.util.schedulers.SchedulerProvider;

import net.quickrecyclerview.utils.log.LLog;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Simon Han on 2016/8/20.
 */

public class SignPresenter implements SignInContract.Presenter {

    private SignInContract.View mSignView;
    private DribbbleDataManger mDataManger;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private CompositeSubscription mSubscription;


    public SignPresenter(SignInContract.View view) {
        mSignView = view;
        mDataManger = DribbbleDataManger.getInstance(DribbbleApi.Creator.dribbbleApi());
        mBaseSchedulerProvider = SchedulerProvider.getInstance();
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void getUserToken(final String token) {
        mSubscription.clear();

        Subscription subscription = mDataManger.getTokenAndUser(token)
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribeOn(mBaseSchedulerProvider.io())
                .subscribe(new Subscriber<UserEntity>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: 请求用户信息执行完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSignView.onFailed(0, e.getMessage());
                        LLog.d("Simon", "onError: 请求用户信息  " + e.getMessage());
                    }

                    @Override
                    public void onNext(UserEntity userEntity) {
                        if (null != userEntity) {
                            DribbbleApp.context().setUserInfo(userEntity);
                            DribbbleApp.spHelper().put(GlobalConstant.USER_NAME, userEntity
                                    .username);
                            DribbbleApp.spHelper().put(GlobalConstant.USER_ID, userEntity.id);
                            DribbbleApp.spHelper().put(GlobalConstant.USER_PROFILE, userEntity
                                    .avatar_url);
                            mSignView.signSuccess();
                        } else {
                            mSignView.onFailed(0, "");
                        }
                    }
                });
        mSubscription.add(subscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscription.clear();
    }
}
