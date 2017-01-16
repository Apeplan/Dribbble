package com.simon.agiledevelop.state.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.simon.agiledevelop.utils.NetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * describe: Receiver for NetWork
 *
 * @author Simon Han
 * @date 2015.08.27
 * @email hanzx1024@gmail.com
 */

public class NetStateReceiver extends BroadcastReceiver {
    private static final String TAG = NetStateReceiver.class.getSimpleName();
    public final static String CUSTOM_ANDROID_NET_CHANGE_ACTION =
            "net.xcgoo.app.netstate.net.conn.CONNECTIVITY_CHANGE";
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    private static boolean isNetAvailable = false;
    private static NetHelper.NetType mNetType;
    private static List<NetChangeObserver> mNetChangeObservers = new ArrayList<NetChangeObserver>();
    private static BroadcastReceiver mBroadcastReceiver;

    /**
     * 获取广播接收者
     *
     * @return BroadcastReceiver
     */
    public static BroadcastReceiver getReceiver() {
        if (null == mBroadcastReceiver) {
            mBroadcastReceiver = new NetStateReceiver();
        }
        return mBroadcastReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mBroadcastReceiver = NetStateReceiver.this;
        String action = intent.getAction();
        if (action.equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION) || action.equalsIgnoreCase
                (CUSTOM_ANDROID_NET_CHANGE_ACTION)) {
            if (!NetHelper.isNetworkAvailable(context)) {
                Log.d(TAG, "-- The Network disconnected --");
                isNetAvailable = false;
            } else {
                Log.d(TAG, "-- The Network connected --");
                isNetAvailable = true;
                mNetType = NetHelper.getAPNType(context);
            }
            notifyObserver();
        }
    }

    /**
     * 注册接收者
     *
     * @param context
     */
    public static void registerNetworkStateReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        context.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    /**
     * 注销接收者
     *
     * @param context
     */
    public static void unregisterNetworkStateReciver(Context context) {
        if (null != mBroadcastReceiver) {
            try {
                context.getApplicationContext().unregisterReceiver(mBroadcastReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注册观察者
     *
     * @param observer
     */
    public static void registerObserver(NetChangeObserver observer) {
        if (null == mNetChangeObservers) {
            mNetChangeObservers = new ArrayList<NetChangeObserver>();
        }
        mNetChangeObservers.add(observer);
    }

    /**
     * 移除注册的观察者
     *
     * @param observer
     */
    public static void removeRegisterObserver(NetChangeObserver observer) {
        if (null != mNetChangeObservers) {
            if (mNetChangeObservers.contains(observer)) {
                mNetChangeObservers.remove(observer);
            }
        }
    }

    /**
     * 检查接收者状态
     *
     * @param context
     */
    public static void checkNetworkState(Context context) {
        Intent intent = new Intent();
        intent.setAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        context.sendBroadcast(intent);
    }

    public static boolean isNetworkAvailable() {
        return isNetAvailable;
    }

    /**
     * 通知更新接收者
     */
    private void notifyObserver() {
        if (!mNetChangeObservers.isEmpty()) {
            int size = mNetChangeObservers.size();
            for (int i = 0; i < size; i++) {
                NetChangeObserver observer = mNetChangeObservers.get(i);
                if (null != observer) {
                    if (isNetworkAvailable()) {
                        observer.onNetConnected(mNetType);
                    } else {
                        observer.onNetDisConnect();
                    }
                }
            }
        }
    }
}
