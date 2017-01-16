package com.simon.agiledevelop.log;

import java.util.Arrays;


/**
 * describe:
 *
 * @author Simon Han
 * @date 2016.11.17
 * @email hanzx1024@gmail.com
 */

public class Logger {

    /**
     * Log level for Log.v.
     */
    public static final int VERBOSE = android.util.Log.VERBOSE;

    /**
     * Log level for Log.d.
     */
    public static final int DEBUG = android.util.Log.DEBUG;

    /**
     * Log level for Log.i.
     */
    public static final int INFO = android.util.Log.INFO;

    /**
     * Log level for Log.w.
     */
    public static final int WARN = android.util.Log.WARN;

    /**
     * Log level for Log.e.
     */
    public static final int ERROR = android.util.Log.ERROR;

    /**
     * Log level for Log#init, printing all logs.
     */
    public static final int ALL = VERBOSE;

    /**
     * Log level for Log#init, printing no log.
     */
    public static final int NONE = ERROR + 1;


    /**
     * Print a log in a new line internally.
     *
     * @param logLevel the log level of the printing log
     * @param msg      the message you would like to log
     */
    public static void printlnInternal(int logLevel, String msg) {

        String thread = LogFormat.formatThread(Thread.currentThread());

        String stackTrace = LogFormat.formatStackTrace(StackTraceUtil.getCroppedRealStackTrack
                (new Throwable().getStackTrace(), 2));

        println(logLevel, LLog.TAG, LLog.isBorder ? LogFormat.formatBorder(new
                String[]{thread, stackTrace, msg}) : ((thread != null ? (thread + LogFormat
                .lineSeparator()) : "") + (stackTrace != null ? (stackTrace + LogFormat
                .lineSeparator()) : "") +msg));

    }


    /**
     * Print an array in a new line.
     *
     * @param logLevel the log level of the printing array
     * @param array    the array to print
     */
    public static void println(int logLevel, Object[] array) {
        if (logLevel < LLog.mLevel) {
            return;
        }
        printlnInternal(logLevel, Arrays.deepToString(array));
    }

    /**
     * Print a log in a new line.
     *
     * @param logLevel the log level of the printing log
     * @param msg      the message you would like to log
     */
    public static void println(int logLevel, String msg) {
        if (logLevel < LLog.mLevel) {
            return;
        }
        printlnInternal(logLevel, msg);
    }

    public static <T> void println(int logLevel, T object) {
        if (logLevel < LLog.mLevel) {
            return;
        }
        printlnInternal(logLevel, object.toString());
    }

    /**
     * Print a log in a new line.
     *
     * @param logLevel the log level of the printing log
     * @param msg      the message you would like to log
     * @param tr       an throwable object to log
     */
    public static void println(int logLevel, String msg, Throwable tr) {
        if (logLevel < LLog.mLevel) {
            return;
        }
        printlnInternal(logLevel, ((msg == null || msg.length() == 0)
                ? "" : (msg + LogFormat.lineSeparator()))
                + LogFormat.formatThrowable(tr));
    }

    /**
     * Print a log in a new line.
     *
     * @param logLevel the log level of the printing log
     * @param format   the format of the printing log, null if just need to concat arguments
     * @param args     the arguments of the printing log
     */
    public static void println(int logLevel, String format, Object... args) {
        if (logLevel < LLog.mLevel) {
            return;
        }
        printlnInternal(logLevel, formatArgs(format, args));
    }

    public static void json(String json) {
        if (DEBUG < LLog.mLevel) {
            return;
        }
        println(DEBUG, LogFormat.formatJSON(json));
    }

    /**
     * Log a XML string, with level {@link #DEBUG} by default.
     *
     * @param xml the XML string to log
     */
    public static void xml(String xml) {
        if (DEBUG < LLog.mLevel) {
            return;
        }
        println(DEBUG, LogFormat.formatXML(xml));
    }

    /**
     * Format a string with arguments.
     *
     * @param format the format string, null if just to concat the arguments
     * @param args   the arguments
     * @return the formatted string
     */
    private static String formatArgs(String format, Object... args) {
        if (format != null) {
            return String.format(format, args);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0, N = args.length; i < N; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(args[i]);
            }
            return sb.toString();
        }
    }


    static final int MAX_LENGTH_OF_SINGLE_MESSAGE = 4063;

    public static void println(int logLevel, String tag, String msg) {
        if (msg.length() <= MAX_LENGTH_OF_SINGLE_MESSAGE) {
            printChunk(logLevel, tag, msg);
            return;
        }

        int msgLength = msg.length();
        int start = 0;
        int end = start + MAX_LENGTH_OF_SINGLE_MESSAGE;
        while (start < msgLength) {
            printChunk(logLevel, tag, msg.substring(start, end));

            start = end;
            end = Math.min(start + MAX_LENGTH_OF_SINGLE_MESSAGE, msgLength);
        }
    }

    /**
     * Print single chunk of log in new line.
     *
     * @param logLevel the level of log
     * @param tag      the tag of log
     * @param msg      the msg of log
     */
    private static void printChunk(int logLevel, String tag, String msg) {
        android.util.Log.println(logLevel, tag, msg);
    }

}
