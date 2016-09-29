package com.simon.dribbble.ui.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.simon.dribbble.R;
import com.simon.dribbble.data.model.UserEntity;
import com.simon.dribbble.ui.BaseActivity;
import com.simon.dribbble.util.ImgLoadHelper;
import com.simon.dribbble.widget.statelayout.StateLayout;

/**
 * Created by Simon Han on 2016/9/17.
 */

public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract
        .View {

    private ImageView mImv_avatar;
    private TextView mTv_username;
    private TextView mTv_user_bio;
    private TextView mTv_user_loc;
    private TextView mTv_user_web;
    private TextView mTv_user_twitter;
    private TextView mTv_user_buckets;
    private TextView mTv_user_followers;
    private TextView mTv_user_followings;
    private TextView mTv_user_likes;
    private StateLayout mStateLayout;

    @Override
    protected int getLayout() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected UserInfoPresenter getPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String name = getBundle().getString("name");
        setCommonBackToolBack(toolbar, name);

        mStateLayout = (StateLayout) findViewById(R.id.stateLayout_userinfo);

        mImv_avatar = (ImageView) findViewById(R.id.imv_avatar);
        mTv_username = (TextView) findViewById(R.id.tv_username);
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
            mStateLayout.showProgressView();
            mPresenter.loadUserInfo(userId);
        }
    }

    @Override
    public void showUserInfo(UserEntity user) {
        mStateLayout.showContentView();

        ImgLoadHelper.loadAvatar(user.avatar_url, mImv_avatar);
        mTv_username.setText(user.name);
        mTv_user_bio.setText(user.bio);
        mTv_user_loc.setText(user.location);

    }

    @Override
    public void onEmpty() {
        mStateLayout.showContentView();
    }

    @Override
    public void onFailed(int action, String msg) {
        mStateLayout.showContentView();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void setPresenter(UserInfoContract.Presenter presenter) {

    }
}
