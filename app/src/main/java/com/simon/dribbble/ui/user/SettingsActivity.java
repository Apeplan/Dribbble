package com.simon.dribbble.ui.user;


import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;

import com.simon.dribbble.R;
import com.simon.dribbble.ui.BaseActivity;
import com.simon.dribbble.ui.BasePresenter;

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

    @Override
    protected int getLayout() {
        return R.layout.activity_settings;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setCommonBackToolBack(toolbar,"设置");
    }

    @Override
    protected void initEventAndData() {

    }
}
