package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author SHshuo
 * @data 2022/12/4--13:30
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekaSlaveMain {
    public static void main(String[] args) {
        SpringApplication.run(EurekaSlaveMain.class, args);
    }
}
