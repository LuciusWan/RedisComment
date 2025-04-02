package com.hmdp.Consumer;

import com.hmdp.entity.VoucherOrder;
import com.hmdp.service.IVoucherOrderService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class VoucherOrderConsumer {
    @Autowired
    private IVoucherOrderService voucherOrderService;
    @Resource
    private RedissonClient redisson6379;
    @Resource
    private RedissonClient redisson6380;
    @Resource
    private RedissonClient redisson6381;
    @Transactional
    @RabbitListener(queues = "voucher_order_queue")
    public void handle(VoucherOrder voucherOrder) throws InterruptedException {
        //SimpleRedisLock lock=new SimpleRedisLock("order:"+userId,stringRedisTemplate);
        RLock lock1=redisson6379.getLock("lock:order:"+voucherOrder.getUserId());
        RLock lock2=redisson6380.getLock("lock:order:"+voucherOrder.getUserId());
        RLock lock3=redisson6381.getLock("lock:order:"+voucherOrder.getUserId());
        RLock lock=redisson6380.getMultiLock(lock1,lock2,lock3);
        Boolean isLock1=lock.tryLock(1L, TimeUnit.SECONDS);
        //Boolean isLock=lock.tryLock(1200L);
        if(!isLock1){
            return ;
        }
        try{
            voucherOrderService.createVoucherOrder(voucherOrder);
        }finally {
            System.out.println("订单处理成功：" + voucherOrder.getId());
            lock.unlock();
        }
    }
}