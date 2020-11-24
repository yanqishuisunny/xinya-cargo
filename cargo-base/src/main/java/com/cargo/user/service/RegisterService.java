package com.cargo.user.service;

import com.cargo.app.dto.AppRegisterDto;
import com.cargo.user.dto.UserRegisterDto;
import com.commom.cache.HeaderDto;
import com.commom.core.IBusCode;

/**
 * @Author GF
 * @Date 2019-7-29 09:54:10
 * @Description
 **/
public interface RegisterService {


    /**
     * app端注册
     * @param appRegisterDTO
     */
    IBusCode appRegister(AppRegisterDto appRegisterDTO,HeaderDto headerDto);


    /**
     * 0302-注册用户
     * @param userRegisterDTO
     * @return
     */
    String registerUser(UserRegisterDto userRegisterDTO, HeaderDto headerDto);

}
