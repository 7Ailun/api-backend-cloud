package com.wei.weiapiinterface.aop;

import com.wei.weiapicommon.common.ErrorCode;
import com.wei.weiapicommon.exception.BusinessException;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
@Aspect
@Component
public class SourceInterceptor {

    @Before(value = "execution(* com.wei.weiapiinterface.controller.*.*(..)) && args(request,joinPoint)", argNames = "joinPoint,request")
    public Object doInterceptor(Joinpoint joinPoint, HttpServletRequest request) {
        String source = request.getHeaders("Source").toString();
        System.out.println("source = " + source);
        if ( !"MyGateway".equals(source)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR,"来源不明");
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }
}
