package com.ruoyi.catering.vo;

import com.ruoyi.catering.domain.Slideshow;
import lombok.Data;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-12 12:51
 */
@Data
public class SlideshowVo extends Slideshow {
    private String[] urlArray;
}