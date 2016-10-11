package com.simon.dribbble.ui.shots;

import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.model.AttachmentEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
import com.simon.dribbble.ui.baselist.BaseListContract;
import com.simon.dribbble.ui.baselist.BaseListFragment;

import net.quickrecyclerview.show.BaseQuickAdapter;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 16:12
 */

public class AttachmentFragment extends BaseListFragment<AttachmentEntity> {

    public static AttachmentFragment newInstance() {
        AttachmentFragment fragment = new AttachmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private long mShotId;

    @Override
    protected BaseQuickAdapter<AttachmentEntity> getListAdapter() {
        return new AttachAdapter();
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        Bundle args = getArguments();
        if (null != args) {
            mShotId = args.getLong("shotId");
            mPresenter.loadList(mShotId, "", 1, DribbbleApi.EVENT_BEGIN);
        } else {
            onEmpty();
        }
    }

    @Override
    protected void itemClick(View view, int position) {

    }

    @Override
    protected BaseListContract.Presenter getPresenter() {
        return new AttachPresenter(this);
    }

    @Override
    public void setPresenter(BaseListContract.Presenter presenter) {

    }
}
