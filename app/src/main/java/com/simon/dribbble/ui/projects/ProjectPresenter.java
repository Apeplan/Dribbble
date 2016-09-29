package com.simon.dribbble.ui.projects;

import com.simon.dribbble.data.model.ProjectEntity;
import com.simon.dribbble.ui.BasePresenterImpl;
import com.simon.dribbble.ui.baselist.BaseListContract;

import net.quickrecyclerview.utils.log.LLog;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 18:31
 */

public class ProjectPresenter extends BasePresenterImpl implements BaseListContract.Presenter {

    private BaseListContract.View mView;

    public ProjectPresenter(BaseListContract.View view) {
        mView = view;
    }

    @Override
    public void loadList(long id, String type, int page, int event) {

        Subscription subscription = mDataManger.getUserProjects()
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribe(new Subscriber<List<ProjectEntity>>() {
                    @Override
                    public void onCompleted() {
                        LLog.d("Simon", "onCompleted: Project 请求成功");
                        mView.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFailed(0, e.getMessage());
                    }

                    @Override
                    public void onNext(List<ProjectEntity> projects) {
                        if (projects.isEmpty()) {
                            mView.onEmpty();
                        } else {
                            mView.showList(projects);
                        }
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void subscribe() {

    }

}
