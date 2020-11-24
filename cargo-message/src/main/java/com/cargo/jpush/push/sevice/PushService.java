package com.cargo.jpush.push.sevice;

import com.cargo.jpush.push.model.dto.PushDto;

/**
 * @Auther: xinzs
 * @Date: 2020/10/26 10:02
 */
public interface PushService {

    public void sendPushMsg(PushDto dto);
}
