package com.ruoyi.api;

import com.ruoyi.catering.domain.Feedback;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.service.IFeedbackService;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-23 11:10
 */
@Slf4j
@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "接口对接", tags = {"接口对接"})
public class FeedbackApiController {
    @Autowired
    private IFeedbackService feedbackService;

    @PostMapping(value = "save")
    public AjaxResult save(Feedback feedback) {
        int result = feedbackService.insertFeedback(feedback);
        if (result <= 0) {
            return AjaxResult.error("上报失败");
        }
        return AjaxResult.success();
    }
}