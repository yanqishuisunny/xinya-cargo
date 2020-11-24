package com.cargo.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title： Location </p>
 * <p>Description：定位数据 </p>
 * <p>Company：ail </p>
 *
 * @author sujunxuan
 * @version V1.0
 * @date 2020/1/13 14:58
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "location_lately")
public class Location implements Serializable {

    @Id
    private String id;

    /**
     * 车牌号
     */
    @Indexed
    private String vehicleNo;

    /**
     * 设备IMEI
     */
    private String imei;

    /**
     * 纬度
     */
    private Double lat;

    /**
     * 经度
     */
    private Double lng;

    /**
     * 状态
     */
    private Integer status;


    /**
     * 定位类型: 卫星定位-GPS, 基站定位-LBS, WIFI定位-WIFI, 蓝牙定位-BEACON
     */
    private String locationType;

    /**
     * 定位时间
     */
    private Date locationTime;

    /**
     * 速度 (单位:km/h)
     */
    private Double speed;

    /**
     * 方向
     */
    private String direction;

    /**
     * 位置
     */
    private String address;

    /**
     * 省
     */
    private String province;
    /**
     * 省
     */
    private String provinceCode;

    /**
     * 市
     */
    private String city;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区
     */
    private String country;

    /**
     * 区编码
     */
    private String countryCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 里程统计
     */
    private String mileage;

    /**
     * 供应商id
     */
    private String supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 所属承运商
     */
    private String carrier = "";

    /**
     * 离上一个点的距离
     */
    private String distance;

    /**
     * 数据来源平台(1:gps系统 2:安卓)
     */
    private Integer platform;

}
