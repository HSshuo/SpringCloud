package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author SHshuo
 * @data 2023/1/16--15:25
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class ProviderSleuthMain {
    public static void main(String[] args) {
        SpringApplication.run(ProviderSleuthMain.class, args);
    }
}
