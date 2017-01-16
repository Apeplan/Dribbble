package com.simon.agiledevelop.recycler.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * describe: Scale anim for RecyclerView item
 *
 * @author Simon Han
 * @date 2015.09.20
 * @email hanzx1024@gmail.com
 */

public class ScaleInAnimation implements BaseAnimation {
    private static final float DEF_SCALE_FROM = 0.5f;
    private final float mFrom;

    public ScaleInAnimation() {
        this(DEF_SCALE_FROM);
    }

    public ScaleInAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1.0f),
                ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1.0f)
        };
    }
}
