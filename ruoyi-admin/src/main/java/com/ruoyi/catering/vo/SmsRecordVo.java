package com.ruoyi.catering.vo;

import com.ruoyi.catering.domain.Garbage;
import com.ruoyi.catering.domain.SmsRecord;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-11 23:57
 */
@Data
public class SmsRecordVo extends SmsRecord {
    @Excel(name = "发送类型", targetAttr = "name", type = Excel.Type.EXPORT)
    private Garbage garbage;
}