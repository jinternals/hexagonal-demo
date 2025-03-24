package com.jinternals.demo.infrastructure.services;

import com.jinternals.demo.domain.services.IdGenerator;

import java.util.UUID;

public class UUIDIdGenerator implements IdGenerator {


    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }


}
