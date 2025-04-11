package com.hmdp;

import com.hmdp.entity.Shop;
import com.hmdp.service.impl.ShopServiceImpl;
import com.hmdp.utils.RedisConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.*;
@SpringBootTest
public class GeoLoad {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ShopServiceImpl shopService;
    @Test
    public void test() {
        for (Long i = 1L; i < 3; i++) {
            List<Shop> shopList=shopService.listById(i);
            for (int i1 = 0; i1 < shopList.size(); i1++) {
                stringRedisTemplate.opsForGeo()
                        .add(RedisConstants.SHOP_GEO_KEY+i,
                                new Point(shopList.get(i1).getX(),
                                        shopList.get(i1).getY()),
                                shopList.get(i1).getId().toString());
            }
        }
    }
}
