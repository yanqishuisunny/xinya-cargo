package com.cargo.feign.service;

import com.cargo.entity.UserInfoEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "xinya-base" ,fallback = FallbackService.class)
public interface FeignService {


    @GetMapping("/ms/service/userInfo/get/{userId}")
    public UserInfoEntity findById(@ApiParam("用户ID") @PathVariable("userId") String userId);



//    @GetMapping("/ms/service/carMessageToRedis/orgId")
//    public String carMessageToRedis(@RequestParam String orgId);
    @ApiOperation(value = "根据ID获得用户")
    @GetMapping("/ms/service/carMessageToRedis/orgId")
    public String carMessageToRedis(@ApiParam("用户ID") @RequestParam String orgId);


}
