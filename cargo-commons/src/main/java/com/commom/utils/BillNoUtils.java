package com.commom.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author Alex
 * 2019 8 14
 * 单据号的生成规则（订单、运单等）
 * 前面两位固定（例如订单为71，运单为73）、6位YYMMDD(年月日)、6位随机数字(搞随机数字的目的是不想让人从单号知道单量)
 */
public class BillNoUtils {

    public static String wayBillNo() {
        StringBuffer wayBillNo = new StringBuffer();
        DateFormat df = new SimpleDateFormat("yyMMdd");
        wayBillNo.append(df.format(new Date()));
        wayBillNo.append((int)((Math.random()*9+1)*100000));
        return wayBillNo.toString();
    }


    public static void main(String[] args) {
        System.out.println("生成的运单号为：73"+wayBillNo());
    }

}
