package com.mlg.forja.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.mapping.Join;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Before("Execution(* com.mlg.forja.service*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint){
        log.info("Iniciando metodo: {} con argumentos: {}",
            joinPoint.getSignature().toShortString(),
            joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* com.mlg.forja.service.*.*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result){
        log.info("Finalizado metodo: {} | Retorno: {}",
            joinPoint.getSignature().toShortString(),
            result);
    }
}