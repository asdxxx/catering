package com.ruoyi.api;

import com.ruoyi.catering.domain.MsgNotice;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.IMsgNoticeService;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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
@Api(value = "接口对接", tags = {"接口对接"})
public class MsgNoticeApiController extends BaseController {
    @Autowired
    private IMsgNoticeService msgNoticeService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IRestaurantService restaurantService;

    @GetMapping(value = "/badgeNumber")
    public AjaxResult badgeNumber(Long userId) {
        MsgNotice msgNotice = new MsgNotice();
        msgNotice.setUserId(userId);
        msgNotice.setHasRead("N");
        List<MsgNotice> msgNotices = msgNoticeService.selectMsgNoticeList(msgNotice);
        return AjaxResult.success("获取成功", msgNotices.size());
    }

    @GetMapping(value = "/getListByUserId")
    public AjaxResult getListByUserId(Long userId) {
        MsgNotice msgNotice = new MsgNotice();
        msgNotice.setUserId(userId);
        List<MsgNotice> msgNotices = msgNoticeService.selectMsgNoticeList(msgNotice);
        return AjaxResult.success(msgNotices);
    }

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

    @PostMapping(value = "remind")
    public AjaxResult remind(Long userId, Long restaurantId) {
        String content = configService.selectConfigByKey("catering.msgNotice.template");
        SysConfig config = new SysConfig();
        config.setConfigKey("catering.msgNotice.template");
        List<SysConfig> sysConfigs = configService.selectConfigList(config);
        config = sysConfigs.get(0);
        MsgNotice notice = new MsgNotice();
        notice.setRestaurantId(restaurantId);
        List<MsgNotice> msgNotices = msgNoticeService.selectMsgNoticeList(notice);
        if (msgNotices.size() > 0) {
            try {
                if (new Date().getTime() - msgNotices.get(0).getCreateTime().getTime() < Integer.parseInt(config.getRemark())) {
                    return AjaxResult.error("请勿频繁发送");
                }
            } catch (Exception e) {

            }
        }
        MsgNotice msgNotice = new MsgNotice();
        msgNotice.setType(3);
        msgNotice.setUserId(userId);
        msgNotice.setRestaurantId(restaurantId);
        if (content.contains("{回收人员}")) {
            SysUser user = userService.selectUserById(userId);
            content.replaceAll("{回收人员}", user.getUserName());
        }
        if (content.contains("{商户}")) {
            Restaurant restaurant = restaurantService.selectRestaurantById(restaurantId);
            content.replaceAll("{商户}", restaurant.getName());
        }
        msgNotice.setContent(content);
        msgNotice.setHasRead("N");

        return toAjax(msgNoticeService.insertMsgNotice(msgNotice));
    }
}