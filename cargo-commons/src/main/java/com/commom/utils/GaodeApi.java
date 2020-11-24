package com.commom.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.commom.exception.BussException;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 高德地图--通过地址获取经纬度
 * @author Alex
 * @date 2019-09-21 15:09
 */
@Slf4j
public class GaodeApi {
    /**
     * 高德地图通过地址获取经纬度
     * 389880a06e3f893ea46036f030c94700
     */
    public static String httpURLConectionGET(String address) {
        //"http://restapi.amap.com/v3/geocode/geo?address=上海市东方明珠&output=JSON&key=xxxxxxxxx";
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

    public static Map<String,String> parseLocation(String address, String area){
        String gaodeAdress = area + address;
        String location = GaodeApi.httpURLConectionGET(gaodeAdress);
        if(Strings.isNullOrEmpty(location)){
            gaodeAdress = area;
            location = GaodeApi.httpURLConectionGET(area);
        }

        if(!Strings.isNullOrEmpty(location)){
            List<String> list = Splitter.on(",").trimResults().splitToList(location);
            Map<String,String> map = new HashMap<>(3);
            map.put("lat",list.get(1));
            map.put("lon",list.get(0));
            map.put("address",gaodeAdress);
            return map;
        }
        return null;
    }


    /**
     * 根据输入框的值带出所选的内容
     * @param keywords
     * @return
     */
    public static String getInputPrompt(String keywords,String city){
        if (ObjectUtils.isEmpty(keywords)) {
            return null;
        }
        String geturl = "https://restapi.amap.com/v3/assistant/inputtips?key=eef3144814069b6dcfc1464292f9c355&keywords="+keywords;
        if (!ObjectUtils.isEmpty(city)) {
            geturl = geturl + "&city=" + city + "&citylimit=" + true;
        }
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
            log.info("Get=="+sb.toString());
            JSONObject a = JSON.parseObject(sb.toString());
            if("1".equals(a.getString("status"))){
               return a.getString("tips");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
            return null;
        }
    }

    /**
     * 获取逆地理编码
     * @param location
     * @return
     */
    public static String LocationAddress(String location){
        if (ObjectUtils.isEmpty(location)) {
            return null;
        }
        String geturl = "https://restapi.amap.com/v3/geocode/regeo?key=eef3144814069b6dcfc1464292f9c355&location="+location;
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
            log.info("Get LocationAddress =="+sb.toString());
            JSONObject a = JSON.parseObject(sb.toString());
            if("1".equals(a.getString("status"))){
                JSONObject object = JSON.parseObject(a.get("regeocode").toString());
                return object.getString("formatted_address");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
            return null;
        }
    }



    /**
     * 调用高德获取两地间驾车时间
     * @param fromAddress
     * @param toAddress
     * @return
     */
    public static String getRunTimeByLonLat(String fromAddress, String toAddress){

        if (ObjectUtils.isEmpty(fromAddress)) {
            throw new BussException("起始地址不得为空");
        }
        if (ObjectUtils.isEmpty(toAddress)) {
            throw new BussException("目的地址不得为空");
        }
        String duration = "";
        String geturl = "https://restapi.amap.com/v3/direction/driving?extensions=all&key=eef3144814069b6dcfc1464292f9c355"+"&origin="+fromAddress+"&destination="+toAddress;
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
            log.info("调用高德获取两地间驾车时间结果： =="+sb.toString());
            JSONObject a = JSON.parseObject(sb.toString());

            if("1".equals(a.getString("status"))){
                JSONObject route = a.getJSONObject("route");
//                JSONObject route = JSON.parseObject("route");
                if(route!=null){
                    JSONArray paths = route.getJSONArray("paths");
//                    JSONArray paths = JSON.parseArray("paths");
                    if(paths!=null&paths.size()>0){
                        JSONObject path = paths.getJSONObject(0);
                        duration = path.getString("duration");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussException("调用高德获取两地间驾车时间失败："+e.getMessage());
        }

        return duration;
    }


    public static Double getDistance(String origins,String destination) {
        String geturl = "https://restapi.amap.com/v3/distance?key=eef3144814069b6dcfc1464292f9c355&origins="+origins+"&destination="+destination;
        String location = "";
        JSONArray distanceArr = null;
        Double result = null;
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
            distanceArr = JSON.parseObject(sb.toString()).getJSONArray("results");
            if(distanceArr == null ||distanceArr.size() == 0 || distanceArr.getJSONObject(0).getString("distance") == null){
                log.warn("无效地址 出发地经纬度=> {}, 目的地经纬度=> {}",origins,destination);
                return null;
            }
            double a = distanceArr.getJSONObject(0).getDouble("distance");

            //rint(x)：x取整为它最接近的整数，如果x与两个整数的距离相等，则返回其中为偶数的那一个
            result = Math.rint(a/100)/10;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
        }
        return result;
    }
    public static void main(String[] args) {

    }
}
