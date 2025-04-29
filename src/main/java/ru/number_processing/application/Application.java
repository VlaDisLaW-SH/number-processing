package ru.number_processing.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("ru")
public class Application {
    public static void main(String[] args) {
        log.info("Microservice number_processing starting...");
        SpringApplication.run(Application.class, args);
        log.info("Microservice number_processing started.");
    }
}
