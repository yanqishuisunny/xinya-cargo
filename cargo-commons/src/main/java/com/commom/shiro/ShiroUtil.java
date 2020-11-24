package com.commom.shiro;

import com.commom.cache.SpringBeanUtil;
import com.commom.core.AssertBuss;
import com.commom.core.BusCode;
import com.commom.vo.CurrentUser;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;

import java.util.Arrays;
import java.util.List;

/**
 * shiro 用户登录
 *
 * @author helihui
 */

@Slf4j
public class ShiroUtil {


    /**
     * 获取当前登录人id
     * @return
     */
    public static String principal(){
        return (String) SecurityUtils.getSubject().getPrincipal();
    }


    /**
     * 获取当前登录人id
     * @return
     */
    public static String currentUserId(){
        List<String> list = Arrays.asList(principal().split(","));
        String userId = list.get(0);
        if(Strings.isNullOrEmpty(userId)){
            log.warn("用户ID为空，请检查方式是否经过shiro 验证");
        }
        AssertBuss.notNull(userId, BusCode.TOKEN_LOSE);
        return userId;
    }



    /**
     * 获取当前登录人id
     * @return
     */
    public static String getUserId(){
        List<String> list = Arrays.asList(principal().split(","));
        String userId = list.get(0);
        if(Strings.isNullOrEmpty(userId)){
            log.warn("用户ID为空，请检查方式是否经过shiro 验证");
        }
        AssertBuss.notNull(userId, BusCode.TOKEN_LOSE);
        return userId;
    }



    /**
     * 获取当前登录人所在qi
     * @return
     */
    public static String getOrgId(){
        List<String> list = Arrays.asList(principal().split(","));
        if(list.size() < 2){
           return "";
        }
        return list.get(1);
    }


//    /**
//     * 获取当前登录人信息
//     * @return
//     */
//    public static CurrentUser currentUser(){
//        String userId = currentUserId();
//        if(Strings.isNullOrEmpty(userId)){
//            log.warn("用户ID为空，请检查方式是否经过shiro 验证");
//        }
//        AssertBuss.notNull(userId, BusCode.TOKEN_LOSE);
//        UserService userService = SpringBeanUtil.getBean(UserService.class);
//        CurrentUser currentUser = userService.findUserById(userId);
//        log.info("当前登录用户 : " + currentUser.toString());
//        return currentUser;
//    }

}
