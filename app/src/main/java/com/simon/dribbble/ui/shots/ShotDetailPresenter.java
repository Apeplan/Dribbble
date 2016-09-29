package com.simon.dribbble.ui.shots;

import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.CommentEntity;
import com.simon.dribbble.data.model.LikeEntity;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.util.schedulers.BaseSchedulerProvider;
import com.simon.dribbble.util.schedulers.SchedulerProvider;

import net.quickrecyclerview.utils.log.LLog;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/31 17:46
 */

public class ShotDetailPresenter implements ShotDetailContract.Presenter {

    private ShotDetailContract.View mShotView;
    private DribbbleDataManger mRemoteDataSource;
    private final BaseSchedulerProvider mSchedulerProvider;
    private CompositeSubscription mSubscriptions;

    public ShotDetailPresenter(ShotDetailContract.View shotsView) {
        mShotView = shotsView;
        mRemoteDataSource = DribbbleDataManger.getInstance(DribbbleApi.Creator.dribbbleApi());
        mSchedulerProvider = SchedulerProvider.getInstance();
        mSubscriptions = new CompositeSubscription();
        mShotView.setPresenter(this);
    }

    @Override
    public void loadShot(long id) {
        mSubscriptions.clear();

        Subscription subscription = mRemoteDataSource.getShot(id)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<ShotEntity>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: Shot信息 请求完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.d("Simon", "onCompleted: Shot信息 请求失败\t" + e.getMessage());
                        mShotView.onFailed(0, e.getMessage());
                    }

                    @Override
                    public void onNext(ShotEntity shotsEntity) {
                        LLog.d("Simon", "onCompleted: Shot信息 请求成功");
                        mShotView.showShot(shotsEntity);
                    }
                });

        mSubscriptions.add(subscription);

    }

    public void addLike(long shotId) {

        mShotView.showLoadDia();

        mSubscriptions.clear();

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

        mSubscriptions.add(subscription);
    }

    public void sendComment(long shotId, String content) {
        mShotView.showLoadDia();

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
        mSubscriptions.add(subscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }
}
