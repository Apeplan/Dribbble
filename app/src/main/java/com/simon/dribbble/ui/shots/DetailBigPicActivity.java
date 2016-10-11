package com.simon.dribbble.ui.shots;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.simon.dribbble.R;
import com.simon.dribbble.ui.BaseActivity;
import com.simon.dribbble.ui.BasePresenter;
import com.simon.dribbble.util.CheckHelper;
import com.simon.dribbble.util.ImgLoadHelper;
import com.simon.dribbble.util.ToastHelper;
import com.simon.dribbble.util.UIUtils;
import com.simon.dribbble.widget.PullBackLayout;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/2 9:37
 */

public class DetailBigPicActivity extends BaseActivity implements PullBackLayout.Callback {

    private Toolbar mToolbar;
    private PhotoView mPhotoView;
    private PullBackLayout mPullBackLayout;

    private ColorDrawable mBackground;
    private boolean mIsToolBarHidden;
    private boolean mIsStatusBarHidden;

    @Override
    protected int getLayout() {
        return R.layout.activity_detai_bigpic;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPullBackLayout.setCallback(this);
        initLazyLoadView();
    }

    private void initLazyLoadView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    showToolBarAndPhotoTouchView();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        } else {
            showToolBarAndPhotoTouchView();
        }
    }

    private void showToolBarAndPhotoTouchView() {
        toolBarFadeIn();
        ImgLoadHelper.loadImage(getBundle().getString("shotImg"), mPhotoView);
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setCommonBackToolBack(mToolbar, "大图");

        mPullBackLayout = (PullBackLayout) findViewById(R.id.pull_back_layout);
        mPhotoView = (PhotoView) findViewById(R.id.photo_touch_iv);

        mBackground = new ColorDrawable(Color.BLACK);
        UIUtils.getRootView(this).setBackgroundDrawable(mBackground);

    }

    @Override
    protected void initEventAndData() {

        setPhotoViewClickEvent();
        Bundle bundle = getBundle();
        if (null != bundle) {
            String shotImg = bundle.getString("shotImg");
            String title = bundle.getString("title");
            mToolbar.setTitle(title);
            if (!CheckHelper.isEmpty(shotImg)) {
                ImgLoadHelper.loadImage(shotImg, mPhotoView);
            }
        }
    }

    private void setPhotoViewClickEvent() {

        mPhotoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                hideOrShowToolbar();
                hideOrShowStatusBar();
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_big_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                ToastHelper.shortToast(getString(R.string.toast_def_hint));
                return true;
            case R.id.action_save:
                ToastHelper.shortToast(getString(R.string.toast_def_hint));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPullStart() {
        toolBarFadeOut();

        mIsStatusBarHidden = true;
        hideOrShowStatusBar();
    }

    @Override
    public void onPull(float progress) {
        mBackground.setAlpha((int) (0xff/*255*/ * (1f - progress)));
    }

    @Override
    public void onPullCancel() {
        toolBarFadeIn();
    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }

    private void toolBarFadeOut() {
        mIsToolBarHidden = false;
        hideOrShowToolbar();
    }

    private void toolBarFadeIn() {
        mIsToolBarHidden = true;
        hideOrShowToolbar();
    }

    protected void hideOrShowToolbar() {
        mToolbar.animate()
                .alpha(mIsToolBarHidden ? 1.0f : 0.0f)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsToolBarHidden = !mIsToolBarHidden;
    }

    private void hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
            UIUtils.enter(DetailBigPicActivity.this);
        } else {
            UIUtils.exit(DetailBigPicActivity.this);
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }
}
