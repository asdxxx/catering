package com.ruoyi.catering.utils;

import com.ruoyi.catering.domain.CheckRecord;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.domain.WarnMsg;
import com.ruoyi.catering.service.ICheckRecordService;
import com.ruoyi.catering.service.IRecoveryRecordService;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.service.IWarnMsgService;
import com.ruoyi.common.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-12 21:15
 */
@Component
public class BusinessUtil {
    @Autowired
    private IRecoveryRecordService recoveryRecordService;
    @Autowired
    private ICheckRecordService checkRecordService;
    @Autowired
    private IWarnMsgService warnMsgService;

    private static BusinessUtil businessUtil;

    @PostConstruct
    public void init() {
        businessUtil = this;
    }

    public static boolean isRecovered(Restaurant restaurant) {
        RecoveryRecord recoveryRecord = new RecoveryRecord();
        recoveryRecord.setRestaurantId(restaurant.getRestaurantId());
        Calendar cal = Calendar.getInstance();
        int size = restaurant.getSize();
        switch (size) {
            case 1:
                cal.add(Calendar.DATE, -3);
                break;
            case 2:
                cal.add(Calendar.DATE, -6);
                break;
            case 3:
                cal.add(Calendar.DATE, -9);
                break;
            default:
                cal.add(Calendar.DATE, -3);
        }
        Date date = cal.getTime();
        Map<String, Object> params = new HashMap<>();
        params.put("beginRecoveryDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, date) + " 00:00:00");
//        params.put("beginRecoveryDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, date));
//        params.put("endRecoveryDate", DateUtils.getTime());
        recoveryRecord.setParams(params);
        List<RecoveryRecord> recoveryRecords = businessUtil.recoveryRecordService.selectRecoveryRecordList(recoveryRecord);
        if (recoveryRecords.size() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isChecked(Restaurant restaurant) {
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setRestaurantId(restaurant.getRestaurantId());
        Calendar cal = Calendar.getInstance();
        int size = restaurant.getSize();
        switch (size) {
            case 1:
                cal.add(Calendar.DATE, -3);
                break;
            case 2:
                cal.add(Calendar.DATE, -6);
                break;
            case 3:
                cal.add(Calendar.DATE, -9);
                break;
            default:
                cal.add(Calendar.DATE, -3);
        }
        Date date = cal.getTime();
        Map<String, Object> params = new HashMap<>();
        params.put("beginCheckDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, date) + " 00:00:00");
        checkRecord.setParams(params);
        List<CheckRecord> checkRecords = businessUtil.checkRecordService.selectCheckRecordList(checkRecord);
        if (checkRecords.size() > 0) {
            return true;
        }
        return false;
    }

//    //检测是否厨余和费油7天之后未收
//    public static String isGetSumWeight(Restaurant restaurant) {
//        if (restaurant.getHaskwoRecoveryAgreement() == null || restaurant.getHaskwoRecoveryAgreement() != 0) {
//            return "blue";
//        }
//        RecoveryRecord recoveryRecord = new RecoveryRecord();
//        recoveryRecord.setRestaurantId(restaurant.getRestaurantId());
//        Calendar cal = Calendar.getInstance();
//        if (restaurant.getDiningTypeId() == 1) {
//            cal.add(Calendar.DATE, -7);
//        } else {
//            cal.add(Calendar.DATE, -15);
//        }
//        Date date = cal.getTime();
//        Map<String, Object> params = new HashMap<>();
//        params.put("beginRecoveryDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, date));
//        params.put("endRecoveryDate", DateUtils.getTime());
//        recoveryRecord.setParams(params);
//        List<RecoveryRecord> recoveryRecords = businessUtil.recoveryRecordService.selectRecoveryRecordList(recoveryRecord);
//        if (recoveryRecords.size() > 0) {
//            return "blue";
//        }
//        return "red";
//    }
//
//    //-------------------五色工具类------------------
//    //1红色为不合格，2橙色为未检查，3黄色为整改中，4蓝色为合格，5灰色为暂停营业
//    //信息是否齐全
//    public static String isinfo(Restaurant restaurant) {
//        if (restaurant.getHasDischargePermit() == null) {
//            return "red";
//        }
//        if (restaurant.getHasFumeCleaner() == null) {
//            return "red";
//        }
//        if (restaurant.getHaskwoRecoveryAgreement() == null) {
//            return "red";
//        }
//        if (restaurant.getHasOilWaterSeparator() == null) {
//            return "red";
//        }
//        return "blue";
//    }
//
//    //是否被投诉
//    public static String getWarning(Long restaurantId) {
//        WarnMsg warnMsg = new WarnMsg();
//        warnMsg.setType(3);
//        warnMsg.setRestaurantId(restaurantId);
//        warnMsg.setMsgType(3);
//        warnMsg.setStatus(0);
//        List<WarnMsg> warnMsgList = businessUtil.warnMsgService.selectWarnMsgList(warnMsg);
//        if (warnMsgList != null && warnMsgList.size() > 0) {
//            return "red";
//        }
//        return "blue";
//    }
}