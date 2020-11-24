package com.commom.cache;

import lombok.Data;

/**
 * 请求头信息
 */
@Data
public class HeaderDto {

    /**
     * 设备ID
     */
    private String appid;
    /**
     * token
     */
    private String token;
    /**
     * sgin 加密串
     */
    private String sign;
    /**
     * 随机数
     */
    private String rnd;
    /**
     * 时间戳
     */
    private long time;

    /**
     * IP
     */
    private String ip;

    /**
     * Referer  来源
     */
    private String referer;
    /**
     * 登录端[PC( 0:承运商 1:货主 4平台) APP( 0:承运商 1:货主 3:车主 )]
     *
     */
    private String versionType;

    /**
     * 版本(app端版本)
     */
    private String version;


}
