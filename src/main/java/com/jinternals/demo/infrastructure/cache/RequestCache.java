package com.jinternals.demo.infrastructure.cache;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestCache {
    public static final Object NONE = new Object();
    public static final Object NULL = new Object();
    private final Map<ExecutionContext, Object> cache = new ConcurrentHashMap<>();

    public Object findCachedResult(ExecutionContext executionContext) {
        return cache.getOrDefault(executionContext, RequestCache.NONE);
    }

    public Object save(ExecutionContext executionContext, Object result) {
        return cache.computeIfAbsent(executionContext, key -> result == null ? RequestCache.NULL : result);
    }
}