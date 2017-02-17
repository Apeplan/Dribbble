package com.simon.dribbble.ui.shots;

import com.simon.agiledevelop.mvpframe.RxPresenter;
import com.simon.agiledevelop.ResultSubscriber;
import com.simon.agiledevelop.log.LLog;
import com.simon.agiledevelop.utils.App;
import com.simon.agiledevelop.utils.NetHelper;
import com.simon.agiledevelop.utils.ResHelper;
import com.simon.dribbble.R;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.DribbbleDataManger;
import com.simon.dribbble.data.model.AttachmentEntity;
import com.simon.dribbble.ui.baselist.BaseListContract;

import java.util.List;

import rx.Observable;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 16:20
 */

public class AttachPresenter extends RxPresenter<BaseListContract.View, List<AttachmentEntity>> {

    public AttachPresenter(BaseListContract.View view) {
        attachView(view);
        view.setPresenter(this);
    }

    public void loadList(long id,int page, final int action) {

        if (!NetHelper.isNetworkConnected(App.INSTANCE)) {
            getView().onFailed(action, ResHelper.getStrByResid(R.string.network_exception));
            return;
        }

        Observable<List<AttachmentEntity>> shotAttach = DribbbleDataManger.getInstance()
                .getShotAttach(id, page);

        subscribe(shotAttach, new ResultSubscriber<List<AttachmentEntity>>() {
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
            public void onResult(List<AttachmentEntity> attachmentEntities) {
                LLog.d("onCompleted: 获取评论成功");
                if (null != attachmentEntities) {

                    if (action == Api.EVENT_BEGIN) {
                        if (attachmentEntities.isEmpty()) {
                            getView().onEmpty("");
                        } else {
                            getView().showList(attachmentEntities);
                        }
                    } else if (action == Api.EVENT_REFRESH) {
                        getView().refreshComments(attachmentEntities);
                    } else {
                        getView().moreComments(attachmentEntities);
                    }

                } else {
                    getView().onFailed(0, "获取数据失败");
                }
            }
        });
    }

}
