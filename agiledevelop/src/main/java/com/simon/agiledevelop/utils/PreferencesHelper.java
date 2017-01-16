package com.simon.agiledevelop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * describe: Help class for SharedPreferences
 *
 * @author Simon Han
 * @date 2016.10.20
 * @email hanzx1024@gmail.com
 */
public class PreferencesHelper {

    public final static float FLOAT_DEFAULT = 0; // float 类型的默认值
    public final static String STRING_DEFAULT = ""; // String 类型的默认值为空串
    public final static int INT_DEFAULT = 0; // int 类型的默认值
    public final static boolean BOOLEAN_DEFAULT = false; // 布尔类型的默认值

    /*public PreferencesHelper(SharedPreferences preferences) {
        mSharedPreferences = preferences;
        mEditor = mSharedPreferences.edit();
    }*/

    public PreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(context.getPackageName(), Context
                .MODE_APPEND);
        mEditor = mSharedPreferences.edit();
    }

    public PreferencesHelper(Context context, String fileName) {
        mSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_APPEND);
        mEditor = mSharedPreferences.edit();
    }

    private static PreferencesHelper INSTANCE;

    public static PreferencesHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (PreferencesHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PreferencesHelper(App.INSTANCE);
                }
            }
        }
        return INSTANCE;
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

    /**
     * 将对象储存到sharepreference
     *
     * @param key
     * @param device
     * @param <T>
     */
    public <T> boolean saveDeviceData(String key, T device) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {   //Device为自定义类
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(device);
            // 将字节流编码成base64的字符串
            String oAuth_Base64 = new String(Base64.encode(baos
                    .toByteArray(), Base64.DEFAULT));
            mEditor.putString(key, oAuth_Base64).commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将对象从shareprerence中取出来
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getDeviceData(String key) {

        T device = null;
        String productBase64 = getString(key, null);

        if (productBase64 == null) {
            return null;
        }
        // 读取字节
        byte[] base64 = Base64.decode(productBase64.getBytes(), Base64.DEFAULT);

        // 封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            // 再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);

            // 读取对象
            device = (T) bis.readObject();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return device;
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
