package com.simon.agiledevelop.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Debug;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Toast;

import com.simon.agiledevelop.log.LLog;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


/**
 * describe:获取手机设备信息的帮助类
 *
 * @author Simon Han
 * @date 2015.05.21
 * @email hanzx1024@gmail.com
 */

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class DeviceHelper {

    // 手机网络类型
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    public static boolean GTE_HC;
    public static boolean GTE_ICS;
    public static boolean PRE_HC;
    private static Integer _loadFactor = null;

    static {
        GTE_ICS = Build.VERSION.SDK_INT >= 14;
        GTE_HC = Build.VERSION.SDK_INT >= 11;
        PRE_HC = Build.VERSION.SDK_INT < 11;
    }

    public static int getDefaultLoadFactor(Context context) {
        if (_loadFactor == null) {
            Integer integer = Integer.valueOf(0xf & context.getResources().getConfiguration()
                    .screenLayout);
            _loadFactor = integer;
            _loadFactor = Integer.valueOf(Math.max(integer.intValue(), 1));
        }
        return _loadFactor.intValue();
    }

    /**
     * 获取UUID
     *
     * @return 32UUID小写字符串
     */
    public static String getUdid(Context context) {
        String strUUID = UUID.randomUUID().toString();
        strUUID = strUUID.replaceAll("-", "").toLowerCase();
        return strUUID;
    }

    /**
     * 是否有物理菜单键
     *
     * @param context
     * @return
     */
    public static boolean hasHardwareMenuKey(Context context) {
        boolean flag = false;
        if (PRE_HC)
            flag = true;
        else if (GTE_ICS) {
            flag = ViewConfiguration.get(context).hasPermanentMenuKey();
        } else
            flag = false;
        return flag;
    }

    /**
     * 打开谷歌市场
     *
     * @param activity
     * @param pck
     * @return
     */
    public static boolean gotoGoogleMarket(Activity activity, String pck) {
        try {
            Intent intent = new Intent();
            intent.setPackage("com.android.vending");
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + pck));
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 包名是否存在
     *
     * @param context
     * @param pckName
     * @return
     */
    public static boolean isPackageExist(Context context, String pckName) {
        try {
            PackageInfo pckInfo = context.getPackageManager().getPackageInfo(pckName, 0);
            if (pckInfo != null)
                return true;
        } catch (NameNotFoundException e) {
            LLog.e(e.getMessage());
        }
        return false;
    }

    public static void hideAnimatedView(View view) {
        if (PRE_HC && view != null)
            view.setPadding(view.getWidth(), 0, 0, 0);
    }

    /**
     * 判断是否是平板（官方用法）
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration
                .SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void showAnimatedView(View view) {
        if (PRE_HC && view != null)
            view.setPadding(0, 0, 0, 0);
    }


    /**
     * 返回系统当前语言和国家
     *
     * @param context
     * @return
     */
    public static String getCurCountryLan(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage()
                + "-"
                + context.getResources().getConfiguration().locale.getCountry();
    }

    /**
     * 是否是中国
     *
     * @param context
     * @return
     */
    public static boolean isZhCN(Context context) {
        String lang = context.getResources().getConfiguration().locale.getCountry();
        if (lang.equalsIgnoreCase("CN")) {
            return true;
        }
        return false;
    }

    /**
     * 计算百分比，默认保留两位小数
     *
     * @param p1
     * @param p2
     * @return
     */
    public static String percent(double p1, double p2) {
        return percent(p1, p2, 2);
    }

    /**
     * 计算百分比
     *
     * @param p1
     * @param p2
     * @param num 保留几位小数
     * @return
     */
    public static String percent(double p1, double p2, int num) {
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(num);
        str = nf.format(p3);
        return str;
    }

    /**
     * 打开手机中的应用市场
     *
     * @param context
     * @param pck
     */
    public static void gotoMarket(Context context, String pck) {
        if (!isHaveMarket(context)) {
            Toast.makeText(context, "你手机中没有安装应用市场！", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + pck));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * 判断手机是否安装了应用市场
     *
     * @param context
     * @return
     */
    public static boolean isHaveMarket(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        return infos.size() > 0;
    }

    /**
     * 判断是否有 Activity 能够匹配 Intent
     *
     * @param context
     * @return
     */
    public static boolean isMatchActivity(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        // MATCH_DEFAULT_ONLY: 只要不返回 null,startActivity 一定可以成功
        ResolveInfo infos = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return infos != null;
    }

    /**
     * 打开App所在的应用市场
     *
     * @param context
     */
    public static void openAppInMarket(Context context) {
        if (context != null) {
            String pckName = context.getPackageName();
            try {
                gotoMarket(context, pckName);
            } catch (Exception ex) {
                try {
                    String otherMarketUri = "http://market.android.com/details?id="
                            + pckName;
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(otherMarketUri));
                    context.startActivity(intent);
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * 返回包信息
     *
     * @param context
     * @param pckName
     * @return
     */
    public static PackageInfo getPackageInfo(Context context, String pckName) {
        try {
            return context.getPackageManager().getPackageInfo(pckName, 0);
        } catch (NameNotFoundException e) {
            LLog.e(e.getMessage());
        }
        return null;
    }

    /**
     * 获取android系统的版本号
     *
     * @return
     */
    public static String getOSVersion() {
        String ver = Build.VERSION.RELEASE;
        ver = "android " + ver;
        return ver;
    }

    /**
     * 返回 android SDK的版本号
     *
     * @return
     */
    public static String getOSVersionSDK() {
        return Build.VERSION.SDK;
    }

    /**
     * 返回 android SDK 的版本号
     *
     * @return
     */
    public static int getOSVersionSDKINT() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 返回手机型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 检测手机手否插入SIM卡
     *
     * @return
     */
    public static boolean isSimCardAvailable(Context context) {
        if (null == context) {
            return false;
        } else {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context
                        .TELEPHONY_SERVICE);
                return tm.getSimState() == TelephonyManager.SIM_STATE_READY;
            } catch (Exception e) {
                LLog.e(e);
            }
        }
        return false;
    }

    /**
     * 检测SIM卡是否可读
     *
     * @return
     */
    public static boolean isSimCardRead(Context context) {
        if (null == context) {
            return false;
        } else {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context
                        .TELEPHONY_SERVICE);
                return TelephonyManager.SIM_STATE_READY == tm.getSimState();
            } catch (Exception e) {
                LLog.e(e);
            }
        }
        return false;
    }

    /**
     * 返回独特的用户ID，一个GSM手机的IMSI。
     *
     * @return 如果不可用，则返回空。
     */
    public static String getIMSI(Context context) {
        String imsi = null;
        if (null != context) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context
                        .TELEPHONY_SERVICE);

                return tm.getSubscriberId();
            } catch (Exception e) {
                LLog.e(e);
            }
        }
        return imsi;
    }

    /**
     * 返回本机号码，并不一定能获取到
     *
     * @return
     */
    public static String getPhoneNumber(Context context) {
        String phoneNum = null;
        if (null != context) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context
                        .TELEPHONY_SERVICE);
                phoneNum = tm.getLine1Number();
            } catch (Exception e) {
                LLog.e(e);
            }
        }
        return phoneNum;
    }

    /**
     * 返回手机服务商
     *
     * @return
     */
    public static String getProvidersName(Context context) {
        String providersName = null;
        String IMSI = getIMSI(context);
//        IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            providersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            providersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            providersName = "中国电信";
        } else {
            providersName = "其他服务商 " + IMSI;
        }
        return providersName;
    }

    /**
     * 返回本应用的版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
                    .versionCode;
        } catch (NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 根据包名应用的版本号
     *
     * @param context
     * @param packageName
     * @return
     */
    public static int getVersionCode(Context context, String packageName) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 返回应用的本版名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String name = "";
        try {
            name = context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
                    .versionName;
        } catch (NameNotFoundException ex) {
            name = "";
        }
        return name;
    }

    /**
     * 设备是否处于可交互状态（屏幕亮着）
     *
     * @param context
     * @return
     */
    public static boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }

    /**
     * 安装APK
     *
     * @param context
     * @param file
     */
    public static void installAPK(Context context, File file) {
        if (file == null || !file.exists())
            return;
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 返回安装APK的Intent
     *
     * @param file
     * @return
     */
    public static Intent getInstallApkIntent(File file) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 打开相机
     *
     * @param context
     */
    public static void openCamera(Context context) {
        Intent intent = new Intent(); // 调用照相机
        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        intent.setFlags(0x34c40000);
        context.startActivity(intent);
    }

    /**
     * 返回设备的IMEI号
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tel = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        return tel.getDeviceId();
    }

    public static String getPhoneType() {
        return Build.MODEL;
    }

    /**
     * 通过包名打开应用
     *
     * @param context
     * @param packageName
     */
    public static void openApp(Context context, String packageName) {
        Intent mainIntent = context.getPackageManager()
                .getLaunchIntentForPackage(packageName);
        if (mainIntent == null) {
            mainIntent = new Intent(packageName);
        } else {
            LLog.d("Action:" + mainIntent.getAction());
        }
        context.startActivity(mainIntent);
    }

    /**
     * 通过包名和Activity名,打开页面
     *
     * @param context
     * @param packageName
     * @param activityName
     * @return
     */
    public static boolean openAppActivity(Context context, String packageName,
                                          String activityName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, activityName);
        intent.setComponent(cn);
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * WIFI 是否打开
     *
     * @param context
     * @return
     */
    public static boolean isWifiOpen(Context context) {
        boolean isWifiConnect = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        // check the networkInfos numbers
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (int i = 0; i < networkInfos.length; i++) {
            if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                if (networkInfos[i].getType() == ConnectivityManager.TYPE_MOBILE) {
                    isWifiConnect = false;
                }
                if (networkInfos[i].getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConnect = true;
                }
            }
        }
        return isWifiConnect;
    }

    /**
     * 通过包名卸载应用
     *
     * @param context
     * @param packageName
     */
    public static void uninstallApk(Context context, String packageName) {
        if (isPackageExist(context, packageName)) {
            Uri packageURI = Uri.parse("package:" + packageName);
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
                    packageURI);
            context.startActivity(uninstallIntent);
        }
    }

    /**
     * 发送邮件
     *
     * @param context
     * @param subject 主题
     * @param content 内容
     * @param emails  邮件地址
     */
    public static void sendEmail(Context context, String subject,
                                 String content, String... emails) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            // 模拟器
            // intent.setType("text/plain");
            intent.setType("message/rfc822"); // 真机
            intent.putExtra(Intent.EXTRA_EMAIL, emails);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, content);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否有ActionBar
     *
     * @param activity
     * @return
     */
    public static boolean hasStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        if ((attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager
                .LayoutParams.FLAG_FULLSCREEN) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 调用系统安装了的应用分享
     *
     * @param context
     * @param title
     * @param url
     */
    public static void showSystemShareOption(Activity context,
                                             final String title, final String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
        intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
        context.startActivity(Intent.createChooser(intent, "选择分享"));
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    /**
     * 获取当前设备的SN
     *
     * @return
     */
    public static String getSimSN(Context context) {
        String sn = null;
        if (null != context) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context
                        .TELEPHONY_SERVICE);
                sn = tm.getSimSerialNumber();
            } catch (Exception e) {
                LLog.e(e);
            }
        }
        return sn;
    }

    /**
     * 获取当前设备的MAC地址
     *
     * @return
     */
    public static String getMAC(Context context) {
        String mac = null;
        if (null != context) {
            try {
                WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wi = wm.getConnectionInfo();
                mac = wi.getMacAddress();
            } catch (Exception e) {
                LLog.e(e);
            }
        }
        return mac;
    }

    /**
     * 获取当前设备的IP
     *
     * @return
     */
    public static String getLocalAddress() {
        String ip = "127.0.0.1";
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();
                Enumeration<InetAddress> ei = nif.getInetAddresses();
                while (ei.hasMoreElements()) {
                    InetAddress ia = ei.nextElement();
                    if (!ia.isLoopbackAddress()) {
                        ip = ia.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            LLog.e(e);
        }
        return ip;
    }

    /**
     * 获取设备信息
     *
     * @return
     */
    public static String[] getDivceInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
            LLog.e(e);
        }
        return cpuInfo;
    }

    /**
     * 判断手机CPU是否支持NEON指令集
     *
     * @return
     */
    public static boolean isNEON() {
        boolean isNEON = false;
        String cupinfo = getCPUInfos();
        if (cupinfo != null) {
            cupinfo = cupinfo.toLowerCase();
            isNEON = cupinfo != null && cupinfo.contains("neon");
        }
        return isNEON;
    }

    /**
     * 读取CPU信息文件，获取CPU信息
     *
     * @return
     */
    @SuppressWarnings("resource")
    private static String getCPUInfos() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        StringBuilder resusl = new StringBuilder();
        String resualStr = null;
        FileReader fr = null;
        BufferedReader localBufferedReader = null;
        try {
            fr = new FileReader(str1);
            localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                resusl.append(str2);
                // String cup = str2;
            }
            if (resusl != null) {
                resualStr = resusl.toString();
                return resualStr;
            }
        } catch (IOException e) {
            LLog.e(e);
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
                if (null != localBufferedReader) {
                    localBufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resualStr;
    }

    /**
     * 获取当前设备cpu的型号
     *
     * @return
     */
    public static int getCPUModel() {
        return matchABI(getSystemProperty("ro.product.cpu.abi")) | matchABI(getSystemProperty("ro" +
                ".product.cpu.abi2"));
    }

    /**
     * 匹配当前设备的cpu型号
     *
     * @param abiString
     * @return
     */
    private static int matchABI(String abiString) {
        if (TextUtils.isEmpty(abiString)) {
            return 0;
        }
        if ("armeabi".equals(abiString)) {
            return 1;
        } else if ("armeabi-v7a".equals(abiString)) {
            return 2;
        } else if ("x86".equals(abiString)) {
            return 4;
        } else if ("mips".equals(abiString)) {
            return 8;
        }
        return 0;
    }

    /**
     * 获取CPU核心数
     *
     * @return
     */
    public static int getCpuCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取Rom版本
     *
     * @return
     */
    public static String getRomversion() {
        String rom = "";
        try {
            String modversion = getSystemProperty("ro.modversion");
            String displayId = getSystemProperty("ro.build.display.id");
            if (modversion != null && !modversion.equals("")) {
                rom = modversion;
            }
            if (displayId != null && !displayId.equals("")) {
                rom = displayId;
            }
        } catch (Exception e) {
            LLog.e(e);
        }
        return rom;
    }

    /**
     * 获取系统配置参数
     *
     * @param key
     * @return
     */
    public static String getSystemProperty(String key) {
        String pValue = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method m = c.getMethod("get", String.class);
            pValue = m.invoke(null, key).toString();
        } catch (Exception e) {
            LLog.e(e);
        }
        return pValue;
    }

    /**
     * 获取系统中的Library包
     *
     * @return
     */
    public static List<String> getSystemLibs(Context context) {
        if (null == context) {
            return null;
        }
        PackageManager pm = context.getPackageManager();
        String[] libNames = pm.getSystemSharedLibraryNames();
        List<String> listLibNames = Arrays.asList(libNames);
        LLog.d("SystemLibs: %s", listLibNames);
        return listLibNames;
    }

    /**
     * 获取单个应用最大分配内存，单位为byte
     *
     * @return
     */
    public static long getOneAppMaxMemory(Context context) {
        if (context == null) {
            return -1;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);
        return activityManager.getMemoryClass() * 1024 * 1024;
    }

    /**
     * 获取指定本应用占用的内存，单位为byte
     *
     * @return
     */
    public static long getUsedMemory(Context context) {
        return getUsedMemory(context, null);
    }

    /**
     * 获取指定包名应用占用的内存，单位为byte
     *
     * @param packageName
     * @return
     */
    public static long getUsedMemory(Context context, String packageName) {
        if (context == null) {
            return -1;
        }
        if (isEmpty(packageName)) {
            packageName = context.getPackageName();
        }
        long size = 0;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runapps = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runapp : runapps) { // 遍历运行中的程序
            if (packageName.equals(runapp.processName)) {// 得到程序进程名，进程名一般就是包名，但有些程序的进程名并不对应一个包名
                // 返回指定PID程序的内存信息，可以传递多个PID，返回的也是数组型的信息
                Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new
                        int[]{runapp.pid});
                // 得到内存信息中已使用的内存，单位是K
                size = processMemoryInfo[0].getTotalPrivateDirty() * 1024;
            }
        }
        return size;
    }

    /**
     * 获取手机剩余内存，单位为byte
     *
     * @return
     */
    public static long getAvailableMemory(Context context) {
        if (context == null) {
            return -1;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        return info.availMem;
    }

    /**
     * 获取手机总内存，单位为byte
     *
     * @return
     */
    public static long getTotalMemory() {
        long size = 0;
        String path = "/proc/meminfo";// 系统内存信息文件
        try {
            String totalMemory = readProperties(path, "MemTotal", null);//
            // 读出来是带单位kb的，并且单位前有空格，所以去掉最后三位
            if (!isEmpty(totalMemory) && totalMemory.length() > 3) {
                size = Long.valueOf(totalMemory.substring(0, totalMemory.length() - 3)) * 1024;
            }
        } catch (Exception e) {
            LLog.e(e);
        }
        return size;
    }

    /**
     * 手机低内存运行阀值，单位为byte
     *
     * @return
     */
    public static long getThresholdMemory(Context context) {
        if (context == null) {
            return -1;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        return info.threshold;
    }

    /**
     * 手机是否处于低内存运行
     *
     * @return
     */
    public static boolean isLowMemory(Context context) {
        if (context == null) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        return info.lowMemory;
    }

    public static String readProperties(String filePath, String key, String defaultValue) {
        if (isEmpty(key) || isEmpty(filePath)) {
            return null;
        }
        String value = null;
        FileInputStream fis = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            fis = new FileInputStream(f);
            Properties p = new Properties();
            p.load(fis);
            value = p.getProperty(key, defaultValue);
        } catch (IOException e) {
            LLog.e(e.getMessage());
        } finally {
            close(fis);
        }
        return value;
    }

    /**
     * 关闭流
     *
     * @param io
     * @return
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                LLog.e(e.getMessage());
            }
        }
        return true;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }
}
