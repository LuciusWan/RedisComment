package com.hmdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_voucher_order")
public class VoucherOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 下单的用户id
     */
    private Long userId;

    /**
     * 购买的代金券id
     */
    private Long voucherId;

    /**
     * 支付方式 1：余额支付；2：支付宝；3：微信
     */
    private Integer payType;

    /**
     * 订单状态，1：未支付；2：已支付；3：已核销；4：已取消；5：退款中；6：已退款
     */
    private Integer status;

    /**
     * 下单时间
     */
    private LocalDateTime createTime;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 核销时间
     */
    private LocalDateTime useTime;

    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    public VoucherOrder() {
    }

    public VoucherOrder( Long id, Long userId, Long voucherId, Integer payType, Integer status, LocalDateTime createTime, LocalDateTime payTime, LocalDateTime useTime, LocalDateTime refundTime, LocalDateTime updateTime) {
        this.id = id;
        this.userId = userId;
        this.voucherId = voucherId;
        this.payType = payType;
        this.status = status;
        this.createTime = createTime;
        this.payTime = payTime;
        this.useTime = useTime;
        this.refundTime = refundTime;
        this.updateTime = updateTime;
    }

    /**
     * 获取
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取
     * @return userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取
     * @return voucherId
     */
    public Long getVoucherId() {
        return voucherId;
    }

    /**
     * 设置
     * @param voucherId
     */
    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    /**
     * 获取
     * @return payType
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * 设置
     * @param payType
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 获取
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取
     * @return createTime
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 设置
     * @param createTime
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取
     * @return payTime
     */
    public LocalDateTime getPayTime() {
        return payTime;
    }

    /**
     * 设置
     * @param payTime
     */
    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取
     * @return useTime
     */
    public LocalDateTime getUseTime() {
        return useTime;
    }

    /**
     * 设置
     * @param useTime
     */
    public void setUseTime(LocalDateTime useTime) {
        this.useTime = useTime;
    }

    /**
     * 获取
     * @return refundTime
     */
    public LocalDateTime getRefundTime() {
        return refundTime;
    }

    /**
     * 设置
     * @param refundTime
     */
    public void setRefundTime(LocalDateTime refundTime) {
        this.refundTime = refundTime;
    }

    /**
     * 获取
     * @return updateTime
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置
     * @param updateTime
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String toString() {
        return "VoucherOrder{serialVersionUID = " + serialVersionUID + ", id = " + id + ", userId = " + userId + ", voucherId = " + voucherId + ", payType = " + payType + ", status = " + status + ", createTime = " + createTime + ", payTime = " + payTime + ", useTime = " + useTime + ", refundTime = " + refundTime + ", updateTime = " + updateTime + "}";
    }
}
