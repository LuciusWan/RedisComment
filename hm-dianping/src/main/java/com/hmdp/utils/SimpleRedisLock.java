package com.hmdp.utils;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
public class SimpleRedisLock implements ILock {
    private StringRedisTemplate stringRedisTemplate;
    private static final String LOCK_PREFIX = "lock:";
    private static final String ID_PREFIX = UUID.randomUUID().toString();
    private String lockName;
    public SimpleRedisLock(String lockName,StringRedisTemplate stringRedisTemplate) {
        this.lockName = lockName;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @Override
    public Boolean tryLock(Long timeoutSec) {
        String threadId =ID_PREFIX+ Thread.currentThread().getId();
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(LOCK_PREFIX+lockName,threadId,timeoutSec, TimeUnit.SECONDS);
        //这样写的话,是true就返回true,如果是false或者null都返回false
        return Boolean.TRUE.equals(result);
    }

    @Override
    public void unlock() {
        String UnlockName= stringRedisTemplate.opsForValue().get(LOCK_PREFIX+lockName);
        String threadId =ID_PREFIX+ Thread.currentThread().getId();
        if(threadId.equals(UnlockName)){
            stringRedisTemplate.delete(LOCK_PREFIX+lockName);
        }

    }
}
