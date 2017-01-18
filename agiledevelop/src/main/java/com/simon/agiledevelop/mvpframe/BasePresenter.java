package com.simon.agiledevelop.mvpframe;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * describe:
 *
 * @author Apeplan
 * @date 2016/12/27
 * @email hanzx1024@gmail.com
 */

public class BasePresenter<V extends MvpView> implements Presenter<V> {

    private WeakReference<V> viewRef;

    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    /**
     * Get the attached view. You should always call {@link #isViewAttached()} to check if the view
     * is
     * attached to avoid NullPointerExceptions.
     *
     * @return <code>null</code>, if view is not attached, otherwise the concrete view instance
     */
    @Nullable
    protected V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    /**
     * Checks if a view is attached to this presenter. You should always call this method before
     * calling {@link #getView()} to get the view instance.
     */
    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }
}
