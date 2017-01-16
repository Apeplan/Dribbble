package com.simon.agiledevelop.recycler.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * describe: Slide in bottom anim for RecyclerView item
 *
 * @author Simon Han
 * @date 2015.09.20
 * @email hanzx1024@gmail.com
 */

public class SlideInBottomAnimation implements BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight()
                , 0)};
    }
}
