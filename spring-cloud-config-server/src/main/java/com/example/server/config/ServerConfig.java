package com.example.server.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
    @Bean
    @ConditionalOnMissingBean(PropertiesPropertySourceLoader.class)
    public PropertiesPropertySourceLoader propertiesPropertySourceLoader() {
        return new PropertiesPropertySourceLoader();
    }

    @Bean
    @ConditionalOnMissingBean(YamlPropertySourceLoader.class)
    public YamlPropertySourceLoader yamlPropertySourceLoader() {
        return new YamlPropertySourceLoader();
    }
}
