package com.simon.dribbble;

import android.app.Application;

import com.simon.dribbble.data.model.User;
import com.simon.dribbble.util.PreferencesHelper;

/**
 * Created by Simon Han on 2016/8/20.
 */

public class DribbbleApp extends Application {
    private static DribbbleApp mInstance;
    public static PreferencesHelper mPreferencesHelper;
    private User mUserInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mPreferencesHelper = new PreferencesHelper(this);
    }

    public static synchronized DribbbleApp context() {
        return mInstance;
    }

    public static PreferencesHelper spHelper() {
        return mPreferencesHelper;
    }

    public User getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(User mUserInfo) {
        this.mUserInfo = mUserInfo;
    }
}
