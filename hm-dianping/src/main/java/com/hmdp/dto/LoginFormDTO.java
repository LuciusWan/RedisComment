package com.hmdp.dto;

import lombok.Data;


public class LoginFormDTO {
    private String phone;
    private String code;
    private String password;

    public LoginFormDTO() {
    }

    public LoginFormDTO(String phone, String code, String password) {
        this.phone = phone;
        this.code = code;
        this.password = password;
    }

    /**
     * 获取
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "LoginFormDTO{phone = " + phone + ", code = " + code + ", password = " + password + "}";
    }
}
