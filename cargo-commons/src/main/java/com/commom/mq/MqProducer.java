package com.commom.mq;

import org.springframework.stereotype.Component;

@Component
public class MqProducer {

    public static String GpsQueue = "MqGpsLocation";


//    public static String MqGpsQueue = "MqGps";


//    public void sendLocationMsg(String msg) {
//        rabbitTemplate.convertAndSend(GpsQueue, msg);
//    }

}
