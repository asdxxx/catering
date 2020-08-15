package com.ruoyi.api;

import com.ruoyi.catering.domain.MsgNotice;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.service.IMsgNoticeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}