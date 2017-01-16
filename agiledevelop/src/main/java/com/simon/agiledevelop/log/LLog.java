package com.simon.agiledevelop.log;

/**
 * describe: 日志管理
 *
 * @author Simon Han
 * @date 2016.11.17
 * @email hanzx1024@gmail.com
 */

public class LLog {

    public static final String TAG = LLog.class.getSimpleName();

    /**
     * 日志级别
     */
    public static int mLevel = Logger.ALL;

    /**
     * 是否进行了初始化
     */
    static boolean isInitialized;

    /**
     * 打印的日志是否用边框包裹起来
     */
    public static boolean isBorder = true;

    public static void init(int Logger) {
        init(Logger, true);
    }

    public static void init(int Logger, boolean border) {
        if (isInitialized) {
            throw new IllegalStateException(
                    "Log is already initialized, do not initialize again");
        }
        isInitialized = true;

        mLevel = Logger;
        isBorder = border;

    }

    /**
     * Log a message with level {@link Logger#VERBOSE}.
     *
     * @param msg the message to log
     */
    public static void v(String msg) {
        Logger.println(Logger.VERBOSE, msg);
    }

    /**
     * Log an object with level {@link Logger#VERBOSE}.
     *
     * @param object the object to log
     */
    public static void v(Object object) {
        Logger.println(Logger.VERBOSE, object);
    }

    /**
     * Log an array with level {@link Logger#VERBOSE}.
     *
     * @param array the array to log
     */
    public static void v(Object[] array) {
        Logger.println(Logger.VERBOSE, array);
    }

    /**
     * Log a message with level {@link Logger#VERBOSE}.
     *
     * @param format the format of the message to log
     * @param args   the arguments of the message to log
     */
    public static void v(String format, Object... args) {
        Logger.println(Logger.VERBOSE, format, args);
    }

    /**
     * Log a message and a throwable with level {@link Logger#VERBOSE}.
     *
     * @param msg the message to log
     * @param tr  the throwable to be log
     */
    public static void v(String msg, Throwable tr) {
        Logger.println(Logger.VERBOSE, msg, tr);
    }


    /**
     * Log an object with level {@link Logger#DEBUG}.
     *
     * @param object the object to log
     */
    public static void d(Object object) {
        Logger.println(Logger.DEBUG, object);
    }

    /**
     * Log an array with level {@link Logger#DEBUG}.
     *
     * @param array the array to log
     */
    public static void d(Object[] array) {
        Logger.println(Logger.DEBUG, array);
    }

    /**
     * Log a message with level {@link Logger#DEBUG}.
     *
     * @param format the format of the message to log
     * @param args   the arguments of the message to log
     */
    public static void d(String format, Object... args) {
        Logger.println(Logger.DEBUG, format, args);
    }

    /**
     * Log a message with level {@link Logger#DEBUG}.
     *
     * @param msg the message to log
     */
    public static void d(String msg) {
        Logger.println(Logger.DEBUG, msg);
    }

    /**
     * Log a message and a throwable with level {@link Logger#DEBUG}.
     *
     * @param msg the message to log
     * @param tr  the throwable to be log
     */
    public static void d(String msg, Throwable tr) {
        Logger.println(Logger.DEBUG, msg, tr);
    }

    /**
     * Log an object with level {@link Logger#INFO}.
     *
     * @param object the object to log
     */
    public static void i(Object object) {
        Logger.println(Logger.INFO, object);
    }

    /**
     * Log an array with level {@link Logger#INFO}.
     *
     * @param array the array to log
     */
    public static void i(Object[] array) {
        Logger.println(Logger.INFO, array);
    }

    /**
     * Log a message with level {@link Logger#INFO}.
     *
     * @param format the format of the message to log
     * @param args   the arguments of the message to log
     */
    public static void i(String format, Object... args) {
        Logger.println(Logger.INFO, format, args);
    }

    /**
     * Log a message with level {@link Logger#INFO}.
     *
     * @param msg the message to log
     */
    public static void i(String msg) {
        Logger.println(Logger.INFO, msg);
    }

    /**
     * Log a message and a throwable with level {@link Logger#INFO}.
     *
     * @param msg the message to log
     * @param tr  the throwable to be log
     */
    public static void i(String msg, Throwable tr) {
        Logger.println(Logger.INFO, msg, tr);
    }

    /**
     * Log an object with level {@link Logger#WARN}.
     *
     * @param object the object to log
     */
    public static void w(Object object) {
        Logger.println(Logger.WARN, object);
    }

    /**
     * Log an array with level {@link Logger#WARN}.
     *
     * @param array the array to log
     */
    public static void w(Object[] array) {
        Logger.println(Logger.WARN, array);
    }

    /**
     * Log a message with level {@link Logger#WARN}.
     *
     * @param format the format of the message to log
     * @param args   the arguments of the message to log
     */
    public static void w(String format, Object... args) {
        Logger.println(Logger.WARN, format, args);
    }

    /**
     * Log a message with level {@link Logger#WARN}.
     *
     * @param msg the message to log
     */
    public static void w(String msg) {
        Logger.println(Logger.WARN, msg);
    }

    /**
     * Log a message and a throwable with level {@link Logger#WARN}.
     *
     * @param msg the message to log
     * @param tr  the throwable to be log
     */
    public static void w(String msg, Throwable tr) {
        Logger.println(Logger.WARN, msg, tr);
    }

    /**
     * Log an object with level {@link Logger#ERROR}.
     *
     * @param object the object to log
     */
    public static void e(Object object) {
        Logger.println(Logger.ERROR, object);
    }

    /**
     * Log an array with level {@link Logger#ERROR}.
     *
     * @param array the array to log
     */
    public static void e(Object[] array) {
        Logger.println(Logger.ERROR, array);
    }

    /**
     * Log a message with level {@link Logger#ERROR}.
     *
     * @param format the format of the message to log
     * @param args   the arguments of the message to log
     */
    public static void e(String format, Object... args) {
        Logger.println(Logger.ERROR, format, args);
    }

    /**
     * Log a message with level {@link Logger#ERROR}.
     *
     * @param msg the message to log
     */
    public static void e(String msg) {
        Logger.println(Logger.ERROR, msg);
    }

    /**
     * Log a message and a throwable with level {@link Logger#ERROR}.
     *
     * @param msg the message to log
     * @param tr  the throwable to be log
     */
    public static void e(String msg, Throwable tr) {
        Logger.println(Logger.ERROR, msg, tr);
    }

    /**
     * Log a XML string, with level {@link Logger#DEBUG} by default.
     *
     * @param xml the XML string to log
     */
    public static void xml(String xml) {
        Logger.xml(xml);
    }

    /**
     * Log a JSON string, with level {@link Logger#DEBUG} by default.
     *
     * @param json the JSON string to log
     */
    public static void json(String json) {
        Logger.json(json);
    }

}
