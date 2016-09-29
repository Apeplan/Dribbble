package com.simon.dribbble.ui.shots;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.simon.dribbble.R;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.ui.BaseFragment;
import com.simon.dribbble.util.DialogHelp;
import com.simon.dribbble.util.ToastHelper;
import com.simon.dribbble.widget.loadingdia.SpotsDialog;
import com.simon.dribbble.widget.statelayout.StateLayout;

import net.quickrecyclerview.XRecyclerView;
import net.quickrecyclerview.show.BaseQuickAdapter;

import java.util.List;


public class ShotsFragment extends BaseFragment<ShotsPresenter> implements ShotsContract.View,
        XRecyclerView.LoadingListener {

    private int mPageNo = 1;
    private StateLayout mStateLayout;
    private XRecyclerView mXRecyclerView;
    private ShotsAdapter mAdapter;
    private ShotsPresenter mPresenter;
    //    String list,String timeframe, String sort
    private String list = "";
    private String timeframe = "";
    private String sort = "";
    private SpotsDialog mLoadingDialog;
    //    private ProgressDialog mWaitDialog;

    public static ShotsFragment newInstance() {
        ShotsFragment fragment = new ShotsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_shots;
    }

    @Override
    protected ShotsPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void initView(View view) {
        setHasOptionsMenu(true);

        mStateLayout = (StateLayout) view.findViewById(R.id.progress_loading);

        mXRecyclerView = (XRecyclerView) view.findViewById(R.id.xrv_shots);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mXRecyclerView.setLayoutManager(layoutManager);

        mPresenter = new ShotsPresenter(this);

        mXRecyclerView.setLoadingListener(this);
    }

    @Override
    protected void initEventAndData() {
        mStateLayout.showProgressView();
        mPageNo = 1;
        request(Api.EVENT_BEGIN, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
//        ToastHelper.shortToast(item.getTitle());
        mPageNo = 1;
        switch (item.getItemId()) {
            case R.id.action_current:
                timeframe = Api.TIMEFRAME_NOW;
                request(Api.EVENT_BEGIN, true);
                break;
            case R.id.action_week:
                timeframe = Api.TIMEFRAME_WEEK;
                request(Api.EVENT_BEGIN, true);
                break;
            case R.id.action_month:
                timeframe = Api.TIMEFRAME_MONTH;
                request(Api.EVENT_BEGIN, true);
                break;
            case R.id.action_year:
                timeframe = Api.TIMEFRAME_YEAR;
                request(Api.EVENT_BEGIN, true);
                break;
            case R.id.action_ever:
                timeframe = Api.TIMEFRAME_EVER;
                request(Api.EVENT_BEGIN, true);
                break;
            case R.id.menu_search:
                ToastHelper.shortToast(item.getTitle());
                startIntent(SearchActivity.class);
                break;
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
        }
        return true;
    }

    public void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.filter_shots, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
//                ToastHelper.shortToast(item.getTitle());
                mPageNo = 1;
                switch (item.getItemId()) {
                    case R.id.filter_debuts:
                        list = Api.LIST_DEBUTS;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_playoffs:
                        list = Api.LIST_PLAYOFFS;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_rebounds:
                        list = Api.LIST_REBOUNDS;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_animated:
                        list = Api.LIST_ANIMATED;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_attachments:
                        list = Api.LIST_ATTACHMENTS;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_hot:
                        sort = Api.SORT_POPULARITY;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_recent:
                        sort = Api.SORT_RECENT;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_views:
                        sort = Api.SORT_VIEWS;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_comments:
                        sort = Api.SORT_COMMENTS;
                        request(Api.EVENT_BEGIN, true);
                        break;

                    default:

                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    private void request(int event, boolean isDia) {
        if (isDia) {
            if (mLoadingDialog == null) {
                mLoadingDialog = DialogHelp.getLoadingDialog(getActivity(), "正在加载...");
            }
            mLoadingDialog.show();
        }
        mPresenter.loadShotsList(mPageNo, list, timeframe, sort, event);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRefresh() {
        mPageNo = 1;
        request(Api.EVENT_REFRESH, false);
    }

    @Override
    public void onLoadMore() {
        mPageNo++;
        request(Api.EVENT_MORE, false);
    }

    @Override
    public void renderShotsList(List<ShotEntity> shotsList) {
        mStateLayout.showContentView();

        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }

        if (mAdapter == null) {
            mAdapter = new ShotsAdapter(shotsList);
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter
                    .OnRecyclerViewItemClickListener() {

                @Override
                public void onItemClick(View view, int position) {

                    ShotEntity shots = mAdapter.getItem(position);
                    Bundle bundle = new Bundle();
                    bundle.putLong("shotId", shots.getId());
                    startIntent(ShotDetailActivity.class, bundle);
                }
            });

            mXRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.removeAll();
            mAdapter.addData(shotsList);
        }
    }

    @Override
    public void renderMoreShotsList(List<ShotEntity> shotsList) {
        if (null != mAdapter) {
            mAdapter.addData(shotsList);
        }
        mXRecyclerView.loadMoreComplete();
    }

    @Override
    public void renderRefrshShotsList(List<ShotEntity> shotsList) {
        if (null != mAdapter) {
            List<ShotEntity> data = mAdapter.getData();
            data.clear();
            mAdapter.addData(shotsList);
        }
        mXRecyclerView.refreshComplete();
    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void onFailed(int event, String msg) {
        ToastHelper.shortToast(msg);
    }

    @Override
    public void onCompleted() {

    }


    @Override
    public void setPresenter(ShotsContract.Presenter presenter) {

    }
}
