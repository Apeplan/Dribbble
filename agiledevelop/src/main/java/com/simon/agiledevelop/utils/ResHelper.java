package com.simon.agiledevelop.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * describe: Help class for get Resources
 *
 * @author Simon Han
 * @date 2015.07.07
 * @email hanzx1024@gmail.com
 */
public class ResHelper {
    private static final String TAG = ResHelper.class.getName();

    private static Context mContext = App.INSTANCE;

    private static Class<?> CDrawable = null;

    private static Class<?> CLayout = null;

    private static Class<?> CId = null;

    private static Class<?> CAnim = null;

    private static Class<?> CStyle = null;

    private static Class<?> CString = null;

    private static Class<?> CArray = null;

    static {
        try {
            CDrawable = Class.forName(mContext.getPackageName() + ".R$drawable");
            CLayout = Class.forName(mContext.getPackageName() + ".R$layout");
            CId = Class.forName(mContext.getPackageName() + ".R$id");
            CAnim = Class.forName(mContext.getPackageName() + ".R$anim");
            CStyle = Class.forName(mContext.getPackageName() + ".R$style");
            CString = Class.forName(mContext.getPackageName() + ".R$string");
            CArray = Class.forName(mContext.getPackageName() + ".R$array");

        } catch (ClassNotFoundException e) {
            Log.i(TAG, e.getMessage());
        }
    }

    /**
     * 根据Drawable名称返回ID
     *
     * @param resName Drawable名称
     * @return Drawable Id
     */
    public static int getDrawableId(String resName) {
        return getResId(CDrawable, resName);
    }

    /**
     * 根据Layout名称返回ID
     *
     * @param resName Layout名称
     * @return Layout Id
     */
    public static int getLayoutId(String resName) {
        return getResId(CLayout, resName);
    }

    public static int getIdId(String resName) {
        return getResId(CId, resName);
    }

    /**
     * 根据Anim名称返回ID
     *
     * @param resName Anim名称
     * @return Anim Id
     */
    public static int getAnimId(String resName) {
        return getResId(CAnim, resName);
    }

    /**
     * 根据 Style 名称返回ID
     *
     * @param resName Style 名称
     * @return Style Id
     */
    public static int getStyleId(String resName) {
        return getResId(CStyle, resName);
    }

    /**
     * 根据 String 名称返回ID
     *
     * @param resName String 名称
     * @return String Id
     */
    public static int getStringId(String resName) {
        return getResId(CString, resName);
    }

    /**
     * 根据 Array 名称返回ID
     *
     * @param resName Array 名称
     * @return Array Id
     */
    public static int getArrayId(String resName) {
        return getResId(CArray, resName);
    }

    public static String getStrByResid(int resId) {
        return mContext.getResources().getString(resId);
    }

    public static Drawable getDrawableByResid(int resId) {
        return mContext.getResources().getDrawable(resId);
    }

    public static String[] getStringArray(int resId) {
        return mContext.getResources().getStringArray(resId);
    }

    public static int[] getIntArray(int resId) {
        return mContext.getResources().getIntArray(resId);
    }

    public static int getDimenPixelSize(int resId) {
        return mContext.getResources().getDimensionPixelSize(resId);
    }

    public static String getResourceName(int resId) {
        return mContext.getResources().getResourceName(resId);
    }

    public static int getColor(int resId) {
        return mContext.getResources().getColor(resId);
    }

    private static int getResId(Class<?> resClass, String resName) {
        if (resClass == null) {
            Log.i(TAG, "getRes(null," + resName + ")");
            throw new IllegalArgumentException("ResClass is not initialized. Please make sure you" +
                    " have added neccessary resources. Also make sure you have " + mContext
                    .getPackageName() + ".R$* configured in obfuscation. field=" + resName);
        }

        try {
            Field field = resClass.getField(resName);
            return field.getInt(resName);
        } catch (Exception e) {
            Log.i(TAG, "getRes(" + resClass.getName() + ", " + resName + ")");
            Log.i(TAG, "Error getting resource. Make sure you have copied all resources (res/) " +
                    "from SDK to your project. ");
            Log.i(TAG, e.getMessage());
        }

        return -1;
    }
}
