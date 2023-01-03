package com.example.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author SHshuo
 * @data 2023/1/1--18:19
 * 处理的是服务端的异常，例如 Eureka 上面注册的服务宕机，会优先走此处的降级处理
 * Controller 里面的处理属于处理客户端自身的异常，如果同时存在此处会优先处理服务端出现的异常
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "服务调用失败，提示来自：cloud-order-service8087";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "服务调用失败，提示来自：cloud-order-service8087";
    }
}
