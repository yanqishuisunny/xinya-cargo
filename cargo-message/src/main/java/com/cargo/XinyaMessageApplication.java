package com.cargo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Auther: xinzs
 * @Date: 2020/10/22 10:09
 */
@SpringBootApplication(scanBasePackages = {"com.cargo","com.commom","com.xsungroup.tms"})
@MapperScan(value = {"com.cargo.**.mapper","com.commom.supper"})
@EnableMongoRepositories
@EnableScheduling
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients(value = {"com.cargo","com.xsungroup.tms"})
public class XinyaMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(XinyaMessageApplication.class,args);
    }
}
