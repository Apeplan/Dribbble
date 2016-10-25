package com.simon.dribbble.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 常用判断、校验帮助类
 * <p>
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/10/11 0011 10:58
 */

public class RegexHelper {

    // 邮箱匹配规则
    private static final Pattern EMAILER = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\" +
            ".\\w+([-.]\\w+)*");
    // 手机模糊匹配规则
    private static final Pattern PHONE = Pattern.compile("^[1][3-8]\\d{9}$");
    // URL 匹配规则
    private static final Pattern URL = Pattern.compile("^((https|http|ftp|rtsp|mms)?://)" + "?(" +
            "([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
            + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IPURL- 199.194.52.184
            + "|" // IP,DOMAIN
            + "([0-9a-z_!~*'()-]+\\.)*" // - www.
            + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." //
            + "[a-z]{2,6})" // first level domain- .com or .museum
            + "(:[0-9]{1,4})?" // :80
            + "((/?)|" // a slash isn't required if there is no file name
            + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$");
    private final static Pattern IMG_URL = Pattern.compile(".*?(gif|jpeg|png|jpg|bmp)");



    // TODO 手机号、邮箱、身份证等校验

    /**
     * 检验是不是手机号
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhone(String phoneNum) {
        return TextUtils.isEmpty(phoneNum) ? false : PHONE.matcher(phoneNum).matches();
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return TextUtils.isEmpty(email) ? false : EMAILER.matcher(email).matches();
    }

    /**
     * 判断一个字符串是不是一个URL
     *
     * @param urlStr
     * @return
     */
    public static boolean isURL(String urlStr) {
        urlStr = urlStr.toString().toLowerCase();
        return TextUtils.isEmpty(urlStr) ? false : URL.matcher(urlStr).matches();
    }

    /**
     * 判断一个url是否为图片url
     *
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url) {
        return TextUtils.isEmpty(url) ? false : IMG_URL.matcher(url).matches();
    }

}
