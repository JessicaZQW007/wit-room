package com.yhxc.common;

import org.springframework.data.domain.Page;

import java.io.Serializable;

/**
 * 封装分页请求结果
 *
 * @author 赵贺飞
 */
public class PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页的数量
     */
    private int pageSize;
    /**
     * 当前页的数量
     */
    private int size;
    /**
     * 总记录数
     */
    private long total;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public PageInfo() {
    }

    public PageInfo(int size, int pageSize, int pageNum, long total) {
        this.size = size;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.total = total;
    }

    public static PageInfo pageInfo(Page p) {
        PageInfo pi = new PageInfo();
        pi.setPageNum(p.getNumber() + 1);
        pi.setPageSize(p.getSize());
        pi.setTotal(p.getTotalElements());
        pi.setSize(p.getNumberOfElements());
        return pi;
    }
}
