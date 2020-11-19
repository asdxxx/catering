package com.ruoyi.catering.data;

import lombok.Data;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-09-26 16:12
 */
@Data
public class RestaurantQueryData {
    private String sqlString;

    private String[] restaurantIds;
}