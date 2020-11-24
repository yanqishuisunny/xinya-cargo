package com.cargo.utils;

import org.springframework.stereotype.Component;

/**
 * 数字处理
 */
@Component
public class UserUtil {


    //redis用户登录错误锁住次数
    public static final String REDIS_LOGIN_ACCOUT_NUMBER = "pass:login:accout:num:";


    //redis用户登录错误锁住时间
    public static final String REDIS_LOGIN_ACCOUT_LOCK = "pass:login:accout:lock:";


}
