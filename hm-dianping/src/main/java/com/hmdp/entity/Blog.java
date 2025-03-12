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
@TableName("tb_blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商户id
     */
    private Long shopId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户图标
     */
    @TableField(exist = false)
    private String icon;
    /**
     * 用户姓名
     */
    @TableField(exist = false)
    private String name;
    /**
     * 是否点赞过了
     */
    @TableField(exist = false)
    private Boolean isLike;

    /**
     * 标题
     */
    private String title;

    /**
     * 探店的照片，最多9张，多张以","隔开
     */
    private String images;

    /**
     * 探店的文字描述
     */
    private String content;

    /**
     * 点赞数量
     */
    private Integer liked;

    /**
     * 评论数量
     */
    private Integer comments;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    public Blog() {
    }

    public Blog(Long id, Long shopId, Long userId, String icon, String name, Boolean isLike, String title, String images, String content, Integer liked, Integer comments, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.shopId = shopId;
        this.userId = userId;
        this.icon = icon;
        this.name = name;
        this.isLike = isLike;
        this.title = title;
        this.images = images;
        this.content = content;
        this.liked = liked;
        this.comments = comments;
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
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
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
     * @return isLike
     */
    public Boolean getIsLike() {
        return isLike;
    }

    /**
     * 设置
     * @param isLike
     */
    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
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
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取
     * @return liked
     */
    public Integer getLiked() {
        return liked;
    }

    /**
     * 设置
     * @param liked
     */
    public void setLiked(Integer liked) {
        this.liked = liked;
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
        return "Blog{serialVersionUID = " + serialVersionUID + ", id = " + id + ", shopId = " + shopId + ", userId = " + userId + ", icon = " + icon + ", name = " + name + ", isLike = " + isLike + ", title = " + title + ", images = " + images + ", content = " + content + ", liked = " + liked + ", comments = " + comments + ", createTime = " + createTime + ", updateTime = " + updateTime + "}";
    }
}
