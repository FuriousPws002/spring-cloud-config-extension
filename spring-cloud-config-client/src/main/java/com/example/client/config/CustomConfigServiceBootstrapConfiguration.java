package com.example.client.config;

import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfigServiceBootstrapConfiguration {

    @Bean
    public ConfigServicePropertySourceLocator configServicePropertySource(ConfigClientProperties defaultProperties) {
        return new LocalAndRemotePropertySourceLocator(defaultProperties);
    }
}
