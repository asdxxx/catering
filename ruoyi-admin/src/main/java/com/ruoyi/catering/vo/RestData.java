package com.ruoyi.catering.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-08-20 21:03
 */
@Data
public class RestData {
    @Excel(name = "街道名称")
    private String street;

    @Excel(name = "店名")
    private String name;

    @Excel(name = "地址")
    private String premises;

    @Excel(name = "车牌")
    private String carNo;

    @Excel(name = "回收人员")
    private String recoveryUser;
}