package com.cargo.feign.service;


import com.cargo.user.entity.UserInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FallbackService implements FeignService {


    @Override
    public UserInfoEntity getUserById(String id) {
        log.error("--------------------FeignClient--启动熔断:{}" , "UserInfoEntity");
        return null;
    }



}
