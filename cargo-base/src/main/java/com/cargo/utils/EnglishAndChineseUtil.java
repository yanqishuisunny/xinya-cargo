package com.cargo.utils;

import com.mysql.jdbc.StringUtils;
import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Objects;

public class EnglishAndChineseUtil {

    /**
     * 获取汉字拼音的首字母简写
     * 刘备----LB
     *
     * @param hanzi
     * @return
     */
    public static String getHanziToSimpleName(String hanzi) {
        String result = null;
        if (null != hanzi && !"".equals(hanzi)) {
            char[] charArray = hanzi.toCharArray();
            StringBuffer sb = new StringBuffer();
            for (char ch : charArray) {
                // 逐个汉字进行转换， 每个汉字返回值为一个String数组（因为有多音字）
                String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(ch);
                if (null != stringArray) {
                    sb.append(stringArray[0].charAt(0));
                }
            }
            if (sb.length() > 0) {
                result = sb.toString().toUpperCase();
            }
        }
        return result;
    }


    /**
     * 获取中文拼音首字母 并且大写
     */
    //返回省份和城市首汉字大写字母
    public static String getHanziInitials(String hanzi) {
        String simpleName = getHanziToSimpleName(hanzi);
        if (StringUtils.isNullOrEmpty(simpleName)) {
            return "";
        }
        return simpleName.length() == 1 ? simpleName : simpleName.substring(0, 1);
    }

    public static String getHanziPinYin(String hanzi) {
        String result = null;

        if (null != hanzi && !"".equals(hanzi)) {
            char[] charArray = hanzi.toCharArray();
            StringBuffer sb = new StringBuffer();
            for (char ch : charArray) {
                // 逐个汉字进行转换， 每个汉字返回值为一个String数组（因为有多音字）
                String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(ch);
                String zimu = "";
                for (int i = 0; i < stringArray.length; i++) {
                    zimu += stringArray[i];//转换成一个字符串
                }
                if (null != stringArray) {
                    // 把第几声这个数字给去掉
                    sb.append(stringArray[0].replaceAll("\\d", ""));
                }
            }
            if (sb.length() > 0) {
                result = sb.toString();
            }
        }


        return result;
    }

    /**
     * 张三    -----》  ZhangSan
     *
     * @param hanzi
     * @return
     */
    public static String getHanziPinYinAndFirstUp(String hanzi) {
        String result = null;
        if (!org.apache.commons.lang3.StringUtils.isEmpty(hanzi)) {
            char[] charArray = hanzi.toCharArray();
            StringBuffer sb = new StringBuffer();
            for (char ch : charArray) {
                // 逐个汉字进行转换， 每个汉字返回值为一个String数组（因为有多音字）
                String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(ch);
                String zimu = "";
                if (Objects.nonNull(stringArray)) {
                    for (int i = 0; i < stringArray.length; i++) {
                        zimu += stringArray[i];//转换成一个字符串
                    }
                    if (null != stringArray) {
                        // 把第几声这个数字给去掉
                        String s = stringArray[0].replaceAll("\\d", "");
                        String small = s.substring(0, 1);
                        String big = small.toUpperCase();
                        String replaceFirst = s.replaceFirst(small, big);
                        sb.append(replaceFirst);
                    }
                }

            }
            if (sb.length() > 0) {
                result = sb.toString();
            }
        }
        return result;
    }


}
