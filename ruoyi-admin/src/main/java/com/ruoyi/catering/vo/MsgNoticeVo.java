package com.ruoyi.catering.vo;

import com.ruoyi.catering.domain.MsgNotice;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.system.domain.SysUser;
import lombok.Data;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-08-11 21:46
 */
@Data
public class MsgNoticeVo extends MsgNotice {
    private SysUser user;

    private Restaurant restaurant;
}