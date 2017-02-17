package com.simon.dribbble.ui.projects;

import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.agiledevelop.utils.App;
import com.simon.agiledevelop.utils.NetHelper;
import com.simon.agiledevelop.utils.ResHelper;
import com.simon.dribbble.R;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.ProjectEntity;
import com.simon.dribbble.ui.CommListContract;
import com.simon.dribbble.ui.CommListPresenter;

import java.util.List;

import rx.Observable;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 18:31
 */

public class ProjectPresenter extends CommListPresenter<CommListContract.View,
        List<ProjectEntity>> {

    public ProjectPresenter(CommListContract.View view) {
        attachView(view);
        view.setPresenter(this);
    }

    @Override
    public void loadList(final int action, long id, String type, int page) {

        if (!NetHelper.isNetworkConnected(App.INSTANCE)) {
            getView().onFailed(action, ResHelper.getStrByResid(R.string.network_exception));
            return;
        }

        Observable<List<ProjectEntity>> userProjects = DribbbleDataManger.getInstance()
                .getUserProjects();

        subscribe(userProjects, new ResultSubscriber<List<ProjectEntity>>() {
            @Override
            public void onStartRequest() {
                getView().showLoading(action, "");
            }

            @Override
            public void onEndRequest() {
                LLog.d("onCompleted: Project 请求成功");
                getView().onCompleted(action);
            }

            @Override
            public void onFailed(Throwable e) {
                getView().onFailed(action, e.getMessage());
            }

            @Override
            public void onResult(List<ProjectEntity> projectEntities) {
                if (projectEntities.isEmpty()) {
                    getView().onEmpty("");
                } else {
                    getView().showList(projectEntities);
                }
            }
        });

    }
}
