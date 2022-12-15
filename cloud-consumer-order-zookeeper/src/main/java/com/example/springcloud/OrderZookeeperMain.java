package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author SHshuo
 * @data 2022/12/15--11:51
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderZookeeperMain {
    public static void main(String[] args) {
        SpringApplication.run(OrderZookeeperMain.class, args);
    }
}
