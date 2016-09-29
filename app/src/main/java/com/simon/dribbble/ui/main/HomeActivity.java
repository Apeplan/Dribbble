package com.simon.dribbble.ui.main;

import android.content.Intent;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.simon.dribbble.DribbbleApp;
import com.simon.dribbble.GlobalConstant;
import com.simon.dribbble.R;
import com.simon.dribbble.ui.BaseActivity;
import com.simon.dribbble.ui.BasePresenter;
import com.simon.dribbble.ui.buckets.BucketsFragment;
import com.simon.dribbble.ui.projects.ProjectsFragment;
import com.simon.dribbble.ui.sample.SampleActivity;
import com.simon.dribbble.ui.shots.ShotsFragment;
import com.simon.dribbble.ui.team.TeamFragment;
import com.simon.dribbble.ui.user.FollowersFragment;
import com.simon.dribbble.ui.user.SettingsActivity;
import com.simon.dribbble.ui.user.UserLikesFragment;
import com.simon.dribbble.ui.user.UserShotsFragment;
import com.simon.dribbble.util.ImgLoadHelper;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/31 9:23
 */

public class HomeActivity extends BaseActivity {

    private long mBackPressedTime;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    private ImageView mImv_profile;
    private TextView mUserName;
    private TextView mTv_email;
    private ShotsFragment mShotsFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.dribbble);

        mFab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.getMenu().findItem(R.id.nav_dribbble).setChecked(true);
        mImv_profile = (ImageView) headerView.findViewById(R.id.imv_header);
        mUserName = (TextView) headerView.findViewById(R.id.tv_username);
        mTv_email = (TextView) headerView.findViewById(R.id.tv_email);

        setNavigationSwitchContent(navigationView);

        if (null == mShotsFragment) {
            mShotsFragment = ShotsFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,
                mShotsFragment).commit();


        String user_name = DribbbleApp.spHelper().getString(GlobalConstant.USER_NAME);
        String user_profile = DribbbleApp.spHelper().getString(GlobalConstant.USER_PROFILE);
        if ("".equals(user_name)) {
            user_name = "Simon.han";
            DribbbleApp.spHelper().put(GlobalConstant.USER_NAME, user_name);
        }
        if (TextUtils.isEmpty(user_profile)) {
            user_profile = GlobalConstant.profile_url;
            DribbbleApp.spHelper().put(GlobalConstant.USER_PROFILE, user_profile);
        }

        mUserName.setText(user_name);
        ImgLoadHelper.loadAvatar(user_profile, mImv_profile);
    }

    @Override
    protected void initEventAndData() {

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(HomeActivity.this, SampleActivity.class);
//                Intent intent = new Intent(HomeActivity.this, CreateShotActivity.class);
                startActivity(intent);

               /* Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

            }
        });

    }

    private void setNavigationSwitchContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
//                mMainPresenterImpl.switchNavigation(item.getItemId());
                int itemId = item.getItemId();
                item.setChecked(true);
                Fragment fragment = null;
                if (itemId == R.id.nav_dribbble) {
                    mToolbar.setTitle(item.getTitle());
                    fragment = ShotsFragment.newInstance();
                } else if (itemId == R.id.nav_following) {
                    mToolbar.setTitle("我的" + item.getTitle());
                    fragment = FollowersFragment.newInstance();
                } else if (itemId == R.id.nav_shots) {
                    mToolbar.setTitle("我的" + item.getTitle());
                    fragment = UserShotsFragment.newInstance();
                } else if (itemId == R.id.nav_buckets) {
                    mToolbar.setTitle("我的" + item.getTitle());
                    fragment = BucketsFragment.newInstance();
                } else if (itemId == R.id.nav_projects) {
                    mToolbar.setTitle("我的" + item.getTitle());
                    fragment = ProjectsFragment.newInstance();
                } else if (itemId == R.id.nav_team) {
                    mToolbar.setTitle("我的" + item.getTitle());
                    fragment = TeamFragment.newInstance();
                } else if (itemId == R.id.nav_favorite) {
                    mToolbar.setTitle("我的" + item.getTitle());
                    fragment = UserLikesFragment.newInstance();
                } else if (itemId == R.id.nav_dl) {
                    mToolbar.setTitle("我的" + item.getTitle());
                } else if (itemId == R.id.nav_settings) {
                    startIntent(SettingsActivity.class);
                }
                if (null != fragment) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,
                            fragment).commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        // 双击退出
        long curTime = SystemClock.uptimeMillis();
        if ((curTime - mBackPressedTime) < (3 * 1000)) {
            finish();
        } else {
            mBackPressedTime = curTime;
            Toast.makeText(this, R.string.double_click_exit, Toast.LENGTH_LONG).show();
        }
//            super.onBackPressed();
    }

}
