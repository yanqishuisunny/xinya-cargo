package com.commom.exception;

import com.commom.core.IBusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class BusinessExceptionHandler {

    private static  final Logger logger= LoggerFactory.getLogger(BusinessExceptionHandler.class);

     @ExceptionHandler(BussException.class)
     public RestResult businessExceptionHandler(BussException ex){

         IBusCode iBusCode=ex.getCode();
        if(ex.getCode()==null){
            logger.warn("------>BusinessException code:{},message:{}",ex.getCode(),ex.getMessage());
            return RestResultBuider.builder().failure("FAILURE").message(ex.getMessage()).build();
        }
        logger.warn("------>BusinessException code:{},message:{}",iBusCode.getCode(),ex.getMessage());
        return RestResultBuider.builder().errorCode(ex.getMessage()).build();
    }

}
