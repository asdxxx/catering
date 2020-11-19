package com.ruoyi.api;

import com.ruoyi.catering.domain.MsgNotice;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.IMsgNoticeService;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.utils.BusinessUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-08-12 08:32
 */
@Slf4j
@RestController
@RequestMapping("/api/msgNotice")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "消息通知接口")
public class MsgNoticeApiController extends BaseController {
    @Autowired
    private IMsgNoticeService msgNoticeService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IRestaurantService restaurantService;

    @ApiOperation("获取用户未读消息通知")
    @GetMapping(value = "/badgeNumber")
    public AjaxResult badgeNumber(Long userId) {
        MsgNotice msgNotice = new MsgNotice();
        msgNotice.setUserId(userId);
        msgNotice.setHasRead("N");
        List<MsgNotice> msgNotices = msgNoticeService.selectMsgNoticeList(msgNotice);
        return AjaxResult.success("获取成功", msgNotices.size());
    }

    @ApiOperation("获取用户消息通知")
    @GetMapping(value = "/getListByUserId")
    public AjaxResult getListByUserId(Long userId) {
        MsgNotice msgNotice = new MsgNotice();
        msgNotice.setUserId(userId);
        List<MsgNotice> msgNotices = msgNoticeService.selectMsgNoticeList(msgNotice);
        return AjaxResult.success(msgNotices);
    }

    @ApiOperation("用户已读操作")
    @PostMapping(value = "read")
    public AjaxResult read(Long noticeId) {
        MsgNotice msgNotice = msgNoticeService.selectMsgNoticeById(noticeId);
        if (msgNotice == null) {
            return AjaxResult.error("未查找到当前消息");
        }
        msgNotice.setHasRead("Y");
        int result = msgNoticeService.updateMsgNotice(msgNotice);
        return toAjax(result);
    }

    @ApiOperation("提醒回收")
    @PostMapping(value = "remind")
    public AjaxResult remind(Long userId, Long restaurantId) {
        SysConfig config = new SysConfig();
        config.setConfigKey("catering.msgNotice.template");
        List<SysConfig> sysConfigs = configService.selectConfigList(config);
        if (sysConfigs == null || sysConfigs.size() == 0) {
            return AjaxResult.error("请联系管理员添加消息模板");
        }
        config = sysConfigs.get(0);
        MsgNotice notice = new MsgNotice();
        notice.setRestaurantId(restaurantId);
        List<MsgNotice> msgNotices = msgNoticeService.selectMsgNoticeList(notice);
        if (msgNotices.size() > 0) {
            try {
                if (new Date().getTime() - msgNotices.get(0).getCreateTime().getTime() < Integer.parseInt(config.getRemark()) * 1000) {
                    return AjaxResult.error("请勿频繁发送");
                }
            } catch (Exception e) {

            }
        }
        MsgNotice msgNotice = new MsgNotice();
        msgNotice.setType(3);
        msgNotice.setUserId(userId);
        msgNotice.setRestaurantId(restaurantId);
        String content = config.getConfigValue();
        SysUser user = userService.selectUserById(userId);
        Restaurant restaurant = restaurantService.selectRestaurantById(restaurantId);
        Map map = new HashMap();
        map.put("user", user.getUserName());
        map.put("store", restaurant.getName());
        String c = BusinessUtil.processTemplate(content, map);
        msgNotice.setContent(c);
        msgNotice.setHasRead("N");
        int result = msgNoticeService.insertMsgNotice(msgNotice);
        if (result > 0) {
            return AjaxResult.success("提醒成功");
        }
        return AjaxResult.error("提醒失败");
    }
}