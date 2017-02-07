package com.simon.agiledevelop.state;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.simon.agiledevelop.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * describe:
 *
 * @author Apeplan
 * @date 2017/2/7
 * @email hanzx1024@gmail.com
 */

public class StateView extends FrameLayout {

    public static final int STATE_CONTENT = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_UNAUTH = 4;

    public static final int DEFAULT_STATE = STATE_CONTENT;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            STATE_CONTENT,
            STATE_LOADING,
            STATE_ERROR,
            STATE_EMPTY,
            STATE_UNAUTH
    })
    public @interface State {
    }

    private View mContentView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mUnauthView;

    private LayoutInflater mInflater;

    private Drawable mErrorDrawable;
    private Drawable mEmptyDrawable;
    private Drawable mUnauthDrawable;

    @State
    private int mState;


    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(getContext());
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StateView);
        try {

            // 加载中视图布局
            int loadingViewResId = ta.getResourceId(R.styleable.StateView_loadingView_id, -1);
            if (loadingViewResId > -1) {
                mLoadingView = mInflater.inflate(loadingViewResId, this, false);
                addView(mLoadingView);
            }
            // 错误视图布局
            int errorViewResId = ta.getResourceId(R.styleable.StateView_errorView_id, -1);
            if (errorViewResId > -1) {
                mErrorView = mInflater.inflate(errorViewResId, this, false);
                addView(mErrorView);
            }
            // 空视图布局
            int emptyViewResId = ta.getResourceId(R.styleable.StateView_emptyView_id, -1);
            if (emptyViewResId > -1) {
                mEmptyView = mInflater.inflate(emptyViewResId, this, false);
                addView(mEmptyView);
            }
            // 未登录视图布局
            int unauthViewResId = ta.getResourceId(R.styleable.StateView_unauthView_id, -1);
            if (unauthViewResId > -1) {
                mUnauthView = mInflater.inflate(unauthViewResId, this, false);
                addView(mUnauthView);
            }

            mErrorDrawable = ta.getDrawable(R.styleable.StateView_error_drawable);
            mEmptyDrawable = ta.getDrawable(R.styleable.StateView_empty_drawable);
            mUnauthDrawable = ta.getDrawable(R.styleable.StateView_unauth_drawable);

            // 默认状态.由于mViewState变量使用了注解限制,所以不能直接赋值
            int viewState = ta.getInt(R.styleable.StateView_viewState, DEFAULT_STATE);
            switch (viewState) {
                case STATE_CONTENT:
                    mState = STATE_CONTENT;
                    break;
                case STATE_LOADING:
                    mState = STATE_LOADING;
                    break;
                case STATE_ERROR:
                    mState = STATE_ERROR;
                    break;
                case STATE_EMPTY:
                    mState = STATE_EMPTY;
                    break;
                default:
                    // nothing to do
                    break;
            }

        } finally {
            ta.recycle();
        }

        initDefaultViews();
    }

    /**
     * 初始化各个状态对应的默认视图
     */
    private void initDefaultViews() {
        if (mLoadingView == null) {
            mLoadingView = mInflater.inflate(R.layout.view_progress, this, false);
            addView(mLoadingView);
        }
        if (mErrorView == null) {
            mErrorView = mInflater.inflate(R.layout.view_error, this, false);
            if (null != mErrorDrawable) {
                ImageView icon = (ImageView) mErrorView.findViewById(R.id.imv_state_view_icon);
                icon.setImageDrawable(mErrorDrawable);
            }
            addView(mErrorView);
        }
        if (mEmptyView == null) {
            mEmptyView = mInflater.inflate(R.layout.view_empty, this, false);
            if (null != mEmptyDrawable) {
                ImageView icon = (ImageView) mEmptyView.findViewById(R.id.imv_state_view_icon);
                icon.setImageDrawable(mEmptyDrawable);
            }
            addView(mEmptyView);
        }
//        if (mUnauthView == null) {
//            mUnauthView = mInflater.inflate(R.layout.view_unauth, this, false);
//            if (null != mUnauthDrawable) {
//                ImageView icon = (ImageView) mUnauthView.findViewById(R.id.imv_state_view_icon);
//                icon.setImageDrawable(mUnauthDrawable);
//            }
//            addView(mUnauthView);
//        }
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if (isValidContentView(child)) {
            mContentView = child;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mContentView == null) {
            throw new IllegalArgumentException("Content view must not null!");
        }

        setView();
    }

    /**
     * 切换显示的状态视图的逻辑
     */
    private void setView() {
        switch (mState) {
            case STATE_CONTENT:
                mContentView.setVisibility(View.VISIBLE);
                mLoadingView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.GONE);
                if (mUnauthView != null) {
                    mUnauthView.setVisibility(View.GONE);
                }
                break;
            case STATE_LOADING:
                mContentView.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.VISIBLE);
                mErrorView.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.GONE);
                if (mUnauthView != null) {
                    mUnauthView.setVisibility(View.GONE);
                }
                break;
            case STATE_ERROR:
                mContentView.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.VISIBLE);
                mEmptyView.setVisibility(View.GONE);
                if (mUnauthView != null) {
                    mUnauthView.setVisibility(View.GONE);
                }
                break;
            case STATE_EMPTY:
                mContentView.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.VISIBLE);
                if (mUnauthView != null) {
                    mUnauthView.setVisibility(View.GONE);
                }
                break;
            case STATE_UNAUTH:
                mContentView.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.GONE);
                if (mUnauthView != null) {
                    mUnauthView.setVisibility(View.VISIBLE);
                }
                break;
            default:
                // nothing to do
                break;
        }
    }

    /**
     * 检测是否是Content View
     *
     * @param view
     * @return
     */
    private boolean isValidContentView(View view) {
        if (mContentView != null && mContentView != view) {
            return false;
        }

        return view != mLoadingView && view != mErrorView
                && view != mEmptyView && view != mUnauthView;
    }

    @Nullable
    public View getView(@State int state) {
        switch (state) {
            case STATE_CONTENT:
                return mContentView;
            case STATE_LOADING:
                return mLoadingView;
            case STATE_ERROR:
                return mErrorView;
            case STATE_EMPTY:
                return mEmptyView;
            default:
                return null;
        }
    }

    /**
     * 设置指定状态的视图
     *
     * @param view
     * @param state
     * @param switchToState
     */
    public StateView setViewForState(View view, @State int state, boolean switchToState) {
        switch (state) {
            case STATE_CONTENT:
                if (mContentView != null) {
                    removeView(mContentView);
                }
                mContentView = view;
                addView(mContentView);
                break;
            case STATE_LOADING:
                if (mLoadingView != null) {
                    removeView(mLoadingView);
                }
                mLoadingView = view;
                addView(mLoadingView);
                break;
            case STATE_ERROR:
                if (mErrorView != null) {
                    removeView(mErrorView);
                }
                mErrorView = view;
                addView(mErrorView);
                break;
            case STATE_EMPTY:
                if (mEmptyView != null) {
                    removeView(mEmptyView);
                }
                mEmptyView = view;
                addView(mEmptyView);
                break;
            case STATE_UNAUTH:
                if (mUnauthView != null) {
                    removeView(mUnauthView);
                }
                mUnauthView = view;
                addView(mUnauthView);
                break;
            default:
                // nothing to do
                break;
        }
        setView();
        // 是否立即切换状态
        if (switchToState) {
            setState(state);
        }

        return this;
    }

    /**
     * 设置指定状态的视图
     *
     * @param view
     * @param state
     */
    public StateView setViewForState(View view, @State int state) {
        return setViewForState(view, state, false);
    }

    /**
     * 设置指定状态的视图
     *
     * @param layoutRes
     * @param state
     * @param switchToState
     */
    public StateView setViewForState(@LayoutRes int layoutRes, @State int state, boolean
            switchToState) {
        View view = mInflater.inflate(layoutRes, this, false);
        return setViewForState(view, state, switchToState);
    }

    /**
     * 设置指定状态的视图
     *
     * @param layoutRes
     * @param state
     */
    public StateView setViewForState(@LayoutRes int layoutRes, @State int state) {
        return setViewForState(layoutRes, state, false);
    }

    /**
     * 获取当前的状态
     *
     * @return
     */
    @State
    public int getState() {
        return mState;
    }

    /**
     * 切换显示的状态
     *
     * @param state
     * @return
     */
    public StateView setState(@State int state) {
        if (state != mState) {
            mState = state;
            setView();
        }
        return this;
    }

    /**
     * 设置当前状态视图的图标
     *
     * @param iconResId
     * @return
     */
    public StateView setIcon(@DrawableRes int iconResId) {
        View view = getView(mState);
        if (view != null) {
            ImageView icon = (ImageView) view.findViewById(R.id.imv_state_view_icon);
            if (icon != null) {
                icon.setImageResource(iconResId);
            } else {
                throw new IllegalArgumentException("view must have an id of named icon");
            }
        }

        return this;
    }

    /**
     * 设置当前状态视图的标题
     *
     * @param titleResId
     * @return
     */
    public StateView setTitle(@StringRes int titleResId) {
        return setTitle(getContext().getString(titleResId));
    }

    /**
     * 设置当前状态视图的标题
     *
     * @param title
     * @return
     */
    public StateView setTitle(String title) {
        View view = getView(mState);
        if (view != null) {
            TextView titleTxt = (TextView) view.findViewById(R.id.tv_state_view_title);
            if (titleTxt != null) {
                titleTxt.setVisibility(View.VISIBLE);
                titleTxt.setText(title);
            } else {
                throw new IllegalArgumentException(view.getClass().getSimpleName() + " must have " +
                        "an id of named title");
            }
        }

        return this;
    }

    /**
     * 设置当前状态视图的子标题
     *
     * @param subtitleResId
     * @return
     */
    public StateView setSubtitle(@StringRes int subtitleResId) {
        return setSubtitle(getContext().getString(subtitleResId));
    }

    /**
     * 设置当前状态视图的子标题
     *
     * @param subtitle
     * @return
     */
    public StateView setSubtitle(String subtitle) {
        View view = getView(mState);
        if (view != null) {
            TextView titleTxt = (TextView) view.findViewById(R.id.tv_state_view_subtitle);
            if (titleTxt != null) {
                titleTxt.setVisibility(View.VISIBLE);
                titleTxt.setText(subtitle);
            } else {
                throw new IllegalArgumentException(view.getClass().getSimpleName() + " must have " +
                        "an id of named subtitle");
            }
        }

        return this;
    }

    /**
     * 设置当前状态视图的按钮点击事件
     *
     * @param listener
     * @return
     */
    public StateView setButton(int btnId, OnClickListener listener) {
        return setButton(btnId, null, listener);
    }

    /**
     * 设置当前状态视图的按钮文字和点击事件
     *
     * @param textResId
     * @param listener
     * @return
     */
    public StateView setButton(int btnId, @StringRes int textResId, OnClickListener listener) {
        return setButton(btnId, getContext().getString(textResId), listener);
    }

    /**
     * 设置当前状态视图的按钮文字和点击事件
     *
     * @param text
     * @param listener
     * @return
     */
    public StateView setButton(int btnId, String text, OnClickListener listener) {
        View view = getView(mState);
        if (view != null) {
            View button = view.findViewById(btnId);
            // 这个button只能是TextView或者Button控件
            if (button != null && (button instanceof Button || button instanceof TextView)) {
                button.setVisibility(VISIBLE);
                if (text != null && !text.equals("")) {
                    if (button instanceof Button) {
                        ((Button) button).setText(text);
                    } else {
                        ((TextView) button).setText(text);
                    }
                }
                if (listener != null) {
                    button.setOnClickListener(listener);
                }
            } else {
                throw new IllegalArgumentException(view.getClass().getSimpleName() + " view must " +
                        "have an id of named button");
            }
        }

        return this;
    }
}
