package com.commom.supper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.commom.cache.HeaderCons;
import com.commom.cache.HeaderDto;
import com.commom.cache.PageCons;
import com.commom.utils.HttpServletUtil;
import com.commom.utils.TypeUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 基础控制层封装
 * @Author: kingJing
 * @Date: 2019/7/5 13:38
 **/
@Api
@Slf4j
public class SuperController {

    @Autowired
    protected HttpServletRequest request;


    protected HeaderDto getHeaders(){
        String appid = TypeUtils.castToString(request.getHeader(HeaderCons.APPID),"");
        String token = TypeUtils.castToString(request.getHeader(HeaderCons.TOKEN),"");
        String sign = TypeUtils.castToString(request.getHeader(HeaderCons.SIGN),"");
        String rnd = TypeUtils.castToString(request.getHeader(HeaderCons.RND),"");
        long time = TypeUtils.castToLong(request.getHeader(HeaderCons.TIME), System.currentTimeMillis());
        String referer = TypeUtils.castToString(request.getHeader(HeaderCons.REFERER), "");
        String versionType = TypeUtils.castToString(request.getHeader(HeaderCons.VERSIONTYPE), "");
        String version = TypeUtils.castToString(request.getHeader(HeaderCons.VERSION), "");
        String ip = HttpServletUtil.getIpAddress();

        HeaderDto dto = new HeaderDto();
        dto.setAppid(appid);
        dto.setToken(token);
        dto.setSign(sign);
        dto.setRnd(rnd);
        dto.setTime(time);
        dto.setIp(ip);
        dto.setReferer(referer);
        dto.setVersionType(versionType);
        return dto;
    }


    protected <T> Page<T> getPage(boolean openSort){
      //TODO 节后重新定义统一的一套
        int index = 1;
        // 页数
        String parameter = request.getParameter(PageCons.PAGE_PAGE);
        if (ObjectUtils.isEmpty(parameter)) {
            parameter = request.getParameter(PageCons.PAGE_PAGE_CURSOR);
        }
      //  Integer cursor = TypeUtils.castToInt(request.getParameter(PageCons.PAGE_PAGE), index);
        Integer cursor = TypeUtils.castToInt(parameter, index);
        // 分页大小
        String rows = request.getParameter(PageCons.PAGE_ROWS);
        if (ObjectUtils.isEmpty(rows)) {
            rows = request.getParameter(PageCons.PAGE_ROWS_LIMIT);
        }
        Integer limit = TypeUtils.castToInt(rows, PageCons.DEFAULT_LIMIT);
        // 是否查询分页
        Boolean searchCount = TypeUtils.castToBoolean(request.getParameter(PageCons.SEARCH_COUNT), true);
        limit = limit > PageCons.MAX_LIMIT ? PageCons.MAX_LIMIT : limit;
        Page<T> page = new Page<>(cursor, limit, searchCount);
        return page;
    }





}
