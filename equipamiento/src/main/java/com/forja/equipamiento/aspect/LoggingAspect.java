package com.forja.equipamiento.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Around("execution(* com.forja.equipamiento.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        if (log.isDebugEnabled()) {
            Map<String, Object> logEntry = new HashMap<>();
            logEntry.put("event", "METHOD_ENTRY");
            logEntry.put("class", className);
            logEntry.put("method", methodName);
            logEntry.put("arguments", Arrays.toString(joinPoint.getArgs()));
            log.debug("{}", objectMapper.writeValueAsString(logEntry));
        }

        long inicio = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();

            long tiempo = System.currentTimeMillis() - inicio;
            Map<String, Object> logExit = new HashMap<>();
            logExit.put("event", "METHOD_EXIT_SUCCESS");
            logExit.put("class", className);
            logExit.put("method", methodName);
            logExit.put("executionTimeMs", tiempo);
            logExit.put("result", result != null ? result.toString() : null);

            if (log.isInfoEnabled()) {
                log.info("{}", objectMapper.writeValueAsString(logExit));
            }
            return result;

        } catch (Throwable t) {
            long tiempo = System.currentTimeMillis() - inicio;
            Map<String, Object> logError = new HashMap<>();
            logError.put("event", "METHOD_EXIT_ERROR");
            logError.put("class", className);
            logError.put("method", methodName);
            logError.put("executionTimeMs", tiempo);
            logError.put("errorMessage", t.getMessage());
            
            log.error("Error durante la ejecución del método: {}", objectMapper.writeValueAsString(logError), t);
            throw t;
        } finally {
            MDC.clear();
        }
    }
}