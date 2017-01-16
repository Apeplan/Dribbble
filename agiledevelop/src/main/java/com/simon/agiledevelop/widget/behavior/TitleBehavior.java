package com.simon.agiledevelop.widget.behavior;

/**
 * Created by wing on 11/5/16.
 */

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * describe: Bye Bye Burger Android Title Bar Behavior
 *
 * @author Simon Han
 * @date 2016.10.20
 * @email hanzx1024@gmail.com
 */

public class TitleBehavior extends CoordinatorLayout.Behavior<View> {

  private final int mTouchSlop;
  private boolean isFirstMove = true;
  private AnimateHelper mAnimateHelper;

  public TitleBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);

    mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
  }

  // on Scroll Started
  @Override
  public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                                     View directTargetChild, View target, int nestedScrollAxes) {

    return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
  }

  @Override
  public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target,
                                int dx, int dy, int[] consumed) {

    if (isFirstMove) {
      isFirstMove = false;
      mAnimateHelper = AnimateHelper.get(child);
      mAnimateHelper.setStartY(child.getY());
      mAnimateHelper.setMode(AnimateHelper.MODE_TITLE);
    }
    if (Math.abs(dy) > mTouchSlop) {
      if (dy < 0) {

        if (mAnimateHelper.getState() == AnimateHelper.STATE_HIDE) {
          mAnimateHelper.show();
        }
      } else if (dy > 0) {
        if (mAnimateHelper.getState() == AnimateHelper.STATE_SHOW) {
          mAnimateHelper.hide();
        }
      }
    }
  }
}

