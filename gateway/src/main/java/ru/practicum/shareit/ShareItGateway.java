package ru.practicum.shareit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShareItGateway {
    public static void main(String[] args) {
        System.setProperty("spring.config.location", "file:/path/to/your/application.properties");
        SpringApplication.run(ShareItGateway.class, args);
    }

}