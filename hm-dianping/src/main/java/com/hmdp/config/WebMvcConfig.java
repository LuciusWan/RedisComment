package com.hmdp.config;

import com.hmdp.intercepter.GlobalInterceptor;
import com.hmdp.intercepter.userIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GlobalInterceptor(stringRedisTemplate))
                .addPathPatterns("/**").order(0);
        registry.addInterceptor(new userIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/user/code",
                        "/shop/**",
                        "/shop-type/**",
                        "/blog/hot",
                        "/voucher/**").order(1);

    }
}
