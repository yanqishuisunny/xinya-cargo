package com.cargo.feign.entity;

import lombok.Data;

/**
 * 返回省市区的名字加代码
 *
 * @author 赵恒亮
 */
@Data
public class AreCodeEntry {

    /**
     * 省
     */
    private String province;
    /**
     * 省代码
     */
    private String provinceCode;
    /**
     * 市
     */
    private String city;
    /**
     * 市代码
     */
    private String cityCode;

    /**
     * 区
     */
    private String area;
    /**
     * 区代码
     */
    private String areaCode;

    /**
     * 街道
     */
    private String town;

    /**
     * 街道码
     */
    private String townCode;
}
