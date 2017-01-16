package com.simon.dribbble.ui.shots;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.simon.agiledevelop.BaseActivity;
import com.simon.agiledevelop.MvpRxPresenter;
import com.simon.agiledevelop.recycler.adapter.RapidAdapter;
import com.simon.agiledevelop.recycler.listeners.OnItemClickListener;
import com.simon.agiledevelop.utils.App;
import com.simon.agiledevelop.utils.ResHelper;
import com.simon.agiledevelop.utils.ToastHelper;
import com.simon.dribbble.R;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.CommentEntity;
import com.simon.dribbble.listener.TextWatcherImpl;
import com.simon.dribbble.util.DialogHelp;
import com.simon.dribbble.widget.loadingdia.SpotsDialog;
import com.simon.dribbble.widget.statelayout.StateLayout;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/1 16:53
 */

public class CommentActivity extends BaseActivity<CommentPresenter> implements CommentContract
        .View, SwipeRefreshLayout.OnRefreshListener, RapidAdapter.LoadMoreListener {

    private String[] mCommOption;

    private RecyclerView mRecyclerView;
    private CommentPresenter mPresenter;
    private int mPageNo = 1;
    private StateLayout mStateLayout;
    private CommentAdapter mAdapter;
    private long mShotId;
    private EditText mEdit_comment;
    private ImageView mAdd_comment;

    private SpotsDialog mLoadingDialog;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comments;
    }

    @Override
    protected CommentPresenter getPresenter() {
        return null;
    }

    @Override
    protected View getLoadingView() {
        return null;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setCommonBackToolBack(toolbar, "评论");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRefreshLayout.setColorSchemeResources(R.color.purple_500, R.color.blue_500, R.color
                .orange_500, R.color.pink_500);

        mStateLayout = (StateLayout) findViewById(R.id.stateLayout_comments);

        mRecyclerView = (RecyclerView) findViewById(R.id.xrv_comment);

        mEdit_comment = (EditText) findViewById(R.id.et_add_comment);
        mAdd_comment = (ImageView) findViewById(R.id.imv_add_comment);
        mAdd_comment.setEnabled(false);

        LinearLayoutManager rlm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(rlm);

        mAdapter = new CommentAdapter();
        mAdapter.openAnimation(RapidAdapter.SCALEIN);
        mAdapter.setLoadMoreEnable(true);
        mAdapter.setOnLoadMoreListener(this);

        mPresenter = new CommentPresenter(this);
    }

    @Override
    protected void initEventAndData() {

        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            protected void onItemClick(RapidAdapter adapter, RecyclerView recyclerView, View
                    view, int position) {
                AlertDialog.Builder dia_comm = DialogHelp.getSelectDialog(CommentActivity.this,
                        "评论选项", mCommOption, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ToastHelper.showLongToast(App.INSTANCE, "评论选项 " + which);
                            }
                        });
                dia_comm.show();
            }
        });

        mEdit_comment.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    mAdd_comment.setEnabled(true);
                } else {
                    mAdd_comment.setEnabled(false);
                }
            }
        });


        mAdd_comment.setOnClickListener(this);

        mCommOption = ResHelper.getStringArray(R.array.comm_option);

        Bundle bundle = getBundle();
        if (null != bundle) {
            mShotId = bundle.getLong("shotId");
            mPresenter.loadComments(mShotId, "comments", mPageNo, Api.EVENT_BEGIN);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.imv_add_comment) {
            String s = mEdit_comment.getText().toString();
//            Html.toHtml();
            startIntent(RichEditorActivity.class);
        }
    }

    @Override
    public void showComments(List<CommentEntity> comments) {
        mStateLayout.showContentView();
        hideDialog();

        mAdapter.setNewData(comments);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void refreshComments(List<CommentEntity> comments) {
        mRefreshLayout.setRefreshing(false);

        if (!comments.isEmpty()) {
            mAdapter.setNewData(comments);
        }
    }

    @Override
    public void moreComments(List<CommentEntity> comments) {
        mAdapter.loadComplete();
        if (!comments.isEmpty()) {
            mAdapter.appendData(comments);
        } else {
            mAdapter.showNOMoreView();
        }
    }

    @Override
    public void onEmpty(String msg) {
        mStateLayout.showEmptyView();
    }

    @Override
    public void showLoading(int action, String msg) {
        if (Api.EVENT_BEGIN == action) {
            showDialog();
        }
    }

    @Override
    public void onFailed(int action, String msg) {
        mStateLayout.showErrorView();
    }

    @Override
    public void onCompleted(int action) {

    }

    @Override
    public void setPresenter(MvpRxPresenter presenter) {

    }

    @Override
    public void onRefresh() {
        mPageNo = 1;
        mPresenter.loadComments(mShotId, "comments", mPageNo, Api.EVENT_REFRESH);
    }

    @Override
    public void onLoadMore() {
        mPageNo++;
        mPresenter.loadComments(mShotId, "comments", mPageNo, Api.EVENT_MORE);
    }


    private void showDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogHelp.getLoadingDialog(this, "正在加载...");
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void hideDialog() {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

}
