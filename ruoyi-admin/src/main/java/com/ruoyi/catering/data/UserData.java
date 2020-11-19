package com.ruoyi.catering.data;

import lombok.Data;

/**
 * @program: catering
 * @description: 返回用户信息
 * @author: liu sheng yin
 * @create: 2020-07-24 11:25
 */
@Data
public class UserData {
    private Long userId;

    private Integer type;

    private String loginName;

    private String userName;

    private String phonenumber;

    private String carNo;

    private String avatar;
}