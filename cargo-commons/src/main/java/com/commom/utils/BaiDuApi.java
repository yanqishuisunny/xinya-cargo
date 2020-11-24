package com.commom.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 获取货车导航时间
 * @author Carlos
 * @date 2020-3-19 15:09
 */
@Slf4j
public class BaiDuApi {
    /**
     * 高德地图通过地址获取经纬度
     * 389880a06e3f893ea46036f030c94700
     */
    public static String httpURLConectionGET(String address) {
        String geturl = "http://restapi.amap.com/v3/geocode/geo?key=eef3144814069b6dcfc1464292f9c355&address="+address;
        String location = "";
        try {
            URL url = new URL(geturl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            System.out.println("Get=="+sb.toString());
            JSONObject a = JSON.parseObject(sb.toString());

            String geocodes = a.get("geocodes") + "";
            if (Strings.isNullOrEmpty(geocodes)){
                log.warn("无效地址 => {}",address);
                return null;
            }

            JSONArray sddressArr = JSON.parseArray(a.get("geocodes").toString());

            if(ObjectUtils.isEmpty(sddressArr)){
                log.warn("无效地址 => {}",address);
                return null;
            }

            System.out.println(sddressArr.get(0));
            JSONObject c = JSON.parseObject(sddressArr.get(0).toString());
            location = c.get("location").toString();
            System.out.println(location);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
        }
        return location;
    }



    public static void main(String[] args) {
//        String s = LocationAddress("121.447209,31.154628");
//        System.out.println("地址"+s);
    }

}
