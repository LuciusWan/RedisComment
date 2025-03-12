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
@TableName("tb_shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商铺名称
     */
    private String name;

    /**
     * 商铺类型的id
     */
    private Long typeId;

    /**
     * 商铺图片，多个图片以','隔开
     */
    private String images;

    /**
     * 商圈，例如陆家嘴
     */
    private String area;

    /**
     * 地址
     */
    private String address;

    /**
     * 经度
     */
    private Double x;

    /**
     * 维度
     */
    private Double y;

    /**
     * 均价，取整数
     */
    private Long avgPrice;

    /**
     * 销量
     */
    private Integer sold;

    /**
     * 评论数量
     */
    private Integer comments;

    /**
     * 评分，1~5分，乘10保存，避免小数
     */
    private Integer score;

    /**
     * 营业时间，例如 10:00-22:00
     */
    private String openHours;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    @TableField(exist = false)
    private Double distance;

    public Shop() {
    }

    public Shop(Long id, String name, Long typeId, String images, String area, String address, Double x, Double y, Long avgPrice, Integer sold, Integer comments, Integer score, String openHours, LocalDateTime createTime, LocalDateTime updateTime, Double distance) {

        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.images = images;
        this.area = area;
        this.address = address;
        this.x = x;
        this.y = y;
        this.avgPrice = avgPrice;
        this.sold = sold;
        this.comments = comments;
        this.score = score;
        this.openHours = openHours;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.distance = distance;
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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return typeId
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * 设置
     * @param typeId
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取
     * @return images
     */
    public String getImages() {
        return images;
    }

    /**
     * 设置
     * @param images
     */
    public void setImages(String images) {
        this.images = images;
    }

    /**
     * 获取
     * @return area
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取
     * @return x
     */
    public Double getX() {
        return x;
    }

    /**
     * 设置
     * @param x
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * 获取
     * @return y
     */
    public Double getY() {
        return y;
    }

    /**
     * 设置
     * @param y
     */
    public void setY(Double y) {
        this.y = y;
    }

    /**
     * 获取
     * @return avgPrice
     */
    public Long getAvgPrice() {
        return avgPrice;
    }

    /**
     * 设置
     * @param avgPrice
     */
    public void setAvgPrice(Long avgPrice) {
        this.avgPrice = avgPrice;
    }

    /**
     * 获取
     * @return sold
     */
    public Integer getSold() {
        return sold;
    }

    /**
     * 设置
     * @param sold
     */
    public void setSold(Integer sold) {
        this.sold = sold;
    }

    /**
     * 获取
     * @return comments
     */
    public Integer getComments() {
        return comments;
    }

    /**
     * 设置
     * @param comments
     */
    public void setComments(Integer comments) {
        this.comments = comments;
    }

    /**
     * 获取
     * @return score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置
     * @param score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取
     * @return openHours
     */
    public String getOpenHours() {
        return openHours;
    }

    /**
     * 设置
     * @param openHours
     */
    public void setOpenHours(String openHours) {
        this.openHours = openHours;
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

    /**
     * 获取
     * @return distance
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * 设置
     * @param distance
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String toString() {
        return "Shop{serialVersionUID = " + serialVersionUID + ", id = " + id + ", name = " + name + ", typeId = " + typeId + ", images = " + images + ", area = " + area + ", address = " + address + ", x = " + x + ", y = " + y + ", avgPrice = " + avgPrice + ", sold = " + sold + ", comments = " + comments + ", score = " + score + ", openHours = " + openHours + ", createTime = " + createTime + ", updateTime = " + updateTime + ", distance = " + distance + "}";
    }
}
