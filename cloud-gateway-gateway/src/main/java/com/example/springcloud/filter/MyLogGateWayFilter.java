package com.example.springcloud.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @author SHshuo
 * @data 2023/1/6--18:49
 */

@Component
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    /**
     * 测试地址：http://127.0.0.1:9527/payment/lb?uname=hshuo
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        System.out.println("time:" + new Date() + "\t 执行了自定义的全局过滤器: " + "MyLogGateWayFilter hello");

        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (uname == null) {
            System.out.println("****用户名为null，无法登录");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        // 继续执行其他过滤器
        return chain.filter(exchange);
    }

    /**
     * 设置优先级，数字越小优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}