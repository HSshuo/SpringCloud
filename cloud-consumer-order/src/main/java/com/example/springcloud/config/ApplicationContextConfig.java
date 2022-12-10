package com.example.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author SHshuo
 * @data 2022/12/3--12:41
 *
 * RestTemplate 提供了多种便捷访问远程 HTTP 服务的方法，是一种简单便捷的访问 Restful 服务模板类
 * 是 Spring 提供的用于访问 Rest 服务的客户端模板工具集
 */
@Configuration
public class ApplicationContextConfig {

    /**
     * @LoadBalanced 由于配置的是服务名称，对应两个服务。需要指定默认的负载均衡机制
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
