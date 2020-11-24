package com.cargo.jpush.push.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * @Auther: xinzs
 * @Date: 2020/10/22 14:36
 */
public interface PushModel {
    public static Gson gson = new Gson();
    public JsonElement toJSON();
}
