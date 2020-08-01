package com.ruoyi.catering.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-11 17:33
 */
@Data
public class RestaurantImportData {
//    /**
//     * 客户编码
//     */
//    @Excel(name = "客户编码")
//    private String restaurantNo;
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
     * 合同签订日期
     */
    @Excel(name = "合同签订日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signDate;

    /**
     * 合同到期时间
     */
    @Excel(name = "合同到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireDate;

    /**
     * 规格
     */
    @Excel(name = "规格")
    private String size;

    /**
     * 营业状态
     */
    @Excel(name = "营业状态")
    private String status;
//    /**
//     * 简称
//     */
//    @Excel(name = "简称")
//    private String shortName;
//
//    /**
//     * 餐饮类型id
//     */
//    @Excel(name = "餐饮类型")
//    private String diningType;
//
//
//
//
////    private String region;
//
//    /**
//     * 面积
//     */
//    @Excel(name = "面积")
//    private BigDecimal area;
//
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
//
//    /**
//     * 有无油水分离器（0有 1无）
//     */
//    @Excel(name = "有无油水分离器")
//    private String hasOilWaterSeparator;
//
//    /**
//     * 有无油烟净化器（0有 1无）
//     */
//    @Excel(name = "有无油烟净化器")
//    private String hasFumeCleaner;
//
//    /**
//     * 有无排水许可证（0有 1无）
//     */
//    @Excel(name = "有无排水许可证")
//    private String hasDischargePermit;
//
//    /**
//     * 排水许可证号
//     */
//    @Excel(name = "排水许可证号")
//    private String dischargeNo;
//
//    /**
//     * 排水许可证有效期
//     */
//    @Excel(name = "排水许可证有效期", width = 30, dateFormat = "yyyy-MM-dd")
//    private Date validityDay;
//
//    /**
//     * 停业日期
//     */
//    @Excel(name = "停业日期", width = 30, dateFormat = "yyyy-MM-dd")
//    private Date closedDay;
//
//    /**
//     * 燃气类型id
//     */
//    @Excel(name = "燃气类型")
//    private String gasType;
//
//    /**
//     * 废油回收协议（0已签 1未签）
//     */
//    @Excel(name = "废油回收协议")
//    private String haskwoRecoveryAgreement;

}