package com.simon.dribbble.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.simon.dribbble.DribbbleApp;
import com.simon.dribbble.data.model.User;
import com.simon.dribbble.data.remote.DribbbleApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/29 0029 10:18
 */
public class DribbblePrefs {

    private static final String DRIBBBLE_PREF = "DRIBBBLE_PREF";
    private static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_USER_NAME = "KEY_USER_NAME";
    private static final String KEY_USER_USERNAME = "KEY_USER_USERNAME";
    private static final String KEY_USER_AVATAR = "KEY_USER_AVATAR";
    private static final String KEY_USER_TYPE = "KEY_USER_TYPE";
    private static final List<String> CREATIVE_TYPES
            = Arrays.asList(new String[]{"Player", "Team"});

    private static volatile DribbblePrefs instance;
    private final SharedPreferences mPrefs;

    private String accessToken;
    private boolean isLoggedIn = false;
    private long userId;
    private String name;
    private String userName;
    private String userAvatar;
    private String userType;
    private List<LoginStatusListener> loginStatusListeners;

    public static DribbblePrefs getInstance() {
        if (instance == null) {
            synchronized (DribbblePrefs.class) {
                if (instance == null) {
                    instance = new DribbblePrefs();
                }
            }
        }
        return instance;
    }

    private DribbblePrefs() {
        mPrefs = DribbbleApp.context().getSharedPreferences(DRIBBBLE_PREF, Context.MODE_PRIVATE);
        accessToken = mPrefs.getString(KEY_ACCESS_TOKEN, null);
        isLoggedIn = !TextUtils.isEmpty(accessToken);
        if (isLoggedIn) {
            userId = mPrefs.getLong(KEY_USER_ID, 0l);
            name = mPrefs.getString(KEY_USER_NAME, null);
            userName = mPrefs.getString(KEY_USER_USERNAME, null);
            userAvatar = mPrefs.getString(KEY_USER_AVATAR, null);
            userType = mPrefs.getString(KEY_USER_TYPE, null);
        }
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setAccessToken(String accessToken) {
        if (!TextUtils.isEmpty(accessToken)) {
            this.accessToken = accessToken;
            isLoggedIn = true;
            mPrefs.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply();
//            createApi();
            dispatchLoginEvent();
        }
    }

    public void setLoggedInUser(User user) {
        if (user != null) {
            name = user.name;
            userName = user.username;
            userId = user.id;
            userAvatar = user.avatar_url;
            userType = user.type;
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putLong(KEY_USER_ID, userId);
            editor.putString(KEY_USER_NAME, name);
            editor.putString(KEY_USER_USERNAME, userName);
            editor.putString(KEY_USER_AVATAR, userAvatar);
            editor.putString(KEY_USER_TYPE, userType);
            editor.apply();
        }
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return name;
    }

    public String getUserUsername() {
        return userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public User getUser() {
        return new User.Builder()
                .setId(userId)
                .setName(name)
                .setUsername(userName)
                .setAvatarUrl(userAvatar)
                .setType(userType)
                .build();
    }

    private String getAccessToken() {
        return !TextUtils.isEmpty(accessToken) ? accessToken
                : DribbbleApi.ACCESS_TOKEN;
    }

    public boolean userCanPost() {
        return CREATIVE_TYPES.contains(userType);
    }

    public void logout() {
        isLoggedIn = false;
        accessToken = null;
        userId = 0l;
        name = null;
        userName = null;
        userAvatar = null;
        userType = null;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(KEY_ACCESS_TOKEN, null);
        editor.putLong(KEY_USER_ID, 0l);
        editor.putString(KEY_USER_NAME, null);
        editor.putString(KEY_USER_AVATAR, null);
        editor.putString(KEY_USER_TYPE, null);
        editor.apply();
//        createApi();
        dispatchLogoutEvent();
    }

    public void addLoginStatusListener(LoginStatusListener listener) {
        if (loginStatusListeners == null) {
            loginStatusListeners = new ArrayList<>();
        }
        loginStatusListeners.add(listener);
    }

    public void removeLoginStatusListener(LoginStatusListener listener) {
        if (loginStatusListeners != null) {
            loginStatusListeners.remove(listener);
        }
    }

    private void dispatchLoginEvent() {
        if (loginStatusListeners != null && !loginStatusListeners.isEmpty()) {
            for (LoginStatusListener listener : loginStatusListeners) {
                listener.onLogin();
            }
        }
    }

    private void dispatchLogoutEvent() {
        if (loginStatusListeners != null && !loginStatusListeners.isEmpty()) {
            for (LoginStatusListener listener : loginStatusListeners) {
                listener.onLogout();
            }
        }
    }

    public interface LoginStatusListener {
        void onLogin();

        void onLogout();
    }
}
