package com.example.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author SHshuo
 * @data 2023/1/16--15:22
 */
@RestController
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * zipkin + sleuth
     */
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin() {
        String result = restTemplate.getForObject("http://localhost:8009" + " /payment/zipkin/ ", String.class);
        return result;
    }
}
