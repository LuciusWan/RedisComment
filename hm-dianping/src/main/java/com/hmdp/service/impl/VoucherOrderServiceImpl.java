package com.hmdp.service.impl;

import com.hmdp.dto.Result;
import com.hmdp.entity.SeckillVoucher;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.mapper.SeckillVoucherMapper;
import com.hmdp.mapper.VoucherOrderMapper;
import com.hmdp.service.IVoucherOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.RedisId;
import com.hmdp.utils.ThreadLocalUtils;
import com.hmdp.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result order(Long voucherId) {
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
        //开始的话看库存够不够,够就库存减一,并且创建订单
        Integer number =seckillVoucher.getStock();
        if(number>=1){
            number--;
            seckillVoucher.setStock(number);
            seckillVoucherMapper.updateById(seckillVoucher);
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
            return Result.ok(voucherOrder.getVoucherId()+"下单成功");
        }//库存不够就返回异常
        else {
            return Result.fail("优惠券已经卖完");
        }
    }
}
