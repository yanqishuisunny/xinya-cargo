package com.cargo.utils;

import com.alibaba.fastjson.JSON;
import com.cargo.location.model.Location;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeoCodeUtils {

    private static final Logger logger = LoggerFactory.getLogger(GeoCodeUtils.class);


    public  static Location getAddress(String coordinate) {
        if(StringUtils.isEmpty(coordinate)){
            return null;
        }
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //创建url
        //output = json 返回结果数据将会以JSON结构构成
        // batch 参数设置为 true 时进行批量查询操作,设置为 false 时进行单点查询,最多查询20个
        String urlStr = "https://restapi.amap.com/v3/geocode/regeo?output=json&batch=true&key=eef3144814069b6dcfc1464292f9c355&";
        String url = urlStr + coordinate;
        Location location = new Location();
        try {
            //生成get 请求对象
            HttpGet httpGet = new HttpGet(url);
            //发送请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            //获取响应实体对象
            HttpEntity entity = response.getEntity();
            //转换成utf-8 编码的字符串
            String entityStr = EntityUtils.toString(entity, "utf-8");
            //转换成map 的集合
            Map map = JSON.parseObject(entityStr, Map.class);
            //获取逆地理编码列表列表
            //batch=true,此时 regeocodes 标签返回；batch=false，会返回 regeocode对象；
            String regeocodes = map.get("regeocodes").toString();
            //字符串转换成  List<Map<String,Object>> 类型的集合
            List<Map<String, Object>> list = JSON.parseObject(regeocodes, new ArrayList<Map<String, Object>>().getClass());
            Map<String, Object> locationMap = (Map<String, Object>) list.get(0).get("addressComponent");
            if(null!=locationMap.get("province")){
                location.setProvince((String) locationMap.get("province"));
            }
            if(!ObjectUtils.isEmpty(locationMap.get("city"))){
                location.setCity((String) locationMap.get("city"));
            }
            if(null!=locationMap.get("citycode")){
                location.setCityCode((String) locationMap.get("citycode"));
            }
            if(!ObjectUtils.isEmpty(locationMap.get("adcode"))){
                location.setCountryCode((String) locationMap.get("adcode"));
            }
            if(!ObjectUtils.isEmpty(locationMap.get("district"))){
                location.setCountry((String) locationMap.get("district"));
            }
            //关闭资源
            httpClient.close();
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }


    public static void main(String[] args) throws Exception {
        String coordinate = "location=122.32446740083907,40.62540552541249";
        Location location = getAddress(coordinate);
        logger.info(String.valueOf(location));
    }

}
