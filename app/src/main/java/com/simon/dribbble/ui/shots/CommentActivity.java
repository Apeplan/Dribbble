package com.simon.dribbble.ui.shots;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.simon.dribbble.R;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.CommentEntity;
import com.simon.dribbble.listener.TextWatcherImpl;
import com.simon.dribbble.ui.BaseActivity;
import com.simon.dribbble.util.DialogHelp;
import com.simon.dribbble.util.ResHelper;
import com.simon.dribbble.util.ToastHelper;
import com.simon.dribbble.widget.statelayout.StateLayout;

import net.quickrecyclerview.XRecyclerView;
import net.quickrecyclerview.show.BaseQuickAdapter;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/1 16:53
 */

public class CommentActivity extends BaseActivity<CommentPresenter> implements CommentContract
        .View, XRecyclerView.LoadingListener, BaseQuickAdapter.OnRecyclerViewItemClickListener {

    private String[] mCommOption;

    private XRecyclerView mXRecyclerView;
    private CommentPresenter mPresenter;
    //    private PopupMenu mPopup;
    private int mPageNo = 1;
    private StateLayout mStateLayout;
    private CommentAdapter mAdapter;
    private long mShotId;
    private EditText mEdit_comment;
    private ImageView mAdd_comment;

    @Override
    protected int getLayout() {
        return R.layout.activity_comments;
    }

    @Override
    protected CommentPresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setSubtitle(getBundle().getInt("count", 0) + "");
        setCommonBackToolBack(toolbar, "评论");

        mStateLayout = (StateLayout) findViewById(R.id.stateLayout_comments);

        mXRecyclerView = (XRecyclerView) findViewById(R.id.xrv_comment);

        mEdit_comment = (EditText) findViewById(R.id.et_add_comment);
        mAdd_comment = (ImageView) findViewById(R.id.imv_add_comment);
        mAdd_comment.setEnabled(false);

        LinearLayoutManager rlm = new LinearLayoutManager(this);
        mXRecyclerView.setLayoutManager(rlm);
//        HorizontalDivider divider = new HorizontalDivider.Builder(this).colorResId(R.color
//                .color_999999).size(1).build();
//        mXRecyclerView.addItemDecoration(divider);

        mAdapter = new CommentAdapter();
        mAdapter.setOnRecyclerViewItemClickListener(this);
        mXRecyclerView.setAdapter(mAdapter);

        mPresenter = new CommentPresenter(this);
    }

    @Override
    protected void initEventAndData() {
        mXRecyclerView.setLoadingListener(this);
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
            mStateLayout.showProgressView();
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
        if (mAdapter == null) {
            mAdapter = new CommentAdapter();
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            mXRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.removeAll();
        mAdapter.addData(comments);
    }

    @Override
    public void refreshComments(List<CommentEntity> comments) {
        mXRecyclerView.refreshComplete();
        if (!comments.isEmpty()) {
            mAdapter.removeAll();
            mAdapter.addData(comments);
        }
    }

    @Override
    public void moreComments(List<CommentEntity> comments) {
        mXRecyclerView.loadMoreComplete();
        if (!comments.isEmpty()) {
            mAdapter.addData(comments);
        } else {
            mXRecyclerView.setNoMore(true);
            ToastHelper.shortToast("没有更多数据了");
        }
    }

    @Override
    public void onEmpty() {
        mStateLayout.showEmptyView();
    }

    @Override
    public void onFailed(int action, String msg) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void setPresenter(CommentContract.Presenter presenter) {

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

    @Override
    public void onItemClick(View view, int position) {
        AlertDialog.Builder dia_comm = DialogHelp.getSelectDialog(this, "评论选项", mCommOption, new
                DialogInterface.OnClickListener
                        () {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastHelper.shortToast("评论选项 " + which);
                    }
                });
        dia_comm.show();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_sort, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sort) {
            showFilteringPopUpMenu();
            return true;
        } else {

            return super.onOptionsItemSelected(item);
        }
    }

    public void showFilteringPopUpMenu() {
        if (mPopup == null) {
            mPopup = new PopupMenu(this, this.findViewById(R.id.menu_sort));
            mPopup.getMenuInflater().inflate(R.menu.filer_sort, mPopup.getMenu());
            mPopup.getMenu().findItem(R.id.menu_sort_oldest).setChecked(true);
            mPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    ToastHelper.shortToast(item.getTitle());
                    item.setChecked(true);
                    switch (item.getItemId()) {
                        case R.id.menu_sort_oldest:

                            break;
                        case R.id.menu_sort_newest:

                            break;
                        case R.id.menu_sort_liked:

                            break;

                        default:

                            break;
                    }

                    return true;
                }
            });
        }

        mPopup.show();
    }*/
}
