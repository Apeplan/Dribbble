package com.simon.dribbble.ui.shots;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.simon.agiledevelop.BaseFragment;
import com.simon.agiledevelop.log.LLog;
import com.simon.agiledevelop.recycler.adapter.RapidAdapter;
import com.simon.agiledevelop.utils.App;
import com.simon.agiledevelop.utils.ToastHelper;
import com.simon.dribbble.R;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.remote.DribbbleService;
import com.simon.dribbble.ui.user.UserInfoActivity;
import com.simon.dribbble.util.DialogHelp;
import com.simon.dribbble.widget.loadingdia.SpotsDialog;
import com.simon.dribbble.widget.statelayout.StateLayout;

import java.util.List;


public class ShotsFragment extends BaseFragment<ShotsPresenter> implements ShotsContract.View,
        RapidAdapter.LoadMoreListener, ShotsAdapter.ShotClickListener {

    private int mPageNo = 1;
    private StateLayout mStateLayout;
    private RecyclerView mRecyclerView;
    private ShotsAdapter mAdapter;
    private ShotsPresenter mPresenter;
    private
    @DribbbleService.ShotType
    String list = "";
    private
    @DribbbleService.ShotTimeframe
    String timeframe = "";
    private
    @DribbbleService.ShotSort
    String sort = "";
    private SpotsDialog mLoadingDialog;
    private SwipeRefreshLayout mRefreshLayout;

    public static ShotsFragment newInstance() {
        ShotsFragment fragment = new ShotsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shots;
    }

    @Override
    protected ShotsPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected View getLoadingView() {
        return null;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        setHasOptionsMenu(true);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRefreshLayout.setColorSchemeResources(R.color.purple_500, R.color.blue_500, R.color
                .orange_500, R.color.pink_500);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                request(Api.EVENT_REFRESH, false);
            }
        });

        mStateLayout = (StateLayout) view.findViewById(R.id.progress_loading);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.xrv_shots);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        if (mAdapter == null) {
            mAdapter = new ShotsAdapter();
            mAdapter.openAnimation(RapidAdapter.SCALEIN);
            mAdapter.setLoadMoreEnable(true);
            mAdapter.setOnLoadMoreListener(this);
            mAdapter.setShotClickListener(this);
        }

        mPresenter = new ShotsPresenter(this);
    }

    @Override
    protected void initEventAndData() {
//        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            protected void onItemClick(RapidAdapter adapter, RecyclerView recyclerView, View
//                    view, int position) {
//                ShotEntity shots = mAdapter.getItem(position);
//                Bundle bundle = new Bundle();
//                bundle.putLong("shotId", shots.getId());
//                startIntent(ShotDetailActivity.class, bundle);
//            }
//        });

        mPageNo = 1;
        request(Api.EVENT_BEGIN, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        mPageNo = 1;
        switch (item.getItemId()) {
            case R.id.action_current:
                timeframe = DribbbleService.SHOT_TIMEFRAME_NOW;
                request(Api.EVENT_BEGIN, true);
                break;
            case R.id.action_week:
                timeframe = DribbbleService.SHOT_TIMEFRAME_WEEK;
                request(Api.EVENT_BEGIN, true);
                break;
            case R.id.action_month:
                timeframe = DribbbleService.SHOT_TIMEFRAME_MONTH;
                request(Api.EVENT_BEGIN, true);
                break;
            case R.id.action_year:
                timeframe = DribbbleService.SHOT_TIMEFRAME_YEAR;
                request(Api.EVENT_BEGIN, true);
                break;
            case R.id.action_ever:
                timeframe = DribbbleService.SHOT_TIMEFRAME_EVER;
                request(Api.EVENT_BEGIN, true);
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
                mPageNo = 1;
                switch (item.getItemId()) {
                    case R.id.filter_debuts:
                        list = DribbbleService.SHOT_TYPE_DEBUTS;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_playoffs:
                        list = DribbbleService.SHOT_TYPE_PLAYOFFS;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_rebounds:
                        list = DribbbleService.SHOT_TYPE_REBOUNDS;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_animated:
                        list = DribbbleService.SHOT_TYPE_ANIMATED;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_attachments:
                        list = DribbbleService.SHOT_TYPE_ATTACHMENTS;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_hot:
                        sort = DribbbleService.SHOT_SORT_POPULARITY;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_recent:
                        sort = DribbbleService.SHOT_SORT_RECENT;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_views:
                        sort = DribbbleService.SHOT_SORT_VIEWS;
                        request(Api.EVENT_BEGIN, true);
                        break;
                    case R.id.filter_comments:
                        sort = DribbbleService.SHOT_SORT_COMMENTS;
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
            showDialog();
        }
        mPresenter.loadShotsList(mPageNo, list, timeframe, sort, event);
    }

    @Override
    public void shotClick(ShotEntity shot, int type) {
        if (type == 0) {
            long id = shot.getUser().id;
            String name = shot.getUser().name;
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putLong("userId", id);
            startIntent(UserInfoActivity.class, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong("shotId", shot.getId());
            startIntent(ShotDetailActivity.class, bundle);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void renderShotsList(List<ShotEntity> shotsList) {
        mStateLayout.showContentView();
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(shotsList);

    }

    @Override
    public void renderMoreShotsList(List<ShotEntity> shotsList) {
        if (null != mAdapter) {
            mAdapter.appendData(shotsList);
        }
        mAdapter.loadComplete();
    }

    @Override
    public void renderRefrshShotsList(List<ShotEntity> shotsList) {
        if (null != mAdapter) {
            List<ShotEntity> data = mAdapter.getData();
            data.clear();
            mAdapter.setNewData(shotsList);
        }
        mRefreshLayout.setRefreshing(false);
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
    public void onFailed(int event, String msg) {
        ToastHelper.showLongToast(App.INSTANCE, msg);
    }

    @Override
    public void onCompleted(int action) {
        LLog.d("请求完成: ");
    }

    @Override
    public void setPresenter(ShotsPresenter presenter) {

    }

    @Override
    public void onLoadMore() {
        mPageNo++;
        request(Api.EVENT_MORE, false);
    }

    private void showDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogHelp.getLoadingDialog(getActivity(), "正在加载...");
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
