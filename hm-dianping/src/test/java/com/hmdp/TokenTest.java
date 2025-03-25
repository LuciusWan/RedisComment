package com.hmdp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.hmdp.utils.RedisConstants;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class TokenTest {
    private static final Integer NUMBER_OF_TOKEN = 1000;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test() {
        for (Integer i = 1; i < NUMBER_OF_TOKEN+1; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("nickName", "");
            map.put("icon", "");
            map.put("id", i.toString());
            String token= UUID.randomUUID().toString();
            stringRedisTemplate.opsForHash().putAll(RedisConstants.LOGIN_USER_KEY+token,map);
            System.out.println(token);
            stringRedisTemplate.expire(token, RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
        }
    }
}