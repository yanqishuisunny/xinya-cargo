package com.cargo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Carols
 */
@SpringBootApplication(scanBasePackages = {"com.cargo","com.commom","com.xsungroup.tms"})
@MapperScan(value = {"com.cargo.**.mapper","com.commom.supper"})
@EnableMongoRepositories
@EnableScheduling
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients(value = {"com.cargo","com.xsungroup.tms"})
public class XinyaOrderApplication {

    public static void main(String[] args) {

        SpringApplication.run(XinyaOrderApplication.class, args);
    }
    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = sdf.parse( source);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }
}
