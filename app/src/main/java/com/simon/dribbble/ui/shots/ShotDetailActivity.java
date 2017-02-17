package com.simon.dribbble.ui.shots;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simon.agiledevelop.mvpframe.BaseActivity;
import com.simon.agiledevelop.state.StateView;
import com.simon.agiledevelop.utils.App;
import com.simon.agiledevelop.utils.ImgLoadHelper;
import com.simon.agiledevelop.utils.ScreenHelper;
import com.simon.agiledevelop.utils.ToastHelper;
import com.simon.dribbble.R;
import com.simon.dribbble.data.Api;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.model.User;
import com.simon.dribbble.listener.TextWatcherImpl;
import com.simon.dribbble.ui.baselist.ListActivity;
import com.simon.dribbble.util.DateTimeUtil;
import com.simon.dribbble.util.DialogHelp;
import com.simon.dribbble.util.StringUtil;
import com.simon.dribbble.widget.TagGroup;
import com.simon.dribbble.widget.loadingdia.SpotsDialog;

import java.io.File;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/31 17:44
 */

public class ShotDetailActivity extends BaseActivity<ShotDetailPresenter> implements
        ShotDetailContract.View {

    private Toolbar mToolbar;
    private ImageView mDetailPic;// 详情大图
    private ImageView mImv_avatar;// 头像
    private TextView mTv_author;// 创建者
    private TextView mTv_time;// 创建时间
    private TextView mTv_views_count; // 查看次数
    private TextView mTv_comments_count;// 评论数
    private TextView mTv_likes_count;// 喜欢数
    private TextView mTv_attachments_count;// 电子邮件发送数
    private TextView mTv_buckets_count;// buckets 数量
    private TextView mTv_shot_title;// Shot 标题
    private TextView mTv_shot_desc;// Shot 描述
    private LinearLayout mLl_tags; // Shot 标签
    private TagGroup mTag_group;
    private ShotDetailPresenter mPresenter;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private EditText mEt_comment;
    private ImageView mAdd_comment;
    private long mShotId;
    private int mCommCount;
    private String mImgUrl;
    private String mTitle;
    private FloatingActionButton mFab;
    private SpotsDialog mLoadingDialog;
    private int mLike_count;
    private int mAttach_count;
    private int mBuckets_count;
    private ShotEntity mShot;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_shot_detail;
    }

    @Override
    protected ShotDetailPresenter getPresenter() {
        return null;
    }

    @Override
    protected StateView getLoadingView() {
        return (StateView) findViewById(R.id.stateView_detail);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mDetailPic = (ImageView) findViewById(R.id.imv_detail_pic);
        mImv_avatar = (ImageView) findViewById(R.id.imv_avatar);
        mTv_author = (TextView) findViewById(R.id.tv_author);
        mTv_time = (TextView) findViewById(R.id.tv_time);
        mTv_views_count = (TextView) findViewById(R.id.tv_views_count);
        mTv_comments_count = (TextView) findViewById(R.id.tv_comments_count);
        mTv_likes_count = (TextView) findViewById(R.id.tv_likes_count);
        mTv_attachments_count = (TextView) findViewById(R.id.tv_attachments_count);
        mTv_buckets_count = (TextView) findViewById(R.id.tv_buckets_count);
        mTv_shot_title = (TextView) findViewById(R.id.tv_shot_title);
        mTv_shot_desc = (TextView) findViewById(R.id.tv_shot_desc);
        mEt_comment = (EditText) findViewById(R.id.et_add_comment);
        mAdd_comment = (ImageView) findViewById(R.id.imv_add_comment);
        mAdd_comment.setEnabled(false);

        mLl_tags = (LinearLayout) findViewById(R.id.ll_tags);
        mTag_group = (TagGroup) findViewById(R.id.tag_group);

        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mPresenter = new ShotDetailPresenter(this);
    }

    @Override
    protected void initEventAndData() {

        mEt_comment.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    mAdd_comment.setEnabled(true);
                } else {
                    mAdd_comment.setEnabled(false);
                }
            }
        });

        mTv_comments_count.setOnClickListener(this);
        mTv_likes_count.setOnClickListener(this);
        mTv_attachments_count.setOnClickListener(this);
        mTv_buckets_count.setOnClickListener(this);
        mDetailPic.setOnClickListener(this);
        mFab.setOnClickListener(this);
        mAdd_comment.setOnClickListener(this);

        Bundle bundle = getBundle();
        if (null != bundle) {
            mShotId = bundle.getLong("shotId");
            mPresenter.loadShot(Api.EVENT_BEGIN, mShotId);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.tv_comments_count) {
            Bundle bundle = new Bundle();
            bundle.putString("type", "comm");
            bundle.putLong("shotId", mShotId);
            bundle.putInt("count", mCommCount);
            startIntent(CommentActivity.class, bundle);
        } else if (id == R.id.tv_likes_count) {
            Bundle bundle = new Bundle();
            bundle.putString("type", "likes");
            bundle.putLong("shotId", mShotId);
            bundle.putInt("count", mLike_count);
            startIntent(ListActivity.class, bundle);

        } else if (id == R.id.tv_attachments_count) {
            Bundle bundle = new Bundle();
            bundle.putString("type", "attachments");
            bundle.putLong("shotId", mShotId);
            bundle.putInt("count", mAttach_count);
            startIntent(ListActivity.class, bundle);
        } else if (id == R.id.tv_buckets_count) {
            Bundle bundle = new Bundle();
            bundle.putString("type", "buckets");
            bundle.putLong("shotId", mShotId);
            bundle.putInt("count", mBuckets_count);
            startIntent(ListActivity.class, bundle);
        } else if (id == R.id.imv_detail_pic) {
            Bundle bundle = new Bundle();
            bundle.putString("shotImg", mImgUrl);
            bundle.putString("title", mTitle);
            startIntent(DetailBigPicActivity.class, bundle);

        } else if (id == R.id.fab) {
            mPresenter.addLike(mShotId);
        } else if (id == R.id.imv_add_comment) {
            String s = "<p>" + mEt_comment.getText().toString() + "</P>";
            mPresenter.sendComment(mShotId, s);
        }
    }

    @Override
    public void showShot(ShotEntity shot) {

        hideDialog();

        mShot = shot;
        showContent();
        mTitle = shot.getTitle();
        mCollapsingToolbarLayout.setTitle(mTitle);
        String normal = shot.getImages().getNormal();
        String hdpi = shot.getImages().getHidpi();
        mImgUrl = StringUtil.isEmpty(hdpi) ? normal : hdpi;

        User user = shot.getUser();
        ImgLoadHelper.loadAvatar(user.avatar_url, mImv_avatar);
        mTv_author.setText(user.name);
        String created_at = shot.getCreated_at();
        String time = DateTimeUtil.formatUTC(created_at);
        String s = DateTimeUtil.friendly_time(time);

        mTv_time.setText(s);
        // attachments_count、comments_count、buckets_count、rebounds_count、likes_count
        mTv_views_count.setText(shot.getViews_count() + "");

        mCommCount = shot.getComments_count();
        mLike_count = shot.getLikes_count();
        mAttach_count = shot.getAttachments_count();
        mBuckets_count = shot.getBuckets_count();

        mTv_comments_count.setText(mCommCount + "");
        mTv_likes_count.setText(mLike_count + "");
        mTv_attachments_count.setText(mAttach_count + "");
        mTv_buckets_count.setText(mBuckets_count + "");

        mTv_comments_count.setVisibility(mCommCount == 0 ? View.GONE : View.VISIBLE);
        mTv_likes_count.setVisibility(mLike_count == 0 ? View.GONE : View.VISIBLE);
        mTv_attachments_count.setVisibility(mAttach_count == 0 ? View.GONE : View.VISIBLE);
        mTv_buckets_count.setVisibility(mBuckets_count == 0 ? View.GONE : View.VISIBLE);

        mTv_shot_title.setText(shot.getTitle());
        String description = shot.getDescription();
        if (!StringUtil.isEmpty(description)) {
            mTv_shot_desc.setText(Html.fromHtml(description));
        }
        String[] tags = shot.getTags();
        if (null != tags && tags.length > 0) {
            mLl_tags.setVisibility(View.VISIBLE);
            mTag_group.setTags(tags);
        } else {
            mLl_tags.setVisibility(View.GONE);
        }

        int width = ScreenHelper.getScreenWidth(App.INSTANCE);
        int height = width * 3 / 4;

        ImgLoadHelper.imageW_H(mImgUrl, mDetailPic, width, height, R.drawable.dribbble_p);

    }

    @Override
    public void onEmpty(String msg) {
        showEmtry(msg, null);
    }

    @Override
    public void showLoading(int action, String msg) {
        if (Api.EVENT_BEGIN == action) {
            showDialog();
        }
    }

    @Override
    public void onFailed(final int flag, String msg) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadShot(flag, mShotId);
            }
        };
        if (Api.EVENT_BEGIN == flag && msg.contains("网络")) {
            showNetworkError(msg, onClickListener);
        } else {
            showError(msg, onClickListener);
        }
    }

    @Override
    public void onCompleted(int action) {

    }

    @Override
    public void setPresenter(ShotDetailPresenter presenter) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            shareMsg("ShotDetailActivity", mTitle, "", mImgUrl);
            return true;
        } else if (id == R.id.action_add) {
            ToastHelper.showLongToast(App.INSTANCE, item.getTitle() + "正在开发中");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 分享功能
     *
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public void shareMsg(String activityTitle, String msgTitle, String msgText, String imgPath) {

        String fileName = mTitle;
//        fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        File renamed = new File(imgPath, fileName);
        Uri uri = Uri.fromFile(renamed);
        ShareCompat.IntentBuilder.from(this)
                .setText(getShareText())
                .setType(getImageMimeType(fileName))
                .setSubject(mTitle)
                .setStream(uri)
                .startChooser();
    }

    private String getShareText() {
        return new StringBuilder()
                .append("“")
                .append(mShot.getTitle())
                .append("” by ")
                .append(mShot.getUser().name)
                .append("\n")
                .append(mShot.getImages().getNormal())
                .toString();
    }

    private String getImageMimeType(@NonNull String fileName) {
        if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        }
        return "image/jpeg";
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

    @Override
    public void onBackPressed() {
        mPresenter.detachView(false);
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        super.onBackPressed();
    }
}
