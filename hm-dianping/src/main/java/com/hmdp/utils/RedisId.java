package com.hmdp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
@Component
public class RedisId {
    public static final Long TIME_STAMP = 1742324721L;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public Long nextId(String keyPrefix) {
        //计算出时间戳
        LocalDateTime now = LocalDateTime.now();
        Long nowSeconds = now.toEpochSecond(ZoneOffset.UTC);
        Long timeStamp = nowSeconds - TIME_STAMP;
        String date=now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        //Redis引入后计算出今日的商品单数,作为商品的序列号
        Long count=stringRedisTemplate.opsForValue().increment("icr:"+keyPrefix+":"+date);
        return timeStamp<<32|count;
    }
}
