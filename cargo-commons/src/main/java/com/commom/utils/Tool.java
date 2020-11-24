package com.commom.utils;

import com.google.common.base.Strings;

import java.util.UUID;

public class Tool {

    public static String substring(String str,int index){
        if(!Strings.isNullOrEmpty(str)){
            return str.substring(0,str.length() < index ? str.length() : index);
        }else{
            return str;
        }
    }

    public static String uuId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 数据脱敏
     * @return
     */
    public static String desensitization(String data){
        if(Strings.isNullOrEmpty(data))
            return data;

        int len = data.length();
        if(len == 11){
            String pre = data.substring(0,3);
            String suf = data.substring(len - 3,len);
            return pre + "*****" + suf;
        }else if(len > 2){
            String pre = data.substring(0,2);
            return pre + "***";
        }else {
            return data;
        }
    }
}
