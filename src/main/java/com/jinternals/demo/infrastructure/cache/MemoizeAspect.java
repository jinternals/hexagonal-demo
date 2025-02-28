package com.jinternals.demo.infrastructure.cache;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@AllArgsConstructor
public class MemoizeAspect {
    private RequestCache requestCache;

    @Pointcut("execution(* (@com.jinternals.demo.infrastructure.cache.Memoize *).*(..))")
    public void methodInMemoizeType() {}

    @Pointcut("execution(@com.jinternals.demo.infrastructure.cache.Memoize * *.*(..))")
    public void methodAnnotatedWithMemoize() {}

    @Around("methodAnnotatedWithMemoize() || methodInMemoizeType()")
    public Object memoize(ProceedingJoinPoint pjp) throws Throwable {
        ExecutionContext executionContext = new ExecutionContext(
                pjp.getSignature().getDeclaringType(),
                pjp.getSignature().getName(),
                pjp.getArgs()
        );

        Object result = requestCache.findCachedResult(executionContext);
        if (result == RequestCache.NONE)
        {
            result = requestCache.save(executionContext, pjp.proceed());
        }
        return result == RequestCache.NULL ? null : result;
    }
}