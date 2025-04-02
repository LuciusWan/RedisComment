package com.hmdp.mapper;

import com.hmdp.entity.SeckillVoucher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 秒杀优惠券表，与优惠券是一对一关系 Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2022-01-04
 */
public interface SeckillVoucherMapper extends BaseMapper<SeckillVoucher> {
    @Update("update hmdp.tb_seckill_voucher set hmdp.tb_seckill_voucher.stock = #{stock} where hmdp.tb_seckill_voucher.voucher_id=#{voucherId}")
    void updateByVoucherId(Long voucherId, Long stock);
}
