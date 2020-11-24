package com.cargo.feign.service;

import com.cargo.location.vo.UserInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "xinya-base" ,fallback = FallbackService.class)
public interface FeignService {

    @GetMapping(value = "/ms/service/userInfo/get/{id}")
    UserInfoVo getUserById(@PathVariable("id") String id);

}
