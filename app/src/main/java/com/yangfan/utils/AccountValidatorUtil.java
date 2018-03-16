package com.yangfan.utils;

import java.util.regex.Pattern;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class AccountValidatorUtil {
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    public static final String REGEX_MOBILE_SIMPLE = "^[1][0-9]{10}$";
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String REGEX_EMAIL2 = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
    public static final String REGEX_CHINESE = "^[一-龥],{0,}$";
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    public AccountValidatorUtil() {
    }

    public static boolean isUsername(String username) {
        return Pattern.matches("^[a-zA-Z]\\w{5,20}$", username);
    }

    public static boolean isPassword(String password) {
        return Pattern.matches("^[a-zA-Z0-9]{6,20}$", password);
    }

    public static boolean isMobile(String mobile) {
        return Pattern.matches("^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", mobile);
    }

    public static boolean isMobileSimple(String mobile) {
        return Pattern.matches("^[1][0-9]{10}$", mobile);
    }

    public static boolean isEmail(String email) {
        return Pattern.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", email) || Pattern.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$", email);
    }

    public static boolean isChinese(String chinese) {
        return Pattern.matches("^[一-龥],{0,}$", chinese);
    }

    public static boolean isIDCard(String idCard) {
        return Pattern.matches("(^\\d{18}$)|(^\\d{15}$)", idCard);
    }

    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)", ipAddr);
    }
}
