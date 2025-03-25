package com.hmdp.utils;

public interface ILock {
    Boolean tryLock(Long timeoutSec);
    void unlock();
}
