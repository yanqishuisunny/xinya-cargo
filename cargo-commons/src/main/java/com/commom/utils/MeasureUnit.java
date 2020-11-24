package com.commom.utils;

/**
 * 计量单位转换
 */

public class MeasureUnit {

    /**
     * 克  转 吨
     * @param gram
     * @return
     */
    public static double gramToTon(double gram){
       return gram / 1000000;
    }

    /**
     * 吨 转 克
     * @param ton
     * @return
     */
    public static int tonToGram(double ton){
        return (int) (ton * 1000000);
    }

    /**
     * 分 转 元
     * @param fen
     * @return
     */
    public static double fenToYuan(double fen){
        return fen / 10000;
    }
}
