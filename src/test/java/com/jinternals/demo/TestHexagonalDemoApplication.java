package com.jinternals.demo;

import org.springframework.boot.SpringApplication;

public class TestHexagonalDemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
