package com.ruoyi.catering.vo;

import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.domain.SysDept;
import lombok.Data;

import java.util.List;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-09 12:16
 */
@Data
public class RestaurantVo extends Restaurant {
    @Excel(name = "归属区域", targetAttr = "deptName", type = Excel.Type.EXPORT)
    private SysDept dept;

    @Excel(name = "二维码数据", width = 150)
    private String qrCodeData;

    private String colorStatus;
}