package com.ruoyi.catering.vo;

import com.ruoyi.catering.domain.Garbage;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import lombok.Data;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-12 10:13
 */
@Data
public class RecoveryRecordVo extends RecoveryRecord {
    @Excel(name = "餐馆信息", targetAttr = "name", type = Excel.Type.EXPORT)
    private Restaurant restaurant;

    @Excel(name = "回收人员", targetAttr = "userName", type = Excel.Type.EXPORT)
    private SysUser user;

    @Excel(name = "垃圾分类", targetAttr = "name", type = Excel.Type.EXPORT)
    private Garbage garbage;

    @Excel(name = "所属区域", targetAttr = "deptName", type = Excel.Type.EXPORT)
    private SysDept dept;
}