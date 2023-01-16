package com.example.springcloud.controller;

import com.example.springcloud.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author SHshuo
 * @data 2023/1/14--20:20
 */
@RestController
public class SendMessageController {
    @Resource
    private MessageService messageProvider;

    @GetMapping(value = "/sendMessage")
    public String sendMessage() {
        return messageProvider.send();
    }
}