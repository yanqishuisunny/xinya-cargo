package com.commom.core;

/**
 * 请求响应码枚举
 * Created by lether on 2016/7/28.
 */
public enum BusCode implements IBusCode {

    /**
     * "1000","成功"
     */
    SUCCESS(1000,"成功"),
    /**
     * "1001","业务失败"
     */
    FAIL(1001,"业务失败"),

    /**
     * "1004","token过期"
     */
    TOKEN_EXPIRATION(7004,"token过期"),

    /**
     * "1005","token丢失"
     */
    TOKEN_LOSE(7003,"token丢失"),

    OTHER_LOGIN_ERROR(5000,"已在其他设备登录"),

    /**
     * "1003","无效token"
     */
    TOKEN_INVALID(7002,"无效token"),

    /**
     * 无效的枚举类型
     */
    INVALID_ENUM(2004,"无效的枚举类型"),


    DEVICEID_NULL(7001,"设备主键不能为空"),

    DEVICE_ERROR(7001,"没有此设备信息"),

    DEVICE_CREATBY_NULL(7001,"创建人不能为空"),

    DEVICE_EXPIRATION_NULL(7001,"到期时间不能为空"),

    DEVICE_REMARK_NULL(7001,"备注不能为空"),

    DEVICE_IMEI_NULL(7001,"设备IMEI不能为空"),

    DEVICE_TYPE_NULL(7001,"设备型号不能为空"),

    DEVICE_SUPPLIERID_NULL(7001,"供应商Id不能为空"),

    DEVICE_SUPPLIERCODE_NULL(7001,"供应商code不能为空"),

    DEVICE_SIM_NULL(7001,"sim卡不能为空"),

    DEVICE_IMEI_HAVE(7001,"设备IMEI卡号已经存在"),

    VEHICLEID_NULL(7001,"车辆ID不能为空"),

    VEHICLENO_NULL(7001,"车牌号不能为空"),

    VEHICLENO_BINDING(7001,"车牌号已经绑定到其他设备"),

    CAR_DEVICE_MAPPING_ID_NULL(7001,"车辆设备关系主键不能为空"),

    CAR_DEVICE_MAPPING_ERROR(7001,"没有此车辆设备关系信息"),

    CAR_DEVICE_MAPPING_DEVICEID_EXIST(7001,"传入的设备主键已经绑定到别的车辆上"),

    APPKEY_NULL(7001,"账号不能为空"),

    SUPPLIERNAME_NULL(7001,"供应商名称不能为空"),

    SUPPLIERCODE_NULL(7001,"供应商编号不能为空"),

    APPSECRET_NULL(7001,"账密不能为空"),

    DESCRIPTION_NULL(7001,"描述不能为空"),

    SUPPLIERID_NULL(7001,"供应商主键不能为空"),

    SUPPLIE_INFO_NULL(7001,"没有此供应商信息"),

    LOGIN_JIMI_ERROE(7001,"获取几米登录数据异常"),

    SUPPLIER_EXIST(7001,"供应商已经存在"),

    CARRIER_NULL(7001,"所属车主/承运商必传"),



    ;


    private int code;
    private String Message;


    BusCode(int code, String message) {
        this.code = code;
        this.Message = message;
    }

    /**
     * 方法描述: 枚举转换
     *
     * @param code code
     * @return BusCode BusCode
     */
    public static BusCode parseOf(int code) {
        for (BusCode item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

    public static BusCode parseOf(String key) {
        return BusCode.parseOf(Integer.parseInt(key));
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.Message;
    }

    @Override
    public String toString() {
        return "BusCode{" +
                "code=" + code +
                ", msg='" + Message + '\'' +
                '}';
    }}
