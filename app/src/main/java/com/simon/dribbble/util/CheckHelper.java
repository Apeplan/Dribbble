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

public class CheckHelper {
    // 数字
    private final static Pattern NUMBER = Pattern.compile("[0-9]*");
    // 小数
    private final static Pattern DECIMAL = Pattern.compile("^[-+]?[0-9]+(\\.[0-9]+)?$");
    // 汉字
    private final static Pattern CHINESE = Pattern.compile("[\u4e00-\u9fa5]");

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

    // TODO 字符串相关校验

    /**
     * 判断字符串是否为 null
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        return str == null;
    }

    /**
     * 判断字符串是否为 null 和 “”
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串
     *
     * @param input
     * @return 若输入字符串为null或空字符串，返回true
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

    /**
     * 判断字符串是否是数字
     *
     * @param number
     * @return
     */
    public static boolean isNumber(String number) {
        return !TextUtils.isEmpty(number) && NUMBER.matcher(number).matches();
    }

    /**
     * 判断字符串是否是小数
     *
     * @param number
     * @return
     */
    public static boolean isDecimal(String number) {
        return !TextUtils.isEmpty(number) && DECIMAL.matcher(number).matches();
    }

    /**
     * 字符串是否是字母
     *
     * @param letter
     * @return
     */
    public static boolean isLetter(String letter) {
        return !TextUtils.isEmpty(letter) && letter.matches("^[a-zA-Z]*");
    }

    /**
     * 字符串中是否包含汉字
     *
     * @param s
     * @return
     */
    public static boolean hasChinese(String s) {
        return !TextUtils.isEmpty(s) && CHINESE.matcher(s).find();
    }

    /**
     * 字符串是否以字母开头
     *
     * @param s
     * @return
     */
    public static boolean isLetterBegin(String s) {
        if (TextUtils.isEmpty(s))
            return false;
        else {
            char c = s.charAt(0);
            int i = (int) c;
            if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 字符串是否以指定的内容开头
     *
     * @param tagtext
     * @param begin
     * @return
     */
    public static boolean startWithTagtext(String tagtext, String begin) {

        return !TextUtils.isEmpty(tagtext) && !TextUtils.isEmpty(begin) && tagtext.startsWith
                (begin);
    }

    /**
     * 字符串是否以指定的内容结尾
     *
     * @param tagtext
     * @param end
     * @return
     */
    public static boolean endWithTagtext(String tagtext, String end) {

        return !TextUtils.isEmpty(tagtext) && !TextUtils.isEmpty(end) && tagtext.endsWith(end);
    }

    /**
     * 字符串是否包含指定的内容
     *
     * @param tagtext
     * @param contain
     * @return
     */
    public static boolean hasContainText(String tagtext, String contain) {

        return !TextUtils.isEmpty(tagtext) && !TextUtils.isEmpty(contain) && tagtext.contains
                (contain);
    }

    /**
     * 判断多个字符串是否相等，如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
     *
     * @param agrs
     * @return
     */
    public static boolean isEquals(String... agrs) {
        String last = null;
        for (int i = 0; i < agrs.length; i++) {
            String str = agrs[i];
            if (isEmpty(str)) {
                return false;
            }
            if (last != null && !str.equalsIgnoreCase(last)) {
                return false;
            }
            last = str;
        }
        return true;
    }

    // TODO 手机号、邮箱、身份证等校验

    /**
     * 检验是不是手机号
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhone(String phoneNum) {
        return isEmpty(phoneNum) ? false : PHONE.matcher(phoneNum).matches();
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return isEmpty(email) ? false : EMAILER.matcher(email).matches();
    }

    /**
     * 判断一个字符串是不是一个URL
     *
     * @param urlStr
     * @return
     */
    public static boolean isURL(String urlStr) {
        urlStr = urlStr.toString().toLowerCase();
        return isEmpty(urlStr) ? false : URL.matcher(urlStr).matches();
    }

    /**
     * 判断一个url是否为图片url
     *
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url) {
        return isEmpty(url) ? false : IMG_URL.matcher(url).matches();
    }

}
