package com.simon.dribbble.ui.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simon.agiledevelop.mvpframe.BaseActivity;
import com.simon.agiledevelop.mvpframe.RxPresenter;
import com.simon.agiledevelop.state.StateView;
import com.simon.agiledevelop.utils.ImgLoadHelper;
import com.simon.dribbble.R;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.User;
import com.simon.dribbble.util.DialogHelp;
import com.simon.dribbble.widget.loadingdia.SpotsDialog;

/**
 * Created by Simon Han on 2016/9/17.
 */

public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract
        .View {

    private ImageView mImv_avatar;
    private TextView mTv_username;
    private TextView mTv_user_bio;
    private TextView mTv_bio;
    private TextView mTv_user_loc;
    private TextView mTv_user_web;
    private TextView mTv_user_twitter;
    private TextView mTv_user_buckets;
    private TextView mTv_user_followers;
    private TextView mTv_user_followings;
    private TextView mTv_user_likes;

    private SpotsDialog mLoadingDialog;
    private TextView mUserType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected UserInfoPresenter getPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected StateView getLoadingView() {
        return (StateView) findViewById(R.id.stateView_userinfo);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String name = getBundle().getString("name");
        setCommonBackToolBack(toolbar, name);

        mImv_avatar = (ImageView) findViewById(R.id.imv_avatar);
        mTv_username = (TextView) findViewById(R.id.tv_username);
        mUserType = (TextView) findViewById(R.id.tv_usertype);
        mTv_bio = (TextView) findViewById(R.id.tv_bio);
        mTv_user_bio = (TextView) findViewById(R.id.tv_user_bio);
        mTv_user_loc = (TextView) findViewById(R.id.tv_user_loc);
        mTv_user_web = (TextView) findViewById(R.id.tv_user_web);
        mTv_user_twitter = (TextView) findViewById(R.id.tv_user_twitter);
        mTv_user_buckets = (TextView) findViewById(R.id.tv_user_buckets);
        mTv_user_followers = (TextView) findViewById(R.id.tv_user_followers);
        mTv_user_followings = (TextView) findViewById(R.id.tv_user_followings);
        mTv_user_likes = (TextView) findViewById(R.id.tv_user_likes);
    }

    @Override
    protected void initEventAndData() {
        Bundle bundle = getBundle();
        if (null != bundle) {
            long userId = bundle.getLong("userId");
            mPresenter.loadUserInfo(Api.EVENT_BEGIN, userId);
        }
    }

    @Override
    public void showUserInfo(User user) {
        showContent();
        hideDialog();

        ImgLoadHelper.loadAvatar(user.avatar_url, mImv_avatar);
        mTv_username.setText(user.name);
        mUserType.setText(user.type);

        String location = user.location;
        if (TextUtils.isEmpty(location)) {
            mTv_user_loc.setVisibility(View.GONE);
        } else {
            mTv_user_loc.setVisibility(View.VISIBLE);
            mTv_user_loc.setText(location);
        }

        String web = user.links.get("web");
        if (TextUtils.isEmpty(web)) {
            mTv_user_web.setVisibility(View.GONE);
        } else {
            mTv_user_web.setVisibility(View.VISIBLE);
            mTv_user_web.setText(web);
        }

        mTv_user_buckets.setText(user.buckets_count + " 作品");
        mTv_user_followers.setText(user.followers_count + " 粉丝");
        mTv_user_followings.setText(user.followings_count + " 关注");
        mTv_user_likes.setText(user.likes_count + " 喜欢");

        Spanned fromHtml = Html.fromHtml(user.bio);
        if (TextUtils.isEmpty(fromHtml)) {
            mTv_bio.setVisibility(View.GONE);
            mTv_user_bio.setVisibility(View.GONE);
        } else {
            mTv_bio.setVisibility(View.VISIBLE);
            mTv_user_bio.setVisibility(View.VISIBLE);
            mTv_user_bio.setText(fromHtml);
        }

    }

    @Override
    public void onEmpty(String msg) {
        showEmtry(msg, null);
        hideDialog();
    }

    @Override
    public void showLoading(int action, String msg) {
        if (Api.EVENT_BEGIN == action) {
            showDialog();
        }
    }

    @Override
    public void onFailed(int action, String msg) {
        showError(msg, null);
        hideDialog();
    }

    @Override
    public void onCompleted(int action) {

    }

    @Override
    public void setPresenter(RxPresenter presenter) {

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
