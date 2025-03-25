package com.hmdp.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redisson6379() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379").setPassword("123456");
        return Redisson.create(config);
    }
    @Bean
    public RedissonClient redisson6380() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6380").setPassword("123456");
        return Redisson.create(config);
    }
    @Bean
    public RedissonClient redisson6381() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6381").setPassword("123456");
        return Redisson.create(config);
    }
}
