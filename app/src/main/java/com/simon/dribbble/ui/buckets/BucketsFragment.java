package com.simon.dribbble.ui.buckets;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.BucketEntity;
import com.simon.dribbble.ui.baselist.BaseListContract;
import com.simon.dribbble.ui.baselist.BaseListFragment;
import com.simon.dribbble.util.DialogHelp;
import com.simon.dribbble.util.ToastHelper;

import net.quickrecyclerview.show.BaseQuickAdapter;
import net.quickrecyclerview.utils.log.LLog;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/31 11:42
 */

public class BucketsFragment extends BaseListFragment<BucketEntity> {

    public static BucketsFragment newInstance() {
        BucketsFragment fragment = new BucketsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected BaseListContract.Presenter getPresenter() {
        return new BucketPresenter(this);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.loadList( Api.USER_ID,"", 1,Api.EVENT_BEGIN);
        LLog.d("Simon", "initEventAndData: ");
    }

    @Override
    protected BaseQuickAdapter<BucketEntity> getListAdapter() {
        return new UserBucketsAdapter();
    }

    @Override
    protected void itemClick(View view, int position) {
        final String[] options = {"编辑", "删除"};
        DialogHelp.getSelectDialog(getActivity(), "操作选项", options, new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastHelper.shortToast(options[which]);
            }
        }).show();
    }

    @Override
    public void setPresenter(BaseListContract.Presenter presenter) {

    }
}
