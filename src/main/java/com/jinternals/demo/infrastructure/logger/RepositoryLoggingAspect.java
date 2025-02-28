package com.jinternals.demo.infrastructure.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class RepositoryLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryLoggingAspect.class);

    @Around("execution(* org.springframework.data.repository.Repository+.*(..))")
    public Object logRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("Calling Repository Method: {}", methodName);
        logger.info("Arguments: {}", Arrays.toString(args));

        Object result = joinPoint.proceed();

        logger.info("Method {} returned: {}", methodName, result);
        return result;
    }
}
