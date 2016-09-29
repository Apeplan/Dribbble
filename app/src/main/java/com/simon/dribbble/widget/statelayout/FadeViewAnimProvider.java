package com.simon.dribbble.widget.statelayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by lufficc on 2016/8/26.
 */

public class FadeViewAnimProvider implements ViewAnimProvider{
    @Override
    public void onHideAndShow(final View willHide,final @NonNull View willShow) {
        if(willHide != null )
        {
            willHide.animate()
                    .alpha(0)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            willHide.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            willHide.setVisibility(View.GONE);
                        }
                    });
        }


        willShow.animate()
                .alpha(1)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        willShow.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        willShow.setVisibility(View.VISIBLE);
                    }
                });
    }

}
