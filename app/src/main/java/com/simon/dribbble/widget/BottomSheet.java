package com.simon.dribbble.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/10/10 0010 16:25
 */

public class BottomSheet extends FrameLayout {

    // config
    private final int MIN_FLING_VELOCITY;
    private final int MAX_FLING_VELOCITY;

    // state
    private List<Callbacks> callbacks;
    private int sheetExpandedTop;
    private int sheetBottom;
    private int dismissOffset;
    private int nestedScrollInitialTop;
    private boolean settling = false;
    private boolean isNestedScrolling = false;
    private boolean initialHeightChecked = false;
    private boolean hasInteractedWithSheet = false;

    // child views & helpers
    private View sheet;
    private ViewDragHelper sheetDragHelper;
//    private ViewOffsetHelper sheetOffsetHelper;

    public BottomSheet(Context context) {
        this(context, null, 0);
    }

    public BottomSheet(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomSheet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        MIN_FLING_VELOCITY = viewConfiguration.getScaledMinimumFlingVelocity();
        MAX_FLING_VELOCITY = viewConfiguration.getScaledMaximumFlingVelocity();
    }


//    public void dismiss() {
//        animateSettle(dismissOffset);
//    }
//
//    public void expand() {
//        animateSettle(0);
//    }

    public boolean isExpanded() {
        return sheet.getTop() == sheetExpandedTop;
    }

    /**
     * 注册 BottomSheet 回调监听
     *
     * @param callback
     */
    public void registerCallback(Callbacks callback) {
        if (callbacks == null) {
            callbacks = new CopyOnWriteArrayList<>();
        }
        callbacks.add(callback);
    }

    /**
     * 注销 BottomSheet 回调监听
     *
     * @param callback
     */
    public void unregisterCallback(Callbacks callback) {
        if (callbacks != null && !callbacks.isEmpty() && callbacks.contains(callback)) {
            callbacks.remove(callback);
        }
    }

    /**
     * BottomSheet 监听回调
     */
    public static abstract class Callbacks {
        public void onSheetDismissed() {
        }

        public void onSheetPositionChanged() {

        }
    }

}
