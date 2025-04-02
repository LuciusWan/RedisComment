package com.hmdp;
import com.hmdp.utils.RedisConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
            stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY+token,RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
        }
    }
    @Test
    public void test1() {
        // 指定文件路径
        String filePath = "C:\\Users\\86180\\Desktop\\Test\\秒杀订单TOKEN数据.txt";
        File file = new File(filePath);

        // 如果文件不存在则创建文件夹和文件
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // 如果文件存在，先清空文件内容
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(""); // 清空文件内容
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 写入新的 token 数据
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (Integer i = 1; i < NUMBER_OF_TOKEN + 1; i++) {
                Map<String, String> map = new HashMap<>();
                map.put("nickName", "");
                map.put("icon", "");
                map.put("id", i.toString());
                String token = UUID.randomUUID().toString();
                stringRedisTemplate.opsForHash().putAll(RedisConstants.LOGIN_USER_KEY + token, map);
                stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY + token, RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);

                // 将 token 写入文件
                writer.write(token);
                writer.newLine(); // 换行
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}