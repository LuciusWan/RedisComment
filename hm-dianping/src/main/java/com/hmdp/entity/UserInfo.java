package com.hmdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-24
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 个人介绍，不要超过128个字符
     */
    private String introduce;

    /**
     * 粉丝数量
     */
    private Integer fans;

    /**
     * 关注的人的数量
     */
    private Integer followee;

    /**
     * 性别，0：男，1：女
     */
    private Boolean gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 积分
     */
    private Integer credits;

    /**
     * 会员级别，0~9级,0代表未开通会员
     */
    private Boolean level;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    public UserInfo() {
    }

    public UserInfo(Long userId, String city, String introduce, Integer fans, Integer followee, Boolean gender, LocalDate birthday, Integer credits, Boolean level, LocalDateTime createTime, LocalDateTime updateTime) {

        this.userId = userId;
        this.city = city;
        this.introduce = introduce;
        this.fans = fans;
        this.followee = followee;
        this.gender = gender;
        this.birthday = birthday;
        this.credits = credits;
        this.level = level;
        this.createTime = createTime;
        this.updateTime = updateTime;
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
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取
     * @return introduce
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * 设置
     * @param introduce
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * 获取
     * @return fans
     */
    public Integer getFans() {
        return fans;
    }

    /**
     * 设置
     * @param fans
     */
    public void setFans(Integer fans) {
        this.fans = fans;
    }

    /**
     * 获取
     * @return followee
     */
    public Integer getFollowee() {
        return followee;
    }

    /**
     * 设置
     * @param followee
     */
    public void setFollowee(Integer followee) {
        this.followee = followee;
    }

    /**
     * 获取
     * @return gender
     */
    public Boolean getGender() {
        return gender;
    }

    /**
     * 设置
     * @param gender
     */
    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    /**
     * 获取
     * @return birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * 设置
     * @param birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取
     * @return credits
     */
    public Integer getCredits() {
        return credits;
    }

    /**
     * 设置
     * @param credits
     */
    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    /**
     * 获取
     * @return level
     */
    public Boolean getLevel() {
        return level;
    }

    /**
     * 设置
     * @param level
     */
    public void setLevel(Boolean level) {
        this.level = level;
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
        return "UserInfo{serialVersionUID = " + serialVersionUID + ", userId = " + userId + ", city = " + city + ", introduce = " + introduce + ", fans = " + fans + ", followee = " + followee + ", gender = " + gender + ", birthday = " + birthday + ", credits = " + credits + ", level = " + level + ", createTime = " + createTime + ", updateTime = " + updateTime + "}";
    }
}
