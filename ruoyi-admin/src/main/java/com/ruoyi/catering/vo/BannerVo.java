package com.ruoyi.catering.vo;

import com.ruoyi.catering.domain.Banner;
import lombok.Data;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-12 18:39
 */
@Data
public class BannerVo extends Banner {
    private String[] indexUrlArray;

    private String[] merchantUrlArray;

    private String[] collectorUrlArray;
}