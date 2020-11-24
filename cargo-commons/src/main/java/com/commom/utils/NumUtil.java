package com.commom.utils;

import java.util.Random;

/**
 * 数字处理
 */
public class NumUtil {


    /**
     * 生成六位随时数
     * @return
     */
    public static int random(){
        return (int)((Math.random()*9+1)*100000);
    }

    /**
     * @param : [digits]
     * @return : java.lang.String
     * @Description : 生成验证码
     * @auther : 李雷
     * @date : 2019/4/16 10:30
     */
    public static String getCode(int len) {
        StringBuilder code = new StringBuilder();
        int i = 0;
        do {
            int temp = new Random().nextInt(10);
            if( ( i == 0 ) && temp <= 0 ){
                continue;
            }
            code.append(temp);
            i++;
        } while (i < len);
        return code.toString();
    }
}
