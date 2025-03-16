package com.hmdp.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.hmdp.entity.Shop;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.mapper.UserInfoMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {
    private static final Logger logger = LoggerFactory.getLogger(ShopTypeServiceImpl.class);
    private final UserInfoMapper userInfoMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ShopTypeServiceImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public List<ShopType> listByRedis() {
        List<ShopType> list = new ArrayList<>();
        //查询Redis中是否有
        for (int i = 1; i < 11; i++) {
            String shopTypeJson = stringRedisTemplate.opsForValue().get(RedisConstants.CACHE_SHOP_TYPE_KEY+i);
            ShopType shopType= JSONUtil.toBean(shopTypeJson, ShopType.class);
            if(shopType.getId()==null){
                break;
            }
            list.add(shopType);
        }
        //如果有的话直接返回
        if(list!=null&&list.size()>0){
            return list;
        }
        logger.info("没用redis中的数据");
        //没有就查数据库
        list=query().orderByAsc("sort").list();
        //查到的东西直接存入Redis
        for (int i = 0; i < 10; i++) {
            String shopType= JSON.toJSONString(list.get(i));
            stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_TYPE_KEY+(i+1),shopType);
        }
        System.out.println(list);
        //数据库查完返还给前端
        return list;
    }
}
