package com.commom.exception;

import com.commom.core.BusCode;
import com.commom.core.IBusCode;

public class BussException extends RuntimeException{
    private IBusCode code;
    private String message;

    private final static boolean WRITABLE_STACK_TRACE = true;

    public BussException(IBusCode code) {
        super(code.getMessage(), null, true, WRITABLE_STACK_TRACE);
        this.setCode(code);
        this.setMessage(code.getMessage());
    }

    public BussException(String e) {
        super(e, null, true, WRITABLE_STACK_TRACE);
        this.setCode(BusCode.FAIL);
        this.setMessage(e);
    }

    public BussException(String message, Throwable e) {
        super(message, e, true, WRITABLE_STACK_TRACE);
        this.setCode(BusCode.FAIL);
        this.setMessage(message);
    }


    public BussException(IBusCode code, String message) {
        super(message, null, true, WRITABLE_STACK_TRACE);
        this.setCode(code);
        this.setMessage(message);
    }

    public IBusCode getCode() {
        return code;
    }

    public void setCode(IBusCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public final static String formatMsg(IBusCode busCode){
        return String.format("%s-%s",busCode.getCode(),busCode.getMessage());

    }

    public String formatMessage(String message){
        return String.format("%s-%s",this.code,message);
    }

    public String formatMessage(int code, String message){
        return String.format("%s-%s",code,message);
    }

}
