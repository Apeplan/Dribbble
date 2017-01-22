package com.simon.dribbble;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.simon.agiledevelop.log.LLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 捕获未捕获的异常
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2015/5/21 15:37
 */
public class UncaughtException implements UncaughtExceptionHandler {
    // Debug Log tag
    private static final String TAG = UncaughtException.class.getSimpleName();
    private Context mContext;
    /**
     * 系统默认的UncaughtException处理器
     */
    private UncaughtExceptionHandler mDefaultExceptionHandler;
    /**
     * 用来存储设备信息和异常信息
     */
    private Map<String, String> infos = new HashMap<String, String>();
    /**
     * 错误报告文件的扩展名
     */
    private static final String CRASH_REPORTER_EXTENSION = ".txt";
    /**
     * 用于格式化日期,作为日志文件名的一部分
     */
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private static UncaughtException minstance;

    public UncaughtException() {

    }

    public static UncaughtException getInstance() {
        if (null == minstance) {
            synchronized (UncaughtException.class) {
                if (null == minstance) {
                    minstance = new UncaughtException();
                }
            }
        }
        return minstance;
    }

    /**
     * 初始化，注册Context
     * ，获取系统默认的UncaughtExceptionHandler处理类，设置UncaughtException为程序的默认处理类
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当未捕获的异常发生时，会转入该函数
     *
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (null != mDefaultExceptionHandler && handlerException(ex)) {
            mDefaultExceptionHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                LLog.e("error=  %s", e);
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return
     */
    public boolean handlerException(Throwable ex) {
        if (null == ex) {
            return false;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "程序发生了位置错误", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        saveCrashInfo2File(ex);
        // 发送错误报告到服务器
//        sendCrashReportsToServer(mContext);
        return true;
    }

    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */
    public void sendPreviousReportsToServer() {
        sendCrashReportsToServer(mContext);
    }

    /**
     * 发送错误信息到服务器
     *
     * @param context
     */
    private void sendCrashReportsToServer(Context context) {
        String[] crFiles = getCrashReportFiles(context);
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));
            for (String fileName : sortedFiles) {
                File cr = new File(context.getFilesDir(), fileName);
                postReport(cr);
                cr.delete();// 删除已发送的报告
            }
        }
    }

    /**
     * 获取错误报告文件名
     *
     * @param ctx
     * @return
     */
    private String[] getCrashReportFiles(Context ctx) {
        File filesDir = ctx.getFilesDir();
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return filesDir.list(filter);
    }

    private void postReport(File file) {
        // TODO 发送错误报告到服务器
    }


    /**
     * 保存错误信息到本地文件中
     *
     * @param ex
     * @return
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        FileOutputStream fos = null;
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
                String fileName = "apeplan-crash-" + time + "-" + timestamp +
                        CRASH_REPORTER_EXTENSION;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = "/sdcard/crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            Log.e(TAG, "an error occured while writing file...", e);
        }

        return null;
    }

    /**
     * 手机设备参数信息
     *
     * @param context
     */
    public void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager
                    .GET_ACTIVITIES);
            if (null != pi) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            LLog.e("an error occured when collect package info %s", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                LLog.d(field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                LLog.e("an error occured when collect crash info  %s", e);
            }
        }
    }


}
