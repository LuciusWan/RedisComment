package com.hmdp.utils;

import com.hmdp.entity.Shop;
import lombok.Data;

import java.time.LocalDateTime;

public class RedisData {
    private LocalDateTime expireTime;
    private Shop data;


    public RedisData() {
    }

    public RedisData(LocalDateTime expireTime, Shop data) {
        this.expireTime = expireTime;
        this.data = data;
    }

    /**
     * 获取
     * @return expireTime
     */
    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    /**
     * 设置
     * @param expireTime
     */
    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 获取
     * @return data
     */
    public Shop getData() {
        return data;
    }

    /**
     * 设置
     * @param data
     */
    public void setData(Shop data) {
        this.data = data;
    }

    public String toString() {
        return "RedisData{expireTime = " + expireTime + ", data = " + data + "}";
    }
}
