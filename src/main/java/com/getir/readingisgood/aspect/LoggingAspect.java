package com.getir.readingisgood.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("methodAnnotatedWithTraceAccessDetails()")
    public void logAccessDetails(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        log.info("Method Name Requested -> {} ", joinPoint.getSignature().toShortString());
        for (Object signatureArg : signatureArgs) {
            log.info("Arguments -> {}", signatureArg.toString());
        }
    }

    @Pointcut("execution(* com.getir.readingisgood.controller..*.*(..))")
    public void methodAnnotatedWithTraceAccessDetails() {

    }

}
