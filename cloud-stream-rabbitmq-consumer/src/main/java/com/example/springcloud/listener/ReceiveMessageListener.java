package com.example.springcloud.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author SHshuo
 * @data 2023/1/14--20:25
 * EnableBinding 作用是信道 channel 与 exchange 绑定在一起
 */
@Component
@EnableBinding(Sink.class)
public class ReceiveMessageListener {

    @Value("${server.port}")
    private String serverPort;

    /***
     * StreamListener 作用是监听队列，用于消费者队列的消息接收
     * @param message
     */
    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        System.out.println("消费者1号，------->接收到的消息：" + message.getPayload() + "\t port: " + serverPort);
    }
}