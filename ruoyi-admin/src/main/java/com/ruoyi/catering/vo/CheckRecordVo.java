package com.ruoyi.catering.vo;

import com.ruoyi.catering.domain.CheckRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import lombok.Data;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-23 14:44
 */
@Data
public class CheckRecordVo extends CheckRecord {
    @Excel(name = "餐馆信息", targetAttr = "name", type = Excel.Type.EXPORT)
    private Restaurant restaurant;

    @Excel(name = "检查人员", targetAttr = "userName", type = Excel.Type.EXPORT)
    private SysUser user;

    @Excel(name = "所属区域", targetAttr = "deptName", type = Excel.Type.EXPORT)
    private SysDept dept;
}