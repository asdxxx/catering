package com.ruoyi.api;

import com.ruoyi.catering.domain.Garbage;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.IGarbageService;
import com.ruoyi.catering.service.IRecoveryRecordService;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.service.IWarnMsgService;
import com.ruoyi.catering.vo.RecoveryRecordVo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-13 08:50
 */
@Slf4j
@RestController
@RequestMapping("/api/recoveryRecord")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "接口对接", tags = {"接口对接"})
public class RecoveryRecordApiController {
    @Autowired
    private IRecoveryRecordService recoveryRecordService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IGarbageService garbageService;

    @PostMapping(value = "save")
    public AjaxResult save(RecoveryRecord recoveryRecord) {
        recoveryRecord.setRecoveryDate(new Date());
        int result = recoveryRecordService.insertRecoveryRecord(recoveryRecord);
        if (result <= 0) {
            return AjaxResult.error("上报失败");
        }
        return AjaxResult.success();
    }

    //获取商户回收详情
    @GetMapping(value = "/getListByRestaurantId")
    public AjaxResult getListByRestaurantId(Long restaurantId) {
        RecoveryRecord recoveryRecord = new RecoveryRecord();
        recoveryRecord.setRestaurantId(restaurantId);
        List<RecoveryRecord> recoveryRecordList = recoveryRecordService.selectRecoveryRecordList(recoveryRecord);
        List<RecoveryRecordVo> recoveryRecordVos = new ArrayList<>();
        for (RecoveryRecord rr : recoveryRecordList) {
            RecoveryRecordVo recoveryRecordVo = new RecoveryRecordVo();
            BeanUtils.copyProperties(rr, recoveryRecordVo);
            SysUser user = userService.selectUserById(rr.getUserId());
            recoveryRecordVo.setUser(user);
            Garbage garbage = garbageService.selectGarbageById(rr.getGarbageId());
            recoveryRecordVo.setGarbage(garbage);
            recoveryRecordVos.add(recoveryRecordVo);
        }
        return AjaxResult.success(recoveryRecordVos);
    }
//    @GetMapping(value = "/toBeConfirmedList")
//    //根据餐馆id获取回收列表
//    public AjaxResult toBeConfirmedList(Long restaurantId) {
//        RecoveryRecord recoveryRecord = new RecoveryRecord();
//        recoveryRecord.setRestaurantId(restaurantId);
//        recoveryRecord.setStatus(1);
//        List<RecoveryRecord> list = recoveryRecordService.selectRecoveryRecordList(recoveryRecord);
//        List<RecoveryRecordVo> rrvList = new ArrayList<>();
//        for (RecoveryRecord rr : list) {
//            RecoveryRecordVo recoveryRecordVo = new RecoveryRecordVo();
//            BeanUtils.copyProperties(rr, recoveryRecordVo);
//            SysUser user = userService.selectUserById(rr.getUserId());
//            recoveryRecordVo.setUser(user);
//            rrvList.add(recoveryRecordVo);
//        }
//        return AjaxResult.success(rrvList);
//    }
//
//    @RequestMapping(value = "/update")
//    //回收记录状态确认
//    public AjaxResult update(RecoveryRecord recoveryRecord) {
//        recoveryRecordService.updateRecoveryRecord(recoveryRecord);
//        return AjaxResult.success("状态更新成功");
//    }
}