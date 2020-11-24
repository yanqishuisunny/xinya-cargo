package com.cargo.feign.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * <p>
 * 调度池-高德地图-位置
 * </p>
 *
 * @author 开发者
 * @since 2019-11-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cargo_location")
public class MapLocation {

    private static final long serialVersionUID = 1L;

   /* *//**
     * 主键
     *//*
    @Id
    private String id;
    *//**
     * app用户id
     *//*
    @Indexed
    private String userId;
    *//**
     * 用户名
     *//*
    @Indexed
    private String userName;*/

    /**
     * 定位纬度
     */
    @ApiModelProperty("定位纬度")
    private String lat;

    /**
     * 定位经度
     */
    @ApiModelProperty("定位经度")
    private String lon;

   /* *//**
     * 定位精度 单位 米
     *//*
    @ApiModelProperty("定位精度 单位 米")
    private String accuracy;

    *//**
     * 海拔高度信息
     *//*
    @ApiModelProperty("海拔高度信息")
    private String altitude;

    *//**
     * 室内定位建筑物ID
     *//*
    @ApiModelProperty("室内定位建筑物ID")
    private String buildingId;

    *//**
     * 室内定位楼层
     *//*
    @ApiModelProperty("室内定位楼层")
    private String floor;

    *//**
     * 当前位置的POI名称
     *//*
    @ApiModelProperty("当前位置的POI名称")
    private String poiName;

    *//**
     * 当前位置所处AOI名称
     *//*
    @ApiModelProperty("当前位置所处AOI名称")
    private String aoiName;

    *//**
     * 地理位置名称
     *//*
    @ApiModelProperty("地理位置名称")
    private String adr;


    *//**
     * 定位时间
     *//*
    @ApiModelProperty("定位时间")
    private String utc;

    *//**
     * 速度 单位 米/秒
     *//*
    @ApiModelProperty("速度 单位 米/秒")
    private String spd;

    *//**
     * 国家
     *//*
    @ApiModelProperty("国家")
    private String country;

    *//**
     * 方向
     *//*
    @ApiModelProperty("方向")
    private String drc;

    *//**
     * 省id
     *//*
    @ApiModelProperty("省id")
    private String provinceId;

    *//**
     * 省
     *//*
    @ApiModelProperty("省")
    private String province;

    *//**
     * 城市id
     *//*
    @ApiModelProperty("城市id")
    private String cityId;

    *//**
     * 城市
     *//*
    @ApiModelProperty("城市")
    private String city;

    *//**
     * 城区
     *//*
    @ApiModelProperty("城区")
    private String district;

    *//**
     * 街道
     *//*
    @ApiModelProperty("街道")
    private String street;

    *//**
     * 街道门牌号信息
     *//*
    @ApiModelProperty("街道门牌号信息")
    private String streetNum;

    *//**
     * 城市编码信息
     *//*
    @ApiModelProperty("城市编码信息")
    private String cityCode;

    *//**
     * 区域编码信息
     *//*
    @ApiModelProperty("区域编码信息")
    private String adCode;


    private LocalDateTime locationTime;*/


}
