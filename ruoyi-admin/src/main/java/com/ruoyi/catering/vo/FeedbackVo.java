package com.ruoyi.catering.vo;

import com.ruoyi.catering.domain.Feedback;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.domain.SysUser;
import lombok.Data;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-11 18:51
 */
@Data
public class FeedbackVo extends Feedback {
    @Excel(name = "上报用户", targetAttr = "userName", type = Excel.Type.EXPORT)
    private SysUser user;

    @Excel(name = "餐馆信息", targetAttr = "name", type = Excel.Type.EXPORT)
    private Restaurant restaurant;
}