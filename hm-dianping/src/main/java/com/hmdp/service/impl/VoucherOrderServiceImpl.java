package com.hmdp.service.impl;

import com.hmdp.dto.Result;
import com.hmdp.entity.SeckillVoucher;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.mapper.SeckillVoucherMapper;
import com.hmdp.mapper.VoucherOrderMapper;
import com.hmdp.service.IVoucherOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.*;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.AopProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private VoucherOrderMapper voucherOrderMapper;
    @Autowired
    private SeckillVoucherMapper seckillVoucherMapper;
    @Autowired
    private RedisId redisId;
    @Autowired
    private SeckillVoucherServiceImpl seckillVoucherServiceImpl;
    @Autowired
    private RedissonClient redisson6379;
    @Autowired
    private RedissonClient redisson6380;
    @Autowired
    private RedissonClient redisson6381;
    @Override
    public Result order(Long voucherId) throws InterruptedException {
        //查询优惠券信息
        SeckillVoucher seckillVoucher = seckillVoucherMapper.selectById(voucherId);
        //判断秒杀是否开始
        LocalDateTime timeEnd = seckillVoucher.getEndTime();
        LocalDateTime timeBegin=seckillVoucher.getBeginTime();
        if(timeBegin.isAfter(LocalDateTime.now())){
            return Result.fail("秒杀活动暂未开始");
        }else if (timeEnd.isBefore(LocalDateTime.now())){
            return Result.fail("秒杀活动现已结束");
        }
        Integer number =seckillVoucher.getStock();
        //库存不够就返回异常
        if(number<1){
            return Result.fail("库存不足");
        }
        Long userId =UserHolder.getUser().getId();
        //SimpleRedisLock lock=new SimpleRedisLock("order:"+userId,stringRedisTemplate);
        RLock lock1=redisson6379.getLock("lock:order:"+userId);
        RLock lock2=redisson6380.getLock("lock:order:"+userId);
        RLock lock3=redisson6381.getLock("lock:order:"+userId);
        RLock lock=redisson6380.getMultiLock(lock1,lock2,lock3);
        Boolean isLock1=lock.tryLock(1L, TimeUnit.SECONDS);
        //Boolean isLock=lock.tryLock(1200L);
        if(!isLock1){
            return Result.fail("一人只能买一张票");
        }
        try{
            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
            return proxy.createVoucherOrder(voucherId);
        }finally {
            System.out.println("id为"+userId+"的顾客买到票了");
            lock.unlock();
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public Result createVoucherOrder(Long voucherId) {
        //开始的话看库存够不够,够就库存减一,并且创建订单
        List<VoucherOrder> list=voucherOrderMapper.selectByUserId(voucherId,UserHolder.getUser().getId());
        if(!list.isEmpty()){
            return Result.fail("该用户已下过单");
        }
        Boolean success=seckillVoucherServiceImpl.update()
                .setSql("stock=stock-1")
                .eq("voucher_id", voucherId)
                .gt("stock", 0)
                .update();
        if(!success){
            return Result.fail("库存不足");
        }
        Long id= UserHolder.getUser().getId();
        VoucherOrder voucherOrder=new VoucherOrder();
        voucherOrder.setVoucherId(voucherId);
        voucherOrder.setUserId(id);
        voucherOrder.setCreateTime(LocalDateTime.now());
        voucherOrder.setUpdateTime(LocalDateTime.now());
        voucherOrder.setPayTime(LocalDateTime.now());
        voucherOrder.setStatus(1);
        voucherOrder.setId(redisId.nextId("order"));
        voucherOrderMapper.insert(voucherOrder);
        return Result.ok(voucherOrder.getVoucherId() + "下单成功");
    }
}
