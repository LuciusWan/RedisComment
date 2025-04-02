package com.hmdp.mapper;

import com.hmdp.entity.VoucherOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface VoucherOrderMapper extends BaseMapper<VoucherOrder> {
    @Select("select * from tb_voucher_order where voucher_id=#{voucherId} and user_id=#{userId}")
    List<VoucherOrder> selectByUserId(Long voucherId, Long userId);
    @Delete("delete from hmdp.tb_voucher_order where hmdp.tb_voucher_order.voucher_id=#{voucherId}")
    void deleteByVoucherId(Long voucherId);
}
