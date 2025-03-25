package com.hmdp;

import com.hmdp.utils.RedisId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class HmDianPingApplicationTests {
    @Autowired
    private RedisId redisId;
    @Test
    void contextLoads() {

        Runnable task =()->{
            for (int i = 0; i < 10000; i++) {
                long id = redisId.nextId("order"); // 假设 nextId 方法接受一个 keyPrefix 参数
                System.out.println("id = " + id);
            }
        };
        System.out.println(System.currentTimeMillis());
        task.run();
        System.out.println(System.currentTimeMillis());
        /*System.out.println(redisId.nextId("shop"));*/
    }

}
