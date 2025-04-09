package com.hmdp.config;

import com.hmdp.properties.RedisProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class RedissonConfig {
    @Autowired
    private RedisProperties redisProperties;
    @Bean
    public RedissonClient redisson6379() {
        Config config = new Config();
        //docker运行环境/本地运行环境
        if(!Objects.equals(redisProperties.getHost(), "localhost")){
            config.useSingleServer().setAddress("redis://redis1:6379");
        }else{
            config.useSingleServer().setAddress("redis://localhost:6379");
        }
        return Redisson.create(config);
    }
    @Bean
    public RedissonClient redisson6380() {
        Config config = new Config();
        if(!Objects.equals(redisProperties.getHost(), "localhost")){
            config.useSingleServer().setAddress("redis://redis2:6379");
        }else{
            config.useSingleServer().setAddress("redis://localhost:6380");
        }
        return Redisson.create(config);
    }
    @Bean
    public RedissonClient redisson6381() {
        Config config = new Config();
        if(!Objects.equals(redisProperties.getHost(), "localhost")){
            config.useSingleServer().setAddress("redis://redis3:6379");
        }else{
            config.useSingleServer().setAddress("redis://localhost:6381");
        }
        return Redisson.create(config);
    }
}
