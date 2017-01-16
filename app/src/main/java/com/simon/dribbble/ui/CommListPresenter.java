package com.simon.dribbble.ui;

import com.simon.agiledevelop.MvpRxPresenter;
import com.simon.agiledevelop.MvpView;

/**
 * describe:
 *
 * @author Apeplan
 * @date 2017/1/9
 * @email hanzx1024@gmail.com
 */

public abstract class CommListPresenter<V extends MvpView, M> extends MvpRxPresenter<V, M> {
    public abstract void loadList(int action, long id, String type, int page);
}
