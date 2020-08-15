package com.ruoyi.catering.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-08-05 14:40
 */
@Data
public class RestaurantExportVo {
    /**
     * 分区
     */
    @Excel(name = "分区")
    private String dept;

    /**
     * 企业名称
     */
    @Excel(name = "企业名称", width = 30)
    private String name;

    /**
     * 地址
     */
    @Excel(name = "地址", width = 40)
    private String premises;

    /**
     * 类型
     */
    @Excel(name = "类型", readConverterExp = "1=小型,2=中型,3=大型")
    private String size;

    /**
     * 运维人员
     */
    @Excel(name = "运维人员")
    private String recycle;

    /**
     * 区域管理
     */
    @Excel(name = "区域管理")
    private String manager;

    /**
     * 收运热线
     */
    @Excel(name = "收运热线")
    private String tel;

    /**
     * 二维码数据
     */
    @Excel(name = "二维码数据", width = 120)
    private String qrData;
}