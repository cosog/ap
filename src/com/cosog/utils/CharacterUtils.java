package com.cosog.utils;

import org.apache.commons.lang.StringUtils;

public class CharacterUtils {
	private CharacterUtils() {
    }
 
    /**
     * 判断字符串是否为中文
     *
     * @param inputStr 字符串
     * @return boolean
     */
    public static boolean isChinese(String inputStr) {
        return match(inputStr, "[\u4E00-\u9FA5]+");
    }
 
    /**
     * 判断单个字符是否为英文
     *
     * @param inputStr 字符串
     * @return boolean
     */
    public static boolean isEnglish(String inputStr) {
        return match(inputStr, "[a-zA-Z]+");
    }
 
    /**
     * 判断字符串是否为日文
     *
     * @param inputStr 字符串
     * @return boolean
     */
    public static boolean isJapanese(String inputStr) {
        return match(inputStr, "[\u3040-\u309F\u30A0-\u30FF\u31F0-\u31FF]+");
    }
 
    /**
     * 判断字符串是否为韩文
     *
     * @param inputStr 字符串
     * @return boolean
     */
    public static boolean isKorea(String inputStr) {
        return match(inputStr, "[\u3130-\u318F\uAC00-\uD7A3]+");
    }
 
    /**
     * 判断字符串是否为俄文
     *
     * @param inputStr 字符串
     * @return boolean
     */
    public static boolean isRussian(String inputStr) {
        return match(inputStr, "[\u0400-\u052F]+");
    }
 
    private static boolean match(String inputStr, String regex) {
        String str = replace(inputStr);
        if (StringUtils.isBlank(str)) {
            return true;
        }
        return str.matches(regex);
    }
 
    /**
     * 替换标点符号、表情、数字、空格、制表符、换行
     *
     * @param inputStr 输入字符
     * @return 替换后的字符
     */
    private static String replace(String inputStr) {
        if (StringUtils.isBlank(inputStr)) {
            return inputStr;
        }
        return inputStr.replaceAll("\\p{Punct}", "")
            .replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]+", "")
            .replaceAll("\\d+", "")
            .replaceAll("\\s*|\t|\r|\n", "");
    }
}
