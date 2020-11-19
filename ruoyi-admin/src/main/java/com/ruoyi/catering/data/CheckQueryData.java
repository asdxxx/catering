package com.ruoyi.catering.data;

import lombok.Data;

/**
 * @program: catering
 * @description: 查询参数
 * @author: liu sheng yin
 * @create: 2020-09-26 09:03
 */
@Data
public class CheckQueryData {
    //餐馆id
    private Long restaurantId;

    //餐馆名称
    private String restaurantName;

    //查询开始日期
    private String startDate;

    //查询结束日期
    private String endDate;

    //用户id
    private Long userId;

    //登陆用户id
    private Long loginUserId;

    //页数
    private Integer pageNum;

    //页码
    private Integer pageSize;
}