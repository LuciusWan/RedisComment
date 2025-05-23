package com.hmdp.service;

import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IShopService extends IService<Shop> {

    Result getByIdRedis(Long id) throws InterruptedException;

    Result updateByRedis(Shop shop);
    Result redisLogicExpireTime(Long id) throws InterruptedException;

    Result pageByGeo(Integer typeId, Integer current, Double x, Double y);
}
