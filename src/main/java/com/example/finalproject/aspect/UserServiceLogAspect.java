package com.example.finalproject.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UserServiceLogAspect {
    @Before("thisPointCut()")
    public void firstAdvice(){
        log.info("Users methods");
    }

    @Around("beanPointCut()  && argsPointCut(model)")
    public Object serviceLog(ProceedingJoinPoint proceedingJoinPoint, Object model) throws Throwable {
        log.info("Executing user service method with model {}", model.toString());
        Object retValue = proceedingJoinPoint.proceed();
        log.info("Executed successfully with return value {}", retValue);
        return retValue;
    }

    @Pointcut("bean(userServiceImp)")
    private void beanPointCut(){}

    @Pointcut("args(model)")
    private void argsPointCut(Object model){}

    @Pointcut("this(com.example.finalproject.service.UserService)")
    private void thisPointCut(){}

}
