package com.simon.agiledevelop.recycler.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * describe: Slide in left anim for RecyclerView item
 *
 * @author Simon Han
 * @date 2015.09.20
 * @email hanzx1024@gmail.com
 */

public class SlideInLeftAnimation implements BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "translationX", -view.getRootView()
                .getWidth(), 0)};
    }
}
