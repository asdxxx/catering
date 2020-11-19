package com.ruoyi.catering.data;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @program: catering
 * @description: 分页数据
 * @author: liu sheng yin
 * @create: 2020-09-27 10:36
 */
@Data
public class PageData<T> {
    private List<T> data;

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int total;

    public PageData(List<T> data, int pageNum, int pageSize, int total) {
        this.data = data;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }
}