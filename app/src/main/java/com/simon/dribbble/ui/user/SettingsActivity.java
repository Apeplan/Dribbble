package com.simon.dribbble.ui.user;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.simon.agiledevelop.mvpframe.BaseActivity;
import com.simon.agiledevelop.mvpframe.Presenter;
import com.simon.agiledevelop.state.StateView;
import com.simon.dribbble.R;
import com.simon.dribbble.ui.AboutActivity;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends BaseActivity {

    private CardView mAbout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected StateView getLoadingView() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setCommonBackToolBack(toolbar, "设置");

        mAbout = (CardView) findViewById(R.id.cv_about);
    }

    @Override
    protected void initEventAndData() {
        mAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        switch (id) {
            case R.id.cv_about:
                startIntent(AboutActivity.class);
                break;

            default:

                break;
        }
    }
}
