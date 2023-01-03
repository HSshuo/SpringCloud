package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author SHshuo
 * @data 2023/1/2--14:35
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashBoardMain {

    /***
     * 访问地址：http://127.0.0.1:9001/hystrix
     * 里面填写的参数配置：被监控的服务 http://127.0.0.1:8007/hystrix.stream; delay填写默认2000即可
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashBoardMain.class, args);
    }
}
