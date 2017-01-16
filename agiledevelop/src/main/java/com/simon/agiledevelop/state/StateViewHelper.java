package com.simon.agiledevelop.state;

import android.content.Context;
import android.view.View;

/**
 * describe: Help class for state view
 *
 * @author Simon Han
 * @date 2015.10.25
 * @email hanzx1024@gmail.com
 */

public interface StateViewHelper {
    View getCurrentLayout();

    void restoreView();

    void showLayout(View view);

    View inflate(int layoutId);

    Context getContext();

    View getView();
}
