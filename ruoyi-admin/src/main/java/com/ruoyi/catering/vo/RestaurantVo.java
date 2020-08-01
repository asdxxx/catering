package com.ruoyi.catering.vo;

import com.ruoyi.catering.domain.DiningType;
import com.ruoyi.catering.domain.GasType;
import com.ruoyi.catering.domain.Region;
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
    //    @Excel(name = "餐饮类型", targetAttr = "name", type = Excel.Type.EXPORT)
    private DiningType diningType;

    //    @Excel(name = "所属区域", targetAttr = "regionName", type = Excel.Type.EXPORT)
//    private Region region;
    @Excel(name = "归属区域", targetAttr = "deptName", type = Excel.Type.EXPORT)
    private SysDept dept;

    private List<GasType> gasTypes;

    //    @Excel(name = "燃气类型")
    private String gasTypeNames;

    private Integer restaurantKindsIndex;

    @Excel(name = "二维码数据", width = 150)
    private String qrCodeData;
}