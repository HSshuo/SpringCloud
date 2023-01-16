package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author SHshuo
 * @data 2023/1/16--15:21
 */
@SpringBootApplication
@EnableEurekaClient
public class OrderSleuthMain {
    public static void main(String[] args) {
        SpringApplication.run(OrderSleuthMain.class, args);
    }
}
