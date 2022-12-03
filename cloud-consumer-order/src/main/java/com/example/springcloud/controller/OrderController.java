package com.example.springcloud.controller;

import com.example.springcloud.base.CommonResult;
import com.example.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author SHshuo
 * @data 2022/12/3--12:42
 */
@RestController
public class OrderController
{

    public static final String PaymentSrv_URL = "http://localhost:8081";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 客户端用浏览器是get请求，但是底层实质发送post调用服务端8081
     * @param payment
     * @return
     */
    @GetMapping("/consumer/payment/create")
    public CommonResult create(Payment payment)
    {
        return restTemplate.postForObject(PaymentSrv_URL + "/payment/create", payment, CommonResult.class);
    }


    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult getPayment(@PathVariable Long id)
    {
        return restTemplate.getForObject(PaymentSrv_URL + "/payment/get/" + id, CommonResult.class, id);
    }
}
