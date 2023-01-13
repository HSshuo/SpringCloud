package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author SHshuo
 * @data 2023/1/13--20:02
 */
@SpringBootApplication
@EnableEurekaClient
public class ConfigClientSlaveMain {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientSlaveMain.class, args);
    }
}