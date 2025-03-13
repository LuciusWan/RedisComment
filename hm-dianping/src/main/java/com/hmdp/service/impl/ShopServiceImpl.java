package com.hmdp.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {
    private static final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Shop getByIdRedis(Long id) {
        String shop= stringRedisTemplate.opsForValue().get(RedisConstants.CACHE_SHOP_KEY+id);
        Shop shopRedis= JSONUtil.toBean(shop,Shop.class);
        if(shop!=null){
            return shopRedis;
        }
        Shop shop1= getById(id);
        String json= JSON.toJSONString(shop1);
        if(shop1!=null){
            stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY+id,json,RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
            return shop1;
        }
        return null;
    }

    @Override
    public Result updateByRedis(Shop shop) {
        //更新操作先修改数据库再删除缓存可以让错误概率较低
        //先判断是否传过来id
        if(shop.getId()==null){
            return Result.fail("未发送店铺信息");
        }
        //更新数据库
        updateById(shop);
        //删除缓存
        stringRedisTemplate.delete(RedisConstants.CACHE_SHOP_KEY+shop.getId());
        return Result.ok();
    }
}
