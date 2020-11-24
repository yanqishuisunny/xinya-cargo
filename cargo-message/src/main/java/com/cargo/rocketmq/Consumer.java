package com.cargo.rocketmq;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.ArrayList;
import java.util.List;

/**
 * 消费者
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //创建一个消费者
        //consumerGroup:做同样事情的Consumer归为同一个Group，应用必须设置，并保证命名唯一
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("broker-a");
        //设置名称srv地址
        consumer.setNamesrvAddr("10.10.10.111:9876");
        //实例名称
        consumer.setInstanceName("broker-a");
        //实现订阅
        try {
            consumer.subscribe("broker-a", "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        //注册消息监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println(msg.getMsgId()+"---"+new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者
        consumer.start();
        System.out.println("Consumer Started.");
//        List<String> list = new ArrayList<>();
//        list.add("test1");
//        list.add("test2");
//        list.add("test3");
//        list.add("test4");
//        list.add("test5");
//        list.add("test6");
//        String json = JSON.toJSONString(list);
//        System.out.println(json);
    }
}
