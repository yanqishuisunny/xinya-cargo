package com.cargo.jpush.push.sevice.impl;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cargo.jpush.JPushClient;
import com.cargo.jpush.push.PushResult;
import com.cargo.jpush.push.model.Options;
import com.cargo.jpush.push.model.Platform;
import com.cargo.jpush.push.model.PushPayload;
import com.cargo.jpush.push.model.audience.Audience;
import com.cargo.jpush.push.model.audience.AudienceTarget;
import com.cargo.jpush.push.model.dto.PushDto;
import com.cargo.jpush.push.model.notification.AndroidNotification;
import com.cargo.jpush.push.model.notification.Notification;
import com.cargo.jpush.push.sevice.PushService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: xinzs
 * @Date: 2020/10/26 10:02
 */
@Service
public class PushServiceImpl implements PushService {

    @Value("${jPush:push:appKey}")
    private String APP_KEY;
    @Value("${jPush:push:masterSecret}")
    private String MASTER_SECRET;

    protected static final Logger LOG = LoggerFactory.getLogger(PushServiceImpl.class);


    @Override
    public void sendPushMsg(PushDto dto) {

        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        PushPayload payload = buildPushObject_all_alias_alert(dto);
        try {
            PushResult result = jpushClient.sendPush(payload);
            int status = result.getResponseCode();
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
    }


    public static PushPayload buildPushObject_all_alias_alert(PushDto dto) {

        //audience：推送目标
//        Audience audience = Audience.newBuilder().build();
        Audience audience = null;
        String audienceStr = dto.getAudience();
        String[] tag = dto.getTag();
        String[] userId = dto.getUserId();
        //转一下极光ID；考虑分组1000个
        if(audienceStr!=null&&audienceStr.equals("all")){
             audience = Audience.all();

        }else {
             audience = Audience.newBuilder()
                    .addAudienceTarget(AudienceTarget.tag(tag))
                    .addAudienceTarget(AudienceTarget.registrationId(userId))
                    .build();
        }


        String title = dto.getTitle();
        //通知内容
        String alert = dto.getAlert();
        //通知内容（扩展）
        Map<String, String> extras = dto.getExtras();

        ////跳转地址
        JsonObject jsonObject = new JsonObject();
        Map<String, String> intent = dto.getIntent();
        Iterator<Map.Entry<String, String>> iterator = intent.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            jsonObject.addProperty(next.getKey(),next.getValue());
        }

        Integer timeToLive = dto.getTimeToLive();


        Notification notification = Notification.newBuilder()
                .setAlert(alert)
                .addPlatformNotification(
                        AndroidNotification.newBuilder()
                        .setTitle(title)
                        .addExtras(extras)
                        .setIntent(jsonObject)
                        .build()
                ).build();


        return PushPayload.newBuilder()
                //推送平台
                .setPlatform(Platform.all())
                //推送目标（推送设备指定）
                .setAudience(audience)
                //通知（通知内容体）
                .setNotification(notification)
                //Options可选参数:apns_production   APNs 是否生产环境;time_to_live  离线消息保留时长(秒)
                .setOptions(Options.newBuilder().setApnsProduction(false).setTimeToLive(86000).build())
                .build();
    }


}
