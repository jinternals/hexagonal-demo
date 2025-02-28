package com.jinternals.demo.infrastructure.cache;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ExecutionContext {
    public static final String TEMPLATE = "%s.%s(%s)";
    private final Class<?> targetClass;
    private final String   targetMethod;
    private final Object[] arguments;

    @Override
    public String toString() {
        return String.format(TEMPLATE, targetClass.getName(), targetMethod, Arrays.toString(arguments));
    }
}