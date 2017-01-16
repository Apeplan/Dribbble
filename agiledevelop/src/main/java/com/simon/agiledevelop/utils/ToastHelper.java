package com.simon.agiledevelop.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * describe: Help class for Toast
 *
 * @author Simon Han
 * @date 2016.10.20
 * @email hanzx1024@gmail.com
 */
public class ToastHelper {

    private ToastHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Toast sToast;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    /**
     * <p>当连续弹出吐司时，是要弹出新吐司还是只修改文本内容</p>
     * <p>{@code true}: 弹出新吐司<br>{@code false}: 只修改文本内容</p>
     * <p>如果为{@code false}的话可用来做显示任意时长的吐司</p>
     */
    public static boolean sIsCancel;

    /**
     * 安全地显示短时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void showShortToastSafe(final Context context, final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    public static void showShortToastSafe(final Context context, final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     * @param args    参数
     */
    public static void showShortToastSafe(final Context context, final int resId, final Object...
            args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param context 上下文
     * @param format  格式
     * @param args    参数
     */
    public static void showShortToastSafe(final Context context, final String format, final
    Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void showLongToastSafe(final Context context, final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    public static void showLongToastSafe(final Context context, final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     * @param args    参数
     */
    public static void showLongToastSafe(final Context context, final int resId, final Object...
            args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param context 上下文
     * @param format  格式
     * @param args    参数
     */
    public static void showLongToastSafe(final Context context, final String format, final
    Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 显示短时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void showShortToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    public static void showShortToast(Context context, int resId) {
        showToast(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     * @param args    参数
     */
    public static void showShortToast(Context context, int resId, Object... args) {
        showToast(context, resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示短时吐司
     *
     * @param context 上下文
     * @param format  格式
     * @param args    参数
     */
    public static void showShortToast(Context context, String format, Object... args) {
        showToast(context, format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void showLongToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    public static void showLongToast(Context context, int resId) {
        showToast(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     * @param args    参数
     */
    public static void showLongToast(Context context, int resId, Object... args) {
        showToast(context, resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param context 上下文
     * @param format  格式
     * @param args    参数
     */
    public static void showLongToast(Context context, String format, Object... args) {
        showToast(context, format, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param text     文本
     * @param duration 显示时长
     */
    private static void showToast(Context context, CharSequence text, int duration) {
        if (sToast == null) {
            sToast = Toast.makeText(context, text, duration);
        } else {
            if (sIsCancel) sToast.cancel();
            sToast.setText(text);
        }
        sToast.show();
    }

    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void showToast(Context context, int resId, int duration) {
        showToast(context, context.getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(Context context, int resId, int duration, Object... args) {
        showToast(context, String.format(context.getResources().getString(resId), args), duration);
    }

    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(Context context, String format, int duration, Object... args) {
        showToast(context, String.format(format, args), duration);
    }

    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (sToast != null) sToast.cancel();
    }
}