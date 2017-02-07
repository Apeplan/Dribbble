package com.simon.agiledevelop.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * describe: Help class for SDCard
 *
 * @author Simon Han
 * @date 2015.05.21
 * @email hanzx1024@gmail.com
 */
public class SDCardHelper {

    private static final String TAG = SDCardHelper.class.getSimpleName();

    private SDCardHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Byte与Byte的倍数
     */
    public static final int BYTE = 1;
    /**
     * KB与Byte的倍数
     */
    public static final int KB = 1024;
    /**
     * MB与Byte的倍数
     */
    public static final int MB = 1048576;
    /**
     * GB与Byte的倍数
     */
    public static final int GB = 1073741824;

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡Data路径
     *
     * @return SD卡Data路径
     */
    public static String getDataPath() {
        if (!isSDCardEnable()) return "sdcard unable!";
        return Environment.getDataDirectory().getPath() + File.separator;
    }

    /**
     * 获取SD卡路径
     * <p>一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable()) return "sdcard unable!";
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 获取SD卡下载路径（Download）
     *
     * @return Download 路径
     */
    public static String getDownloadPath() {
        if (!isSDCardEnable()) return "sdcard unable!";
        return getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath();
    }

    /**
     * 获取SD卡路径
     *
     * @return SD卡路径
     */
    public static String getSDCardPathByCmd() {
        if (!isSDCardEnable()) return "sdcard unable!";
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();
        BufferedReader bufferedReader = null;
        try {
            Process p = run.exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(p
                    .getInputStream())));
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null) {
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray.length >= 5) {
                        return strArray[1].replace("/.android_secure", "") + File.separator;
                    }
                }
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    return " 命令执行失败";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseHelper.closeIO(bufferedReader);
        }
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getFreeSpace() {
        if (!isSDCardEnable()) return "sdcard unable!";
        StatFs stat = new StatFs(getSDCardPath());
        long blockSize, availableBlocks;
        availableBlocks = stat.getAvailableBlocksLong();
        blockSize = stat.getBlockSizeLong();
        return byte2FitSize(availableBlocks * blockSize);
    }

    /**
     * 获取手机外部可用空间大小，单位为byte
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getExternalTotalSpace() {
        long totalSpace = -1L;
        if (isSDCardEnable()) {
            try {
                String path = Environment.getExternalStorageDirectory().getPath();// 获取外部存储目录即
                // SDCard
                StatFs stat = new StatFs(path);
                long blockSize = stat.getBlockSize();
                long totalBlocks = stat.getBlockCount();
                totalSpace = totalBlocks * blockSize;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return totalSpace;
    }


    /**
     * 获取外部存储可用空间，单位为byte
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getExternalSpace() {
        long availableSpace = -1L;
        if (isSDCardEnable()) {
            try {
                String path = Environment.getExternalStorageDirectory().getPath();
                StatFs stat = new StatFs(path);
                availableSpace = stat.getAvailableBlocks() * (long) stat.getBlockSize();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return availableSpace;
    }

    /**
     * 获取手机内部空间大小，单位为byte
     *
     * @author Jansea
     */
    @SuppressWarnings("deprecation")
    public static long getTotalInternalSpace() {
        long totalSpace = -1L;
        try {
            String path = Environment.getDataDirectory().getPath();
            StatFs stat = new StatFs(path);
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();// 获取该区域可用的文件系统数
            totalSpace = totalBlocks * blockSize;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return totalSpace;
    }

    /**
     * 获取手机内部可用空间大小，单位为byte
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getAvailableInternalMemorySize() {
        long availableSpace = -1l;
        try {
            String path = Environment.getDataDirectory().getPath();// 获取 Android
            // 数据目录
            StatFs stat = new StatFs(path);// 一个模拟linux的df命令的一个类,获得SD卡和手机内存的使用情况
            long blockSize = stat.getBlockSize();// 返回 Int ，大小，以字节为单位，一个文件系统
            long availableBlocks = stat.getAvailableBlocks();// 返回 Int
            // ，获取当前可用的存储空间
            availableSpace = availableBlocks * blockSize;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return availableSpace;
    }

    /**
     * 字节数转合适大小
     * <p>保留3位小数</p>
     *
     * @param byteNum 字节数
     * @return 1...1024 unit
     */
    public static String byte2FitSize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < KB) {
            return String.format(Locale.getDefault(), "%.3fB", (double) byteNum);
        } else if (byteNum < MB) {
            return String.format(Locale.getDefault(), "%.3fKB", (double) byteNum / KB);
        } else if (byteNum < GB) {
            return String.format(Locale.getDefault(), "%.3fMB", (double) byteNum / MB);
        } else {
            return String.format(Locale.getDefault(), "%.3fGB", (double) byteNum / GB);
        }
    }


    /**
     * 获取SD卡信息
     *
     * @return SDCardInfo
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static SDCardInfo getSDCardInfo() {
        SDCardInfo sd = new SDCardInfo();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            sd.isExist = true;
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            sd.totalBlocks = sf.getBlockCountLong();
            sd.blockByteSize = sf.getBlockSizeLong();
            sd.availableBlocks = sf.getAvailableBlocksLong();
            sd.availableBytes = sf.getAvailableBytes();
            sd.freeBlocks = sf.getFreeBlocksLong();
            sd.freeBytes = sf.getFreeBytes();
            sd.totalBytes = sf.getTotalBytes();
        }
        return sd;
    }

    private static class SDCardInfo {
        boolean isExist;
        long totalBlocks;
        long freeBlocks;
        long availableBlocks;

        long blockByteSize;

        long totalBytes;
        long freeBytes;
        long availableBytes;

        @Override
        public String toString() {
            return "SDCardInfo{" +
                    "isExist=" + isExist +
                    ", totalBlocks=" + totalBlocks +
                    ", freeBlocks=" + freeBlocks +
                    ", availableBlocks=" + availableBlocks +
                    ", blockByteSize=" + blockByteSize +
                    ", totalBytes=" + totalBytes +
                    ", freeBytes=" + freeBytes +
                    ", availableBytes=" + availableBytes +
                    '}';
        }
    }
}