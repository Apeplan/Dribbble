package com.simon.dribbble.ui.shots;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.simon.dribbble.R;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.remote.DribbbleApi;
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
    private
    @DribbbleApi.ShotType
    String list = "";
    private
    @DribbbleApi.ShotTimeframe
    String timeframe = "";
    private
    @DribbbleApi.ShotSort
    String sort = "";
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
        mXRecyclerView.setHasFixedSize(true);
        if (mAdapter == null) {
            mAdapter = new ShotsAdapter();
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
        }

        mPresenter = new ShotsPresenter(this);

        mXRecyclerView.setLoadingListener(this);
    }

    @Override
    protected void initEventAndData() {
        mStateLayout.showProgressView();
        mPageNo = 1;
        request(DribbbleApi.EVENT_BEGIN, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        mPageNo = 1;
        switch (item.getItemId()) {
            case R.id.action_current:
                timeframe = DribbbleApi.SHOT_TIMEFRAME_NOW;
                request(DribbbleApi.EVENT_BEGIN, true);
                break;
            case R.id.action_week:
                timeframe = DribbbleApi.SHOT_TIMEFRAME_WEEK;
                request(DribbbleApi.EVENT_BEGIN, true);
                break;
            case R.id.action_month:
                timeframe = DribbbleApi.SHOT_TIMEFRAME_MONTH;
                request(DribbbleApi.EVENT_BEGIN, true);
                break;
            case R.id.action_year:
                timeframe = DribbbleApi.SHOT_TIMEFRAME_YEAR;
                request(DribbbleApi.EVENT_BEGIN, true);
                break;
            case R.id.action_ever:
                timeframe = DribbbleApi.SHOT_TIMEFRAME_EVER;
                request(DribbbleApi.EVENT_BEGIN, true);
                break;
            case R.id.menu_search:
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
                        list = DribbbleApi.SHOT_TYPE_DEBUTS;
                        request(DribbbleApi.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_playoffs:
                        list = DribbbleApi.SHOT_TYPE_PLAYOFFS;
                        request(DribbbleApi.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_rebounds:
                        list = DribbbleApi.SHOT_TYPE_REBOUNDS;
                        request(DribbbleApi.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_animated:
                        list = DribbbleApi.SHOT_TYPE_ANIMATED;
                        request(DribbbleApi.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_attachments:
                        list = DribbbleApi.SHOT_TYPE_ATTACHMENTS;
                        request(DribbbleApi.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_hot:
                        sort = DribbbleApi.SHOT_SORT_POPULARITY;
                        request(DribbbleApi.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_recent:
                        sort = DribbbleApi.SHOT_SORT_RECENT;
                        request(DribbbleApi.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_views:
                        sort = DribbbleApi.SHOT_SORT_VIEWS;
                        request(DribbbleApi.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_comments:
                        sort = DribbbleApi.SHOT_SORT_COMMENTS;
                        request(DribbbleApi.EVENT_BEGIN, true);
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
        request(DribbbleApi.EVENT_REFRESH, false);
    }

    @Override
    public void onLoadMore() {
        mPageNo++;
        request(DribbbleApi.EVENT_MORE, false);
    }

    @Override
    public void renderShotsList(List<ShotEntity> shotsList) {
        mStateLayout.showContentView();

        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }

        mAdapter.removeAll();
        mAdapter.addData(shotsList);

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
