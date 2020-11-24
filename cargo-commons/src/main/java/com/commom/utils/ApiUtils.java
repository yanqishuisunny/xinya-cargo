package com.commom.utils;


import com.commom.core.BeanConverter;

/**
 * <p>
 * Tree工具类
 * </p>
 *
 * @author Caratacus
 */
public abstract class ApiUtils {

    /**
     * 递归查找子节点
     *
     * @param responseInfo
     * @return
     */
    public static ResponseInfo convertRes(ResponseInfo responseInfo) {
        return BeanConverter.convert(ResponseInfo.class,responseInfo);
    }
}
