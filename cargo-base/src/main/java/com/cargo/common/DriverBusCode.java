package com.cargo.common;

import com.commom.core.IBusCode;

public enum DriverBusCode implements IBusCode {

    SELECT_DATA(4101, "请选择数据！"),

    SELECT_NO_DATA(4102, "没有找到相关数据"),

    NO_VEHICLE_OWNER_MSG(4103, "没有完善车主信息，不能添加司机"),

    NO_ID_CARD_CHECK(4104, "您还没有实名认证，请先进行实名认证"),

    NO_DRIVER_LICENSE(4105, "您还没有进行从业资格认证，请先进行从业资格认证"),

    NO_AUDIT_SUCCESS(4106, "您提交的资料正在审核中，请您耐心等待"),

    AUDIT_ERROR(4109, "您提交的资料可能有误，已被平台拒绝，请重新上传正确的资料"),

    ERROR_EDIT_STATUS(4107, "只有【待实名认证】、【待从业资格认证】、【审核拒绝】状态才能进行修改操作"),

    ACCCPT_INVIT_FAIL(4108,"当前已绑定服务单位，无法重复绑定"),

    UNBOUND_FAIL_CAROWNER(4109,"当前司机有未完成的运输任务，无法解除绑定"),

    UNBOUND_FAIL_DRIVER(4109,"您当前正在执行运输任务，无法解除合作关系"),

    APP_SIGN(5101,"您当天已经签到成功!"),

    APP_RULE(5102,"活动暂时没有开启!"),

    DRIVER_LICENSE_ERROR(5103,"请上传本人驾驶证!"),

    DRIVER_REPEAT(5104,"司机不能重复!"),

    CAR_REPEAT(5105,"车辆不能重复!"),

    DRIVER_CONFLICT(5106,"司机已分配了别的作业单!"),

    CAR_CONFLICT(5107,"车辆已分配了别的作业单!"),

    DEL_DRIVER_FAIL(5108,"司机正在执行运输任务,无法删除!"),

    AUDIT_FAIL(5109,"待审核状态才能执行此操作"),

    WAY_CAR_REPEAT(5105,"车辆（%s）已分配作业单，不可重复分配!"),


    ;





    private int code;
    private String message;


    DriverBusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 方法描述: 枚举转换
     *
     */
    public static DriverBusCode parseOf(int code) {
        for (DriverBusCode item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

    public static DriverBusCode parseOf(String key) {
        return parseOf(Integer.parseInt(key));
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "BusCode{" +
                "code=" + code +
                ", msg='" + message + '\'' +
                '}';
    }
}
