package net.quickrecyclerview.utils.log;

import android.util.Log;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2015/12/15 13:41
 */

public class BaseLog {
    /**
     * 默认打印方法
     * @param type Log级别
     * @param tag TAG
     * @param msg 打印的信息
     */
    public static void printDefault(int type, String tag, String msg) {

        int index = 0;
        int maxLength = 4000;
        int countOfSub = msg.length() / maxLength;

        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + maxLength);
                printSub(type, tag, sub);
                index += maxLength;
            }
            printSub(type, tag, msg.substring(index, msg.length()));
        } else {
            printSub(type, tag, msg);
        }
    }

    private static void printSub(int type, String tag, String sub) {
        switch (type) {
            case LLog.V:
                Log.v(tag, sub);
                break;
            case LLog.D:
                Log.d(tag, sub);
                break;
            case LLog.I:
                Log.i(tag, sub);
                break;
            case LLog.W:
                Log.w(tag, sub);
                break;
            case LLog.E:
                Log.e(tag, sub);
                break;
            case LLog.A:
                Log.wtf(tag, sub);
                break;
        }
    }

    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag,
                    "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag,
                    "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }
}
