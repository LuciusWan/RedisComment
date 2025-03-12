package com.hmdp.dto;

import lombok.Data;

import java.util.List;


public class ScrollResult {
    private List<?> list;
    private Long minTime;
    private Integer offset;

    public ScrollResult() {
    }

    public ScrollResult(List<?> list, Long minTime, Integer offset) {
        this.list = list;
        this.minTime = minTime;
        this.offset = offset;
    }

    /**
     * 获取
     * @return list
     */
    public List<?> getList() {
        return list;
    }

    /**
     * 设置
     * @param list
     */
    public void setList(List<?> list) {
        this.list = list;
    }

    /**
     * 获取
     * @return minTime
     */
    public Long getMinTime() {
        return minTime;
    }

    /**
     * 设置
     * @param minTime
     */
    public void setMinTime(Long minTime) {
        this.minTime = minTime;
    }

    /**
     * 获取
     * @return offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * 设置
     * @param offset
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String toString() {
        return "ScrollResult{list = " + list + ", minTime = " + minTime + ", offset = " + offset + "}";
    }
}
