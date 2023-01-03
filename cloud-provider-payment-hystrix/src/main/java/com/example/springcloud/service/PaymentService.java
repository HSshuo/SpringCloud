package com.example.springcloud.service;

/**
 * @author SHshuo
 * @data 2022/12/30--18:33
 */
public interface PaymentService {
    String paymentInfo_OK(Integer id);

    String paymentInfo_TimeOut(Integer id);

    String paymentCircuitBreaker(Integer id);
}
