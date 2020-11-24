package com.cargo.jpush.push;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

/**
 * @Auther: xinzs
 * @Date: 2020/10/22 14:32
 */
public class PushResult extends BaseResult {

    private static final long serialVersionUID = 93783137655776743L;

    @Expose
    public long msg_id;
    @Expose public int sendno;
    @Expose public int statusCode;
    @Expose public Error error;
    public class Error {
        @Expose String message;
        @Expose int code;

        public String getMessage() {
            return this.message;
        }

        public int getCode() {
            return this.code;
        }
    }
}
