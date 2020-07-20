package com.example.client.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {

    @Value("${key3}")
    private List<String> key3;
    private final Config config;

    private final Environment environment;

    /**
     * 由于label2 关联 label1 ，label1关联master
     * 所以key3的值应以label2的值为准
     * config中key1是测试数组,users是测试pojo的数组
     *
     * @see ConfigServicePropertySourceLocator#locate(org.springframework.core.env.Environment)
     */
    @GetMapping("test")
    public Object test() throws Exception {
        System.err.println(key3);
        System.err.println(config);
        System.err.println(environment);
        return Instant.now();
    }

}
