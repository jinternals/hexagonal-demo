package com.jinternals.demo.infrastructure.logger;

import com.jinternals.demo.domain.logger.DomainLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jDomainLogger implements DomainLogger {

    private Logger logger;

    public Slf4jDomainLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

}
