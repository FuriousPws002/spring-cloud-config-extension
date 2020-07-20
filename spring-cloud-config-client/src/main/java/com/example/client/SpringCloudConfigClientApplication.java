package com.example.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigClientApplication.class, args);
    }

    @Bean
    @ConditionalOnProperty(name = "spring.print.beans", havingValue = "true")
    public CommandLineRunner runner(ApplicationContext ctx) {
        return (args) -> {
            String[] beanNames = ctx.getBeanDefinitionNames();
            if (beanNames.length == 0) {
                return;
            }
            for (String beanName : beanNames) {
                System.err.println(beanName + " -> " + ctx.getBean(beanName));
            }
        };
    }
}
