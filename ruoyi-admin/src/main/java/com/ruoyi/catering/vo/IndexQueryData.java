package com.ruoyi.catering.vo;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-08-21 11:05
 */
@Data
public class IndexQueryData extends BaseEntity {
    private Long deptId;

    private String startDate;

    private String endDate;
}