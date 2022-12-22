package com.example.springcloud.controller;

import com.example.springcloud.base.CommonResult;
import com.example.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author SHshuo
 * @data 2022/12/3--12:42
 */
@RestController
public class OrderController {

    /**
     * 访问地址不能写死 ： "http://localhost:8001";
     * 通过在eureka上注册过的微服务名称调用
     */
    public static final String PaymentSrv_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 客户端用浏览器是get请求，但是底层实质发送post调用服务端8001
     * Object：返回对象为响应体中数据转化成的对象，基本上可以理解为 JSON
     * @param payment
     * @return
     */
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PaymentSrv_URL + "/payment/create", payment, CommonResult.class);
    }


    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable Long id) {
        return restTemplate.getForObject(PaymentSrv_URL + "/payment/get/" + id, CommonResult.class);
    }


    /**
     * Entity：返回对象为 ResponseEntity 对象，包含了响应中的一些重要信息，比如响应头、响应状态码、响应体
     * @return
     */
    @GetMapping("/consumer/payment/getEntry/{id}")
    public CommonResult<Payment> getPaymentEntry(@PathVariable Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PaymentSrv_URL + "/payment/get/" + id, CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }else {
            return new CommonResult(444, "操作失败");
        }
    }
}
