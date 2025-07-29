package com.cy.rememeber.aop;

import com.cy.rememeber.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Log4j2
@Component
public class LoginCheckAspect {
    @Around("@annotaion(com.cy.remember.aop.LoginCheck) && @annotaion(loginCheck)")//logincheck interface 동작하게함
    public Object adminLoginCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable{
        HttpSession session = (HttpSession) ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        String id = null;
        int idIndex = 0;

        String userType = loginCheck.type().toString();
        switch (userType){
            case "ADMIN":{
                id = SessionUtil.getLoginAdminId(session);
                break;
            }
            case "USER":{
                id = SessionUtil.getLoginMemberId(session);
                break;
            }
        }
        if(id==null){
            log.error(proceedingJoinPoint.toString()+"accountName : "+ id);
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 Id를 확인해주세요"){};
        }

        Object[] modifiedArgs = proceedingJoinPoint.getArgs();
        if(proceedingJoinPoint!=null){
            modifiedArgs[idIndex] = id;
        }
        return proceedingJoinPoint.proceed(modifiedArgs);
    }

}
