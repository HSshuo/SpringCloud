package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author SHshuo
 * @data 2022/12/3--12:36
 */

@SpringBootApplication
@EnableFeignClients
public class OrderOpenFeignMain {
    public static void main(String[] args) {
        SpringApplication.run(OrderOpenFeignMain.class, args);
    }
}
