package com.simon.agiledevelop.mvpframe;

/**
 * describe:
 *
 * @author Apeplan
 * @date 2016/12/27
 * @email hanzx1024@gmail.com
 */

public interface Presenter<V extends MvpView> {
    /**
     * Set or attach the view to this presenter
     */
    void attachView(V view);

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from
     * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
     */
    void detachView(boolean retainInstance);
}
