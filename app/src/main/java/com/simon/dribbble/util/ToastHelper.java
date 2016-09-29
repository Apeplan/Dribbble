package com.simon.dribbble.util;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.simon.dribbble.DribbbleApp;

/**
 * Created by Simon Han on 2016/8/20.
 */

public class ToastHelper {

    private static Toast toast;

    /**
     * 向Toast中添加自定义view
     *
     * @param view
     * @return
     */
    public static void cusView(View view, int duration) {
        Toast toast = new Toast(DribbbleApp.context());
        toast.setView(view);
        toast.setDuration(duration);
        toast.show();
    }

    /**
     * 设置Toast字体颜色
     *
     * @param msg
     * @param messageColor
     * @param duration
     */
    public static void cusToast(String msg, int messageColor, int duration) {
        Toast toast = create();
        View view = toast.getView();
        if (view != null) {
            TextView message = ((TextView) view.findViewById(android.R.id.message));
            message.setTextColor(messageColor);
        }
        toast.setText(msg);
        toast.setDuration(duration);
        toast.show();
    }

    /**
     * 设置Toast字体及背景
     *
     * @param messageColor
     * @param background
     * @return
     */
    public static void cusToast2(String msg, int messageColor, int background, int duration) {
        Toast toast = create();
        View view = toast.getView();
        if (view != null) {
            TextView message = ((TextView) view.findViewById(android.R.id.message));
            view.setBackgroundResource(background);
            message.setTextColor(messageColor);
        }
        toast.setText(msg);
        toast.setDuration(duration);
        toast.show();
    }

    /**
     * 短时间显示Toast
     */
    public static void shortToast(int message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    /**
     * 短时间显示Toast
     */
    public static void shortToast(CharSequence message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void longToast(int message) {
        showToast(message, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     */
    public static void longToast(CharSequence message) {
        showToast(message, Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param msgResId
     * @param duration
     */
    public static void showToast(int msgResId, int duration) {
        String message = ResHelper.getStrByResid(msgResId);
        showToast(message, duration);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void showToast(CharSequence message, int duration) {
        create(message, duration).show();
    }

    private static Toast create() {
        if (null == toast) {
            toast = Toast.makeText(DribbbleApp.context(), "", Toast.LENGTH_SHORT);
        }
        return toast;
    }

    private static Toast create(CharSequence message, int duration) {
        Toast toast = create();
        toast.setText(message);
        toast.setDuration(duration);
        return toast;
    }
}
