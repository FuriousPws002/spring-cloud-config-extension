package com.example.client.controller;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("test")
public class Config {
    private List<String> key1;
    private List<User> users;
}
