package com.commom.snowflake;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class IdGeneratorSnowflake {

    private long workerId = 0;

    private long datacenterId = 1;

    private Snowflake snowflake = IdUtil.createSnowflake(workerId,datacenterId);

    @PostConstruct
    public void init(){
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("当前机器workerId: {}" + workerId);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("当前机器的workerId获取失败",e);
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
    }


    public synchronized long snowflakeId(){
        return snowflake.nextId();
    }

    public synchronized long snowflakeId(long workerId , long datacenterId){
       Snowflake snowflake = IdUtil.createSnowflake(workerId , datacenterId);
        return snowflake.nextId();
    }

    public synchronized List<Long> getSnowflakeIds(){
        List<Long> list = new ArrayList<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for(int i = 0; i<= 20; i++){
            threadPool.submit(()->{
                System.out.println(this.snowflakeId());
                list.add(this.snowflakeId());
            });
        }
        threadPool.shutdown();
        return list;
    };



    public static void main(String[] args){
        System.out.println(new IdGeneratorSnowflake().snowflakeId());
    }


}
