package com.simon.dribbble.ui.buckets;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.simon.agiledevelop.log.LLog;
import com.simon.agiledevelop.utils.App;
import com.simon.agiledevelop.utils.ToastHelper;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.ui.CommListFragment;
import com.simon.dribbble.ui.CommListPresenter;
import com.simon.dribbble.util.DialogHelp;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/31 11:42
 */

public class UserBucketsFragment extends CommListFragment<BucketPresenter, UserBucketsAdapter> {

    public static UserBucketsFragment newInstance() {
        UserBucketsFragment fragment = new UserBucketsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BucketPresenter getPresenter() {
        return new BucketPresenter(this);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        mPresenter.loadList(Api.EVENT_BEGIN, Api.USER_ID, "", 0);
        LLog.d("initEventAndData: ");
    }

    @Override
    protected UserBucketsAdapter getListAdapter() {
        return new UserBucketsAdapter();
    }

    protected void itemClick(View view, int position) {
        final String[] options = {"编辑", "删除"};
        DialogHelp.getSelectDialog(getActivity(), "操作选项", options, new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastHelper.showLongToast(App.INSTANCE, options[which]);
            }
        }).show();
    }


    @Override
    public void setPresenter(CommListPresenter presenter) {

    }

    @Override
    protected void refreshData() {
        super.refreshData();
        mPresenter.loadList(Api.EVENT_REFRESH, Api.USER_ID, "", 0);
    }

    @Override
    protected boolean isLoadMoreEnabled() {
        return false;
    }
}
