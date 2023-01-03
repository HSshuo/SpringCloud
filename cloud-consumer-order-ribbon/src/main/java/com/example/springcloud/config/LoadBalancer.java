package com.example.springcloud.config;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author SHshuo
 * @data 2022/12/30--18:04
 */
public interface LoadBalancer {
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}