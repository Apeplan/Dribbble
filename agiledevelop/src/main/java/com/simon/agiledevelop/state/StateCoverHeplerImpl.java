package com.simon.agiledevelop.state;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

/**
 * describe: Help class for Cover state view
 *
 * @author Simon Han
 * @date 2015.10.25
 * @email hanzx1024@gmail.com
 */

public class StateCoverHeplerImpl implements StateViewHelper {
    private StateReplaceHeplerImpl helper;
    private View view;

    public StateCoverHeplerImpl(View view) {
        super();
        if (null == view) return;
        this.view = view;
        ViewGroup group = (ViewGroup) view.getParent();
        LayoutParams layoutParams = view.getLayoutParams();
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        group.removeView(view);
        group.addView(frameLayout, layoutParams);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams
                .MATCH_PARENT);
        View floatView = new View(view.getContext());
        frameLayout.addView(view, params);
        frameLayout.addView(floatView, params);

        helper = new StateReplaceHeplerImpl(floatView);
    }

    @Override
    public View getCurrentLayout() {
        return helper.getCurrentLayout();
    }

    @Override
    public void restoreView() {
        helper.restoreView();
    }

    @Override
    public void showLayout(View view) {
        helper.showLayout(view);
    }

    @Override
    public View inflate(int layoutId) {
        return helper.inflate(layoutId);
    }

    @Override
    public Context getContext() {
        return helper.getContext();
    }

    @Override
    public View getView() {
        return view;
    }
}
