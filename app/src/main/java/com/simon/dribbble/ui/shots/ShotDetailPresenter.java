package com.simon.dribbble.ui.shots;

import com.simon.agiledevelop.mvpframe.RxPresenter;
import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.agiledevelop.utils.App;
import com.simon.agiledevelop.utils.NetHelper;
import com.simon.agiledevelop.utils.ResHelper;
import com.simon.dribbble.R;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.ShotEntity;

import rx.Observable;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/31 17:46
 */

public class ShotDetailPresenter extends RxPresenter<ShotDetailContract.View, ShotEntity> {

    public ShotDetailPresenter(ShotDetailContract.View shotsView) {
        attachView(shotsView);
        shotsView.setPresenter(this);
    }

    public void loadShot(final int action, long id) {

        if (!NetHelper.isNetworkConnected(App.INSTANCE)) {
            getView().onFailed(action, ResHelper.getStrByResid(R.string.network_exception));
            return;
        }

        Observable<ShotEntity> shot = DribbbleDataManger.getInstance().getShot(id);
        subscribe(shot, new ResultSubscriber<ShotEntity>() {
            @Override
            public void onStartRequest() {
                getView().showLoading(action, "");
            }

            @Override
            public void onEndRequest() {
                LLog.d("onCompleted: Shot信息 请求完成");
                getView().onCompleted(action);
            }

            @Override
            public void onFailed(Throwable e) {
                LLog.d("onCompleted: Shot信息 请求失败\t" + e.getMessage());
                getView().onFailed(action, e.getMessage());
            }

            @Override
            public void onResult(ShotEntity shotEntity) {
                LLog.d("onCompleted: Shot信息 请求成功");
                getView().showShot(shotEntity);
            }
        });

    }

    public void addLike(long shotId) {

       /* getView().showLoadDia();

        Observable<LikeEntity> likeEntity = DribbbleDataManger.getInstance().addLike(shotId);

        subscribe();

        Subscription subscription = mRemoteDataSource.addLike(shotId)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<LikeEntity>() {
                    @Override
                    public void onCompleted() {
                        mShotView.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mShotView.hintMsg("添加喜欢失败");
                    }

                    @Override
                    public void onNext(LikeEntity likeEntity) {
                        mShotView.hintMsg("添加喜欢成功");
                    }
                });

        mSubscriptions.add(subscription);*/
    }

    public void sendComment(long shotId, String content) {
       /* mShotView.showLoadDia();

        mSubscriptions.clear();

        Subscription subscription = mRemoteDataSource.createComment(shotId, content)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<CommentEntity>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: 评论请求");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.d("Simon", "onCompleted: 评论请求失败");
                        mShotView.hintMsg("评论失败");
                    }

                    @Override
                    public void onNext(CommentEntity commentEntity) {
                        LLog.d("Simon", "onCompleted: 评论请求成功");
                        mShotView.hintMsg("评论发表成功");
                    }
                });
        mSubscriptions.add(subscription);*/
    }

}
