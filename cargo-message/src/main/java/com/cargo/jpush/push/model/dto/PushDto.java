package com.cargo.jpush.push.model.dto;

import com.cargo.jpush.push.model.Platform;
import com.cargo.jpush.push.model.audience.Audience;
import com.cargo.jpush.push.model.notification.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: xinzs
 * @Date: 2020/11/3 17:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushDto implements Serializable {

    //推送平台设置
    private Platform platform;
    //广播时使用，传“all”
    private String audience;
    //推送目标（标签名）
    private String[] tag;
    //推送目标（用户ID）
    private String[] userId;
    //通知标题
    private String title;
    //通知内容
    private String alert;
    //通知内容（扩展）
    private Map<String, String> extras;
    //跳转地址
    private Map<String, String> intent;
    //离线消息保留(0：不保留，1：保留)
    private Integer timeToLive;

    private Integer type;

    private String version;
}
