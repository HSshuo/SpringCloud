package com.example.springcloud.service;

import com.example.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @author SHshuo
 * @data 2022/12/1--20:13
 */
public interface PaymentService {
    int create(Payment payment);

     Payment getPaymentById(@Param("id") Long id);

}