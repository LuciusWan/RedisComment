package com.hmdp.dto;

import lombok.Data;


public class UserDTO {
    private Long id;
    private String nickName;
    private String icon;

    public UserDTO() {
    }

    public UserDTO(Long id, String nickName, String icon) {
        this.id = id;
        this.nickName = nickName;
        this.icon = icon;
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
     * @return nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String toString() {
        return "UserDTO{id = " + id + ", nickName = " + nickName + ", icon = " + icon + "}";
    }
}
