package com.hmdp.config;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;
@Configuration
public class CacheConfig {

    /**
     * 创建Caffeine本地缓存实例
     * - 初始容量100，最大200条记录
     * - 条件过期：创建后10分钟过期 OR 最后访问后5分钟过期
     */
    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                .initialCapacity(50) // 初始容量
                .maximumSize(100)    // 最多缓存100个对象
                .expireAfterWrite(10, TimeUnit.MINUTES) // 写入后10分钟过期
                .expireAfterAccess(5, TimeUnit.MINUTES) // 最后一次访问后5分钟过期
                .build();
    }
}
