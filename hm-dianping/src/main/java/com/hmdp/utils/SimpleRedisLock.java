package com.hmdp.utils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;


import java.util.*;
import java.util.concurrent.TimeUnit;
public class SimpleRedisLock implements ILock {
    private StringRedisTemplate stringRedisTemplate;
    private static final String LOCK_PREFIX = "lock:";
    private static final String ID_PREFIX = UUID.randomUUID().toString();
    private static DefaultRedisScript<Long> UNLOCK_SCRIPT;
    static {
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("unLock.lua"));
        UNLOCK_SCRIPT.setResultType(Long.class);
    }
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
        List list = new ArrayList();
        list.add(LOCK_PREFIX+lockName);
        stringRedisTemplate.execute(
                UNLOCK_SCRIPT,
                list,
                ID_PREFIX+ Thread.currentThread().getId());
    }

    /*@Override
    public void unlock() {
        String UnlockName= stringRedisTemplate.opsForValue().get(LOCK_PREFIX+lockName);
        String threadId =ID_PREFIX+ Thread.currentThread().getId();
        if(threadId.equals(UnlockName)){
            stringRedisTemplate.delete(LOCK_PREFIX+lockName);
        }
    }*/
}
