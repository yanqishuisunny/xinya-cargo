package com.commom.exception;


import com.commom.core.BusCode;
import com.commom.core.IBusCode;

public class RestResultBuider<T> {
    protected String status;
    protected int code;
    protected String message;
    protected T data;


    public static RestResultBuider builder(){
        RestResultBuider buider=new RestResultBuider();
        return buider;
    }

    public RestResultBuider status(String  status){
        this.status=status;
        return this;
    }
    public RestResultBuider code(int code){
        this.code=code;
        return this;
    }
    public RestResultBuider message(String message){
        this.message=message;
        return this;
    }
    public RestResultBuider data(T data){
        this.data=data;
        return this;
    }

    public RestResultBuider errorCode(String message){
        this.message=message;
        this.code=BusCode.FAIL.getCode();
        this.status="FAIL";
        return this;
    }



    public RestResultBuider success(){
        this.status="SUCCESS";
        this.message= BusCode.SUCCESS.getMessage();
        this.code= BusCode.SUCCESS.getCode();
        return this;

    }

    public RestResultBuider success(T data){
        this.status="SUCCESS";
        this.message= BusCode.SUCCESS.getMessage();
        this.code= BusCode.SUCCESS.getCode();
        this.data=data;
        return this;

    }

    public RestResultBuider failure(T data){
        this.status="FAIL";
        this.message= BusCode.FAIL.getMessage();
        this.code= BusCode.FAIL.getCode();
        this.data=data;
        return this;

    }

    public RestResultBuider failure(){
        this.status="FAIL";
        this.message= BusCode.FAIL.getMessage();
        this.code= BusCode.FAIL.getCode();
        return this;

    }

    public RestResultBuider result(boolean successful){
        if(successful){
            return this.success();

        }else {
            return  this.failure();
        }
    }

    public RestResultBuider success(boolean result){
        if(result==Boolean.TRUE){
            success();

        }else {
            failure();
        }
        return this;
    }


    public RestResult<T> build(){
        return new RestResult<T>(this.status,this.code,this.message,this.data);
    }

    public RestResult build(RestResult restResult){
        return  restResult;
    }

    public RestResultBuider success(T data, String desc){
        this.status="SUCCESS";
        this.message=desc;
        this.code= BusCode.SUCCESS.getCode();
        this.data=data;
        return this;

    }

    public RestResultBuider failure(T data, String desc){
        this.status="FAILURE";
        this.message=desc;
        this.code= BusCode.FAIL.getCode();
        this.data=data;
        return this;

    }

    public RestResultBuider failure(String desc){
        this.status="FAILURE";
        this.message=desc;
        this.code= BusCode.FAIL.getCode();
        return this;

    }

}
