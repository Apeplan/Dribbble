package com.simon.dribbble.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

public class PreferencesHelper {
    public final static float FLOAT_DEFAULT = 0;
    public final static String STRING_DEFAULT = "";
    public final static int INT_DEFAULT = 0;
    public final static boolean BOOLEAN_DEFAULT = false;

    public PreferencesHelper(SharedPreferences preferences) {
        mSharedPreferences = preferences;
        mEditor = mSharedPreferences.edit();
    }

    public PreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(context.getPackageName(), Context
                .MODE_APPEND);
        mEditor = mSharedPreferences.edit();
    }

    public PreferencesHelper(Context context, String fileName) {
        mSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_APPEND);
        mEditor = mSharedPreferences.edit();
    }

    /**
     * put
     *
     * @param key
     * @param value
     */
    public void put(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public void put(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public void put(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    public void put(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public void put(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, STRING_DEFAULT);
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }


    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, BOOLEAN_DEFAULT);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public float getFloat(String key) {
        return mSharedPreferences.getFloat(key, FLOAT_DEFAULT);
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, INT_DEFAULT);
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, INT_DEFAULT);
    }

    public int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }

    public boolean isContains(String key) {
        return mSharedPreferences.contains(key);
    }

    public void remove(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    public void clear() {
        mEditor.clear().commit();
    }

    public PreferencesHelper() {
    }

    public final void setSharedPreferences(SharedPreferences preferences) {
        mSharedPreferences = preferences;
        mEditor = mSharedPreferences.edit();
    }

    private SharedPreferences mSharedPreferences;
    private Editor mEditor;
}
