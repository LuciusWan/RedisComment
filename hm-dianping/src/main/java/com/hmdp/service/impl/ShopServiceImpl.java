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
    public Result getByIdRedis(Long id) throws InterruptedException {
        String shop= stringRedisTemplate.opsForValue().get(RedisConstants.CACHE_SHOP_KEY+id);
        //查询到对应的value,并且这个value不是"",则店铺存在,直接返回
        if(shop!=null&&!"".equals(shop)){
            Shop shopRedis= JSONUtil.toBean(shop,Shop.class);
            return Result.ok(shopRedis);
        }
        //店铺value不等于null,但是为""，说明缓存和数据库中都没有,直接返回不存在信息
        if(shop!=null){
            return Result.fail("店铺信息不存在");
        }
        //所有线程同时去抢夺互斥锁资源,只会有一个线程抢到
        if(setLock(RedisConstants.LOCK_SHOP_KEY+id)){
            Shop shop1= getById(id);

            //数据没查到,说明这个店铺id不存在,这次缓存穿透了,但是可以把空信息写入redis
            if(shop1==null){
                stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY+id,"",RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
                return Result.fail("店铺信息不存在");
            }
            String json= JSON.toJSONString(shop1);
            //查询到店铺,返回店铺信息,并且把信息写入Redis
            if(shop1!=null){
                stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY+id,json,RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
                return Result.ok(shop1);
            }
            //所有工作做完后释放锁资源
            unLock(RedisConstants.LOCK_SHOP_KEY+id);
        //其他线程休眠对应的时间后重新尝试获取资源(递归)
        }else {
            Thread.sleep(50);
            getByIdRedis(id);
        }
        return Result.fail("店铺信息不存在");
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
    private Boolean setLock(String key){
        Boolean flag= stringRedisTemplate.opsForValue().setIfAbsent(key,"1",10,TimeUnit.SECONDS);
        if(flag==null){
            return false;
        }else if(flag){
            return true;
        }
        return false;
    }
    private void unLock(String key){
        stringRedisTemplate.delete(key);
    }
    public Shop queryWithPassThrough(Long id) {
        String shop= stringRedisTemplate.opsForValue().get(RedisConstants.CACHE_SHOP_KEY+id);
        //查询到对应的value,并且这个value不是"",则店铺存在,直接返回
        if(shop!=null&&!"".equals(shop)){
            Shop shopRedis= JSONUtil.toBean(shop,Shop.class);
            return shopRedis;
        }
        //店铺value不等于null,但是为""，说明缓存和数据库中都没有,直接返回不存在信息
        if(shop!=null){
            return null;
        }
        Shop shop1= getById(id);

        //数据没查到,说明这个店铺id不存在,这次缓存穿透了,但是可以把空信息写入redis
        if(shop1==null){
            stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY+id,"",RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
            return null;
        }
        String json= JSON.toJSONString(shop1);
        //查询到店铺,返回店铺信息,并且把信息写入Redis
        if(shop1!=null){
            stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY+id,json,RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
            return shop1;
        }
        return null;
    }
}
