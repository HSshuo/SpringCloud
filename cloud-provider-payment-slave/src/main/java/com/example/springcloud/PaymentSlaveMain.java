package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author SHshuo
 * @data 2022/12/1--19:43
 */

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class PaymentSlaveMain {
    public static void main(String[] args) {
        SpringApplication.run(PaymentSlaveMain.class, args);
    }
}
