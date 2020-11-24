package com.cargo.jpush.push;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xinzs
 * @Date: 2020/10/22 14:53
 */
public class CIDResult extends BaseResult {

    @Expose
    public List<String> cidlist = new ArrayList<String>();
}
