package com.fengying.ad.advice;

import com.fengying.ad.exception.AdException;
import com.fengying.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest rep, AdException ex){
        CommonResponse<String > response=new CommonResponse<>(-1,"business error");
        response.setData(ex.getMessage());
        return response;
    }
}
