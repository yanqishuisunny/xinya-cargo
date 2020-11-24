package com.cargo.feign.service;


import com.cargo.location.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FallbackService implements FeignService {


    @Override
    public UserInfoVo getUserById(String id) {
        log.error("--------------------FeignClient--启动熔断:{}" , "UserInfoEntity");
        return null;
    }


}