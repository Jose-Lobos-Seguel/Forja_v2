package com.forja.recursos.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.forja.equipamiento.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        long inicio = System.currentTimeMillis();

        logger.info("======================================");
        logger.info("Método: {}", joinPoint.getSignature().getName());
        logger.info("Clase: {}", joinPoint.getTarget().getClass().getSimpleName());

        Object[] argumentos = joinPoint.getArgs();

        if(argumentos.length > 0){
            logger.info("Argumentos:");
            for(Object arg : argumentos){
                logger.info("-> {}", arg);
            }
        }

        try{

            Object resultado = joinPoint.proceed();

            long tiempo = System.currentTimeMillis() - inicio;

            logger.info("Método ejecutado correctamente.");
            logger.info("Tiempo: {} ms", tiempo);

            return resultado;

        }catch(Exception e){

            logger.error("Error ejecutando el método.");
            logger.error(e.getMessage());

            throw e;

        }finally{

            logger.info("======================================");

        }

    }

}