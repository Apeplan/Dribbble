package com.simon.dribbble.ui.shots;

import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.agiledevelop.utils.App;
import com.simon.agiledevelop.utils.NetHelper;
import com.simon.agiledevelop.utils.ResHelper;
import com.simon.dribbble.R;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.LikeEntity;
import com.simon.dribbble.ui.CommListContract;
import com.simon.dribbble.ui.CommListPresenter;

import java.util.List;

import rx.Observable;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/1 17:12
 */

public class LikesPresenter extends CommListPresenter<CommListContract.View, List<LikeEntity>> {

    public LikesPresenter(CommListContract.View shotsView) {
        attachView(shotsView);
        shotsView.setPresenter(this);
    }

    @Override
    public void loadList(final int action, long id, String type, int page) {

        if (!NetHelper.isNetworkConnected(App.INSTANCE)) {
            getView().onFailed(action, ResHelper.getStrByResid(R.string.network_exception));
            return;
        }

        Observable<List<LikeEntity>> shotLikes = DribbbleDataManger.getInstance().getShotLikes
                (id, page);
        subscribe(shotLikes, new ResultSubscriber<List<LikeEntity>>() {
            @Override
            public void onStartRequest() {
                getView().showLoading(action, "");
            }

            @Override
            public void onEndRequest() {
                LLog.d("onCompleted: 获取评论");
                getView().onCompleted(action);
            }

            @Override
            public void onFailed(Throwable e) {
                LLog.d("onCompleted: 获取评论失败\t" + e.getMessage());
                getView().onFailed(action, e.getMessage());
            }

            @Override
            public void onResult(List<LikeEntity> likeEntities) {
                LLog.d("onCompleted: 获取评论成功");

                if (action == Api.EVENT_BEGIN) {
                    if (likeEntities.isEmpty()) {
                        getView().onEmpty("");
                    } else {
                        getView().showList(likeEntities);
                    }
                } else if (action == Api.EVENT_REFRESH) {
                    getView().refreshComments(likeEntities);
                } else {
                    getView().moreComments(likeEntities);
                }
            }
        });

    }

}
