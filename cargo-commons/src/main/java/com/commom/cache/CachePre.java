package com.commom.cache;

public interface CachePre {

    /**
     * 登录jwt
     */
    String LOING_SHIRO_JWT_ID = "login:shiro:jwt:id:";
    String LOING_SHIRO_USER_ID = "login:shiro:user:id:";
    String LOING_USER_PERMISSION_ID = "login:user:permisson:id:";

    /**
     * 登录短信key
     */
    String LOING_SMS_PHONE = "login:sms:phone:";
    String LOING_SMS_FORGET = "login:sms:forget:";
    String LOING_SMS_REGISTER = "login:sms:register:";
    String LOING_SMS_SENDER_CONSIGNEE = "login:sms:sender_consignee:";

    /**
     * 手机发送验证码 次数(每小时请求的次数)
     */
    String PHONE_SMS_HOUR_SEND_COUNT = "phone:sms:hour:send:count:";

    /**
     * 手机发送验证码 次数(每天请求的次数)
     */
    String PHONE_SMS_DAY_SEND_COUNT = "phone:sms:day:send:count:";

    /**
     * 车辆最后定位数据
     */
    String DRIVERS_LOCATION_LAST = "gps:driver:location";


}
