package com.hmdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("tb_voucher")
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商铺id
     */
    private Long shopId;

    /**
     * 代金券标题
     */
    private String title;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 使用规则
     */
    private String rules;

    /**
     * 支付金额
     */
    private Long payValue;

    /**
     * 抵扣金额
     */
    private Long actualValue;

    /**
     * 优惠券类型
     */
    private Integer type;

    /**
     * 优惠券类型
     */
    private Integer status;
    /**
     * 库存
     */
    @TableField(exist = false)
    private Integer stock;

    /**
     * 生效时间
     */
    @TableField(exist = false)
    private LocalDateTime beginTime;

    /**
     * 失效时间
     */
    @TableField(exist = false)
    private LocalDateTime endTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    public Voucher() {
    }

    public Voucher(Long id, Long shopId, String title, String subTitle, String rules, Long payValue, Long actualValue, Integer type, Integer status, Integer stock, LocalDateTime beginTime, LocalDateTime endTime, LocalDateTime createTime, LocalDateTime updateTime) {

        this.id = id;
        this.shopId = shopId;
        this.title = title;
        this.subTitle = subTitle;
        this.rules = rules;
        this.payValue = payValue;
        this.actualValue = actualValue;
        this.type = type;
        this.status = status;
        this.stock = stock;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.createTime = createTime;
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
     * @return shopId
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * 设置
     * @param shopId
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取
     * @return subTitle
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * 设置
     * @param subTitle
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    /**
     * 获取
     * @return rules
     */
    public String getRules() {
        return rules;
    }

    /**
     * 设置
     * @param rules
     */
    public void setRules(String rules) {
        this.rules = rules;
    }

    /**
     * 获取
     * @return payValue
     */
    public Long getPayValue() {
        return payValue;
    }

    /**
     * 设置
     * @param payValue
     */
    public void setPayValue(Long payValue) {
        this.payValue = payValue;
    }

    /**
     * 获取
     * @return actualValue
     */
    public Long getActualValue() {
        return actualValue;
    }

    /**
     * 设置
     * @param actualValue
     */
    public void setActualValue(Long actualValue) {
        this.actualValue = actualValue;
    }

    /**
     * 获取
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
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
     * @return stock
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置
     * @param stock
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取
     * @return beginTime
     */
    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    /**
     * 设置
     * @param beginTime
     */
    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取
     * @return endTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * 设置
     * @param endTime
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
        return "Voucher{serialVersionUID = " + serialVersionUID + ", id = " + id + ", shopId = " + shopId + ", title = " + title + ", subTitle = " + subTitle + ", rules = " + rules + ", payValue = " + payValue + ", actualValue = " + actualValue + ", type = " + type + ", status = " + status + ", stock = " + stock + ", beginTime = " + beginTime + ", endTime = " + endTime + ", createTime = " + createTime + ", updateTime = " + updateTime + "}";
    }
}
