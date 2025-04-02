package com.hmdp;
import com.hmdp.mapper.SeckillVoucherMapper;
import com.hmdp.service.impl.VoucherOrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RefreshData {
    private static final Long VOUCHER_ID=16L;
    private static final Long STOCK=1000L;
    @Autowired
    private VoucherOrderServiceImpl voucherOrderService;
    @Autowired
    private SeckillVoucherMapper seckillVoucherMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test() {
        //删除所有订单
        voucherOrderService.deleteByVoucherId(VOUCHER_ID);
        //将剩余票数刷新为指定数值
        seckillVoucherMapper.updateByVoucherId(VOUCHER_ID,STOCK);
        //同步修改redis的剩余票数
        stringRedisTemplate.opsForValue().set("Inventory"+VOUCHER_ID+":stock",STOCK+"");
        //删除redis的set
        stringRedisTemplate.delete("Inventory"+VOUCHER_ID+":set");
    }
}
