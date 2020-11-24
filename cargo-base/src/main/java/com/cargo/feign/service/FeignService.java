package com.cargo.feign.service;

import com.cargo.user.entity.UserInfoEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "xinya-gps" ,fallback = FallbackService.class)
public interface FeignService {


    @GetMapping(value = "/userInfo/get/{id}")
    public UserInfoEntity getUserById(@PathVariable("id") String id);



}
