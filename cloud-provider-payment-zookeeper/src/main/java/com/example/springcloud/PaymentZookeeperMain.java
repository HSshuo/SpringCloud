package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author SHshuo
 * @data 2022/12/14--20:40
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentZookeeperMain {
    public static void main(String[] args) {
        SpringApplication.run(PaymentZookeeperMain.class, args);
    }
}
