package com.ruoyi.catering.vo;

import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.domain.WarnMsg;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-12 23:29
 */
@Data
public class WarnMsgVo extends WarnMsg {
    @Excel(name = "餐馆信息", targetAttr = "name", type = Excel.Type.EXPORT)
    private Restaurant restaurant;
}