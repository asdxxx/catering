package com.ruoyi.catering.utils;

import com.ruoyi.catering.domain.CheckRecord;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.ICheckRecordService;
import com.ruoyi.catering.service.IRecoveryRecordService;
import com.ruoyi.catering.vo.CheckRecordVo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static BusinessUtil businessUtil;

    @PostConstruct
    public void init() {
        businessUtil = this;
    }

    public static boolean updateCoordinates(Restaurant restaurant, String longitude, String latitude) {
        if (StringUtils.isNotEmpty(restaurant.getLongitude()) && StringUtils.isNotEmpty(restaurant.getLatitude())) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}");
        boolean isNum = pattern.matcher(longitude).matches();
        if (!isNum) {
            return false;
        }
        boolean isNum2 = pattern.matcher(latitude).matches();
        if (!isNum2) {
            return false;
        }
        return true;
    }

    //是否已回收
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
        recoveryRecord.setParams(params);
        List<RecoveryRecord> recoveryRecords = businessUtil.recoveryRecordService.selectRecoveryRecordList(recoveryRecord);
        if (recoveryRecords.size() > 0) {
            return true;
        }
        return false;
    }

    //是否已检查
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

    //是否合格
    public static boolean isQualified(Long restaurantId) {
        boolean flag = true;
        CheckRecord checkRecord = businessUtil.checkRecordService.selectLastRecordByRestaurantId(restaurantId);
        if (checkRecord != null && checkRecord.getStatus() != null && checkRecord.getStatus() == 1) {
            flag = false;
        }
        return flag;
    }

    /**
     * 自定义渲染模板
     *
     * @param template 模版
     * @param params   参数
     * @return
     */
    public static String processTemplate(String template, Map<String, Object> params) {
        if (template == null || params == null)
            return null;
        StringBuffer sb = new StringBuffer();
        Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(template);
        while (m.find()) {
            String param = m.group();
            Object value = params.get(param.substring(2, param.length() - 1));
            m.appendReplacement(sb, value == null ? "" : value.toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }
}