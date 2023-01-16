package com.example.springcloud.service.Impl;

import com.example.springcloud.service.MessageService;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author SHshuo
 * @data 2023/1/14--20:18
 * EnableBinding 作用是信道 channel 与 exchange 绑定在一起
 */
@EnableBinding(Source.class) // 可以理解为是一个消息的发送管道的定义
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageChannel output; // 消息的发送管道

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        this.output.send(MessageBuilder.withPayload(serial).build()); // 创建并发送消息
        System.out.println("***serial: " + serial);
        return serial;
    }
}