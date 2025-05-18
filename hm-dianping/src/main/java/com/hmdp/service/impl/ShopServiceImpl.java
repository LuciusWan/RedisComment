package com.hmdp.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.benmanes.caffeine.cache.Cache;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.RedisData;
import com.hmdp.utils.SystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    @Autowired
    private RedisProperties redisProperties;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private Cache<String,Object> caffeineCache;
    @Override
    public Result getByIdRedis(Long id) throws InterruptedException {
        String shop= (String) caffeineCache.getIfPresent(RedisConstants.CACHE_SHOP_KEY+id);
        System.out.println(shop+"====================================================================");
        if(shop==null){
            System.out.println(RedisConstants.CACHE_SHOP_KEY+id+"_______________________________________________________________________");
            System.out.println("redis查询++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            caffeineCache.put(RedisConstants.CACHE_SHOP_KEY+id, shop);
            shop= stringRedisTemplate.opsForValue().get(RedisConstants.CACHE_SHOP_KEY+id);
        }
        //查询到对应的value,并且这个value不是"",则店铺存在,直接返回
        if(shop!=null&&!"".equals(shop)){
            Shop shop1=JSON.parseObject(shop,Shop.class);
            return Result.ok(shop1);
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
                stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY+id,"",cacheAvalanche(), TimeUnit.MINUTES);
                return Result.fail("店铺信息不存在");
            }
            String json= JSON.toJSONString(shop1);
            //查询到店铺,返回店铺信息,并且把信息写入Redis
            if(shop1!=null){
                stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY+id,json,cacheAvalanche(), TimeUnit.MINUTES);
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
    public Result redisLogicExpireTime(Long id){
        String shop= (String) caffeineCache.getIfPresent(RedisConstants.CACHE_SHOP_KEY+id);
        if(shop==null){
            shop= stringRedisTemplate.opsForValue().get(RedisConstants.CACHE_SHOP_KEY+id);
            caffeineCache.put(RedisConstants.CACHE_SHOP_KEY+id, shop);
        }
        logger.info(shop);
        //查询到对应的value,并且这个value不是"",则店铺存在,直接返回
        if(shop!=null&&!"".equals(shop)){
            RedisData redisData=JSONUtil.toBean(shop,RedisData.class);
            if(redisData.getExpireTime().isAfter(LocalDateTime.now())){
                Shop shopRedis= redisData.getData();
                System.out.println(shopRedis);
                logger.info("查询redis直接输出");
                return Result.ok(shopRedis);
            }
        }
        //店铺value不等于null,但是为""，说明缓存和数据库中都没有,直接返回不存在信息
        if(shop!=null&&shop.equals("")){
            return Result.fail("店铺信息不存在");
        }
        //所有线程同时去抢夺互斥锁资源,只会有一个线程抢到
        if(setLock(RedisConstants.LOCK_SHOP_KEY+id)){
            Shop shop1= getById(id);
            //数据没查到,说明这个店铺id不存在,这次缓存穿透了,但是可以把空信息写入redis
            if(shop1==null){
                stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY+id,"",cacheAvalanche(), TimeUnit.MINUTES);
                return Result.fail("店铺信息不存在");
            }
            RedisData redisData=new RedisData();
            redisData.setData(shop1);
            redisData.setExpireTime(LocalDateTime.now().plusMinutes(cacheAvalanche()));
            String json= JSON.toJSONString(redisData);
            //查询到店铺,返回店铺信息,并且把信息写入Redis
            if(shop1!=null){
                stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY+id,json,cacheAvalanche(), TimeUnit.MINUTES);
                return Result.ok(shop1);
            }
            //所有工作做完后释放锁资源
            unLock(RedisConstants.LOCK_SHOP_KEY+id);
            //其他线程休眠对应的时间后重新尝试获取资源(递归)
        }else {
            RedisData redisData=new RedisData();
            redisData.setData(JSONUtil.toBean(shop,Shop.class));
            Shop redis=redisData.getData();
            return Result.ok(redis);
        }
        return Result.fail("店铺信息不存在");
    }

    @Override
    public Result pageByGeo(Integer typeId, Integer current, Double x, Double y) {
        if(x==null||y==null){
            Page<Shop> page = query()
                    .eq("type_id", typeId)
                    .page(new Page<>(current, SystemConstants.DEFAULT_PAGE_SIZE));
            // 返回数据
            return Result.ok(page.getRecords());
        }
        //根据店铺类型查询店铺列表
        int from = (current - 1) * SystemConstants.DEFAULT_PAGE_SIZE;
        int end = from + SystemConstants.DEFAULT_PAGE_SIZE;
        String key=RedisConstants.SHOP_GEO_KEY+typeId;
        GeoResults<RedisGeoCommands.GeoLocation<String>> result = stringRedisTemplate.opsForGeo().search(
                key,
                GeoReference.fromCoordinate(x, y),
                //默认米为单位,5公里
                new Distance(5000),
                RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs().includeDistance().limit(end)
        );
        if(result==null){
            return Result.fail("Redis罢工了");
        }
        int sum=0;
        List<Long> ids=new ArrayList<>();
        List<Distance> distances=new ArrayList<>();
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> r : result) {
            if (sum<from) {
                sum++;
                continue;
            }
            RedisGeoCommands.GeoLocation<String> location = r.getContent();
            ids.add(Long.valueOf(location.getName()));
            distances.add(r.getDistance());
            /*System.out.println("Member: " + location.getName());
            System.out.println("Longitude: " + location.getPoint().getX());
            System.out.println("Latitude: " + location.getPoint().getY());
            System.out.println("Distance: " + r.getDistance());*/
        }
        List<Shop> shops=new ArrayList<>();
        for (int i =0;i<ids.size();i++) {
            Shop shop= shopMapper.selectById(ids.get(i));
            shop.setDistance(Double.valueOf(distances.get(i).toString().replaceAll("[^\\d.]", "")));
            if(shop!=null){
                shops.add(shop);
            }
        }
        //stringRedisTemplate.opsForHyperLogLog().add("2025.4.28","hello");
        return Result.ok(shops);
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
        Boolean flag= stringRedisTemplate.opsForValue().setIfAbsent(key,"lock!!!",10,TimeUnit.SECONDS);
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
    private Long cacheAvalanche(){
        Random random=new Random();
        Long number=random.nextInt(11)+20L;
        return number;
    }


    public List<Shop> listById(Long id) {
        return shopMapper.selectByTypeId(id);
    }
}
