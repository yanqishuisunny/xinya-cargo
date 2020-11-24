package com.cargo.rocketmq;

import lombok.Value;
import org.apache.poi.ss.formula.functions.T;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 生产者
 */
//@Component
public class Producer {

//    @Value("${rocketmq.config.producerGroup}")
//    private String group;
//
//    @Value("${rocketmq.config.namesrvAddr}")
//    private String namesrvAddr;
//
//    @PostConstruct
//    public void  addMessage(List<T> t)  throws Exception{
//        DefaultMQProducer producer = new DefaultMQProducer(group);
//        producer.setNamesrvAddr(namesrvAddr);
//        producer.setInstanceName(group);
//        producer.start();
//        for (T zz:t){
//            Message msg = new Message("broker-a", // topic 主题名称
//                    "TagA", // tag 临时值
//                    zz.toString().getBytes()// body 内容
//            );
//            //send()发送
//            SendResult sendResult = producer.send(msg);
//            producer.shutdown();
//        }
//    }
    public static void main(String[] args) throws Exception {
        //创建一个消息的生产者
        // producerGroup：一般发送同样消息的Producer，归为同一个Group，应用必须设置，并保证命名唯一
        DefaultMQProducer producer = new DefaultMQProducer("broker-a");
        //设置名称srv地址
        producer.setNamesrvAddr("10.10.10.111:9876;10.10.10.212:9876");
        //实例名称
        producer.setInstanceName("broker-a");
        //启动
        producer.start();

        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000); // 每秒发送一次MQ
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            Message msg = new Message("broker-a", // topic 主题名称
                    "TagA", // tag 临时值
                    ("itmayiedu-" + i).getBytes()// body 内容
            );
            //send()发送
            SendResult sendResult = producer.send(msg);
            //SendResult:发送消息结果
            System.out.println(sendResult.toString());
        }

        //关掉
        producer.shutdown();
    }


}


