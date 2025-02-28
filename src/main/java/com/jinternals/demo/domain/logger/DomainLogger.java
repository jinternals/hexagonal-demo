package com.jinternals.demo.domain.logger;

public interface DomainLogger {
    void info(String message);
    void warn(String message);
    void error(String message);
}
