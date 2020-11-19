package com.ruoyi.catering.data;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: catering
 * @description: 商户导入数据
 * @author: liu sheng yin
 * @create: 2020-07-11 17:33
 */
@Data
public class RestaurantImportData {
    /**
     * 合同签订日期
     */
    @Excel(name = "合同签订日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signDate;

    /**
     * 店名
     */
    @Excel(name = "店名")
    private String name;

    /**
     * 所属区域id
     */
    @Excel(name = "所属区域")
    private String dept;

    /**
     * 合同到期时间
     */
    @Excel(name = "合同到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireDate;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String legalPerson;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String tel;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String premises;

    /**
     * 规格
     */
    @Excel(name = "规格")
    private String size;

    /**
     * 商户性质
     */
    @Excel(name = "商户性质")
    private String nature;

    /**
     * 营业状态
     */
    @Excel(name = "营业状态")
    private String status;

//    /**
//     * 经度
//     */
//    @Excel(name = "经度")
//    private String longitude;
//
//    /**
//     * 纬度
//     */
//    @Excel(name = "纬度")
//    private String latitude;
}