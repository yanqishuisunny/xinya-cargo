package com.cargo.feign.service;

import com.cargo.complaint.entity.UserInfoEntity;
import com.cargo.feign.service.impl.FallbackService;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "xinya-base" ,fallback = FallbackService.class)
public interface FeignService {

    @GetMapping("/ms/service/userInfo/get/{userId}")
    public UserInfoEntity findById(@ApiParam("用户ID") @PathVariable("userId") String userId);

}
