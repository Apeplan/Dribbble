package com.simon.agiledevelop.recycler.animation;

import android.animation.Animator;
import android.view.View;

/**
 * describe: Base anim for RecyclerView item
 *
 * @author Simon Han
 * @date 2015.09.20
 * @email hanzx1024@gmail.com
 */

public interface BaseAnimation {
    Animator[] getAnimators(View view);
}
