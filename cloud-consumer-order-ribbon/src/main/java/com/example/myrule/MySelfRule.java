package com.example.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SHshuo
 * @data 2022/12/21--12:27
 */
@Configuration
public class MySelfRule {

    /**
     * 将 Ribbon 的负载均衡规则替换为 随机
     * @return
     */
    @Bean
    public IRule myRule() {
        return new RandomRule();
    }
}
