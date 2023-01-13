package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author SHshuo
 * @data 2023/1/6--20:28
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerMain {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerMain.class, args);
    }
}
