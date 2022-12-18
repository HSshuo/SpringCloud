package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author SHshuo
 * @data 2022/12/17--18:40
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentConsulMain {
    public static void main(String[] args) {
        SpringApplication.run(PaymentConsulMain.class,args);
    }
}
