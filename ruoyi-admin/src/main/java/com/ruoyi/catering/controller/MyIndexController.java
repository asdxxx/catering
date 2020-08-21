package com.ruoyi.catering.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.catering.domain.CheckRecord;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.service.ICheckRecordService;
import com.ruoyi.catering.service.IRecoveryRecordService;
import com.ruoyi.catering.utils.BaseUtil;
import com.ruoyi.catering.vo.IndexQueryData;
import com.ruoyi.catering.vo.RecoveryRecordVo;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @program: catering
 * @description: 首页数据获取
 * @author: liu sheng yin
 * @create: 2020-08-17 08:47
 */
@Slf4j
@Controller
@RequestMapping("/catering/myindex")
public class MyIndexController extends BaseController {
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private IRecoveryRecordService recoveryRecordService;
    @Autowired
    private ICheckRecordService checkRecordService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysUserService userService;

    @DataScope(deptAlias = "d")
    @PostMapping(value = "/indexData")
    @ResponseBody
    public JSONObject indexData(Restaurant restaurant) {
        LocalDateTime beginTime = LocalDateTime.now();
        /* ---------------------------查询当前用户下辖商户---------------------------*/
        List<Restaurant> restaurantList = restaurantService.selectRestaurantList(restaurant);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "获取成功");
        jsonObject.put("restaurantCount", restaurantList.size());

        /* ---------------------------获取3.6.9天前0点日期---------------------------*/
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DATE, -3);
        Date smallDate = cal.getTime();
        cal.add(Calendar.DATE, -3);
        Date mediumDate = cal.getTime();
        cal.add(Calendar.DATE, -3);
        Date largeDate = cal.getTime();

        String ids = "";
        for (Restaurant r : restaurantList) {
            ids += r.getRestaurantId() + ",";
        }
        if (ids.length() > 0) {
            ids = ids.substring(0, ids.length() - 1);
        }
        /* ---------------------------根据商户id集合分组查询最新的回收记录---------------------------*/
        List<RecoveryRecord> recoveryRecords = recoveryRecordService.selectListByRestaurantId(ids);
        int recoveredCount = 0;
        int unRecoveredCount = 0;
        List<Long> hasIds = new ArrayList<>();
        for (RecoveryRecord rr : recoveryRecords) {
            Restaurant r = restaurantService.selectRestaurantById(rr.getRestaurantId());
            int size = r.getSize();
            Date date = size == 2 ? mediumDate : size == 3 ? largeDate : smallDate;
            /* ---------------------------该商户已回收---------------------------*/
            if (rr.getRecoveryDate().after(date)) {
                hasIds.add(rr.getRestaurantId());
                recoveredCount++;
            }
        }
        for (Restaurant rest : restaurantList) {
            if (rest.getStatus() != null && rest.getStatus() == 1) {
                continue;
            }
            /* ---------------------------未回收商户数---------------------------*/
            if (!hasIds.contains(rest.getRestaurantId())) {
                unRecoveredCount++;
            }
        }

        jsonObject.put("recoveredCount", recoveredCount);
        jsonObject.put("unRecoveredCount", unRecoveredCount);

        /* ---------------------------根据商户id集合分组查询最新的检查记录---------------------------*/
        List<CheckRecord> checkRecords = checkRecordService.selectListByRestaurantId(ids);
        int checkedCount = 0;
        int unqualifiedCount = 0;
        for (CheckRecord cr : checkRecords) {
            /* ---------------------------检查不合格---------------------------*/
            if (cr.getStatus() == 1) {
                unqualifiedCount++;
            }
            Restaurant r = restaurantService.selectRestaurantById(cr.getRestaurantId());
            int size = r.getSize();
            Date date = size == 2 ? mediumDate : size == 3 ? largeDate : smallDate;
            /* ---------------------------该商户已检查---------------------------*/
            if (cr.getCheckDate().after(date)) {
                checkedCount++;
            }
        }
        jsonObject.put("checkedCount", checkedCount);
        jsonObject.put("unqualifiedCount", unqualifiedCount);

        SysUser sysUser = ShiroUtils.getSysUser();
        String sqlString = BaseUtil.dataScopeFilter(sysUser);
        /* ---------------------------地沟油回收数量---------------------------*/
        RecoveryRecord recoveryRecord = new RecoveryRecord();
        recoveryRecord.setGarbageId(1L);
        Map<String, Object> params = new HashMap<>();
        params.put("dataScope", sqlString);
        recoveryRecord.setParams(params);
        double gutterOilWeight = recoveryRecordService.sumWeight(recoveryRecord);
        /* ---------------------------老油回收数量---------------------------*/
        recoveryRecord.setGarbageId(2L);
        double oldOilWeight = recoveryRecordService.sumWeight(recoveryRecord);
        jsonObject.put("gutterOilWeight", gutterOilWeight);
        jsonObject.put("oldOilWeight", oldOilWeight);

        /* ---------------------------1周前---------------------------*/
        cal.add(Calendar.DATE, 3);

        Map<String, Object> rparams = new HashMap<>();
        rparams.put("beginRecoveryDate", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", cal.getTime()));
        rparams.put("dataScope", sqlString);
        RecoveryRecord dailyRecoveryRecord = new RecoveryRecord();
        dailyRecoveryRecord.setParams(rparams);
        List<Map> rmapList = recoveryRecordService.getDailyData(dailyRecoveryRecord);

        Map<String, Object> cparams = new HashMap<>();
        cparams.put("beginCheckDate", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", cal.getTime()));
        CheckRecord dailyCheckRecord = new CheckRecord();
        dailyCheckRecord.setParams(cparams);
        List<Map> cmapList = checkRecordService.getDailyData(dailyCheckRecord);

        String[] wdateArray = new String[7];
        int[] wrecoveryArray = new int[7];
        int[] wcheckArray = new int[7];
        for (int i = 0; i < 7; i++) {
            wdateArray[i] = DateUtils.parseDateToStr("MM-dd", cal.getTime());
            for (Map map : rmapList) {
                if (map.get("recoveryDate").toString().equals(DateUtils.parseDateToStr("yyyy-MM-dd", cal.getTime()))) {
                    wrecoveryArray[i] = Integer.parseInt(map.get("count").toString());
                    break;
                }
            }
            for (Map map : cmapList) {
                if (map.get("checkDate").toString().equals(DateUtils.parseDateToStr("yyyy-MM-dd", cal.getTime()))) {
                    wcheckArray[i] = Integer.parseInt(map.get("count").toString());
                    break;
                }
            }
            cal.add(Calendar.DATE, 1);
        }


        jsonObject.put("wdateArray", wdateArray);
        jsonObject.put("wrecoveryArray", wrecoveryArray);
        jsonObject.put("wcheckArray", wcheckArray);

        /* ---------------------------1月前---------------------------*/
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);//得到前一个月
        calendar.add(Calendar.DATE, 1);
        Date monthago = calendar.getTime();
        int days = (int) ((today.getTime() - monthago.getTime()) / (1000 * 3600 * 24)) + 1;

        Map<String, Object> rparams2 = new HashMap<>();
        rparams2.put("beginRecoveryDate", DateUtils.parseDateToStr("yyyy-MM-dd", calendar.getTime()) + " 00:00:00");
        rparams2.put("dataScope", sqlString);
        RecoveryRecord dailyRecoveryRecord2 = new RecoveryRecord();
        dailyRecoveryRecord2.setParams(rparams2);
        List<Map> rmapList2 = recoveryRecordService.getDailyData(dailyRecoveryRecord2);

        Map<String, Object> cparams2 = new HashMap<>();
        cparams2.put("beginCheckDate", DateUtils.parseDateToStr("yyyy-MM-dd", calendar.getTime()) + " 00:00:00");
        CheckRecord dailyCheckRecord2 = new CheckRecord();
        dailyCheckRecord2.setParams(cparams2);
        List<Map> cmapList2 = checkRecordService.getDailyData(dailyCheckRecord2);

        String[] mdateArray = new String[days];
        int[] mrecoveryArray = new int[days];
        int[] mcheckArray = new int[days];
        for (int i = 0; i < days; i++) {
            mdateArray[i] = DateUtils.parseDateToStr("MM-dd", calendar.getTime());
            for (Map map : rmapList2) {
                if (map.get("recoveryDate").toString().equals(DateUtils.parseDateToStr("yyyy-MM-dd", calendar.getTime()))) {
                    mrecoveryArray[i] = Integer.parseInt(map.get("count").toString());
                    break;
                }
            }
            for (Map map : cmapList2) {
                if (map.get("checkDate").toString().equals(DateUtils.parseDateToStr("yyyy-MM-dd", calendar.getTime()))) {
                    mcheckArray[i] = Integer.parseInt(map.get("count").toString());
                    break;
                }
            }
            calendar.add(Calendar.DATE, 1);
        }

        jsonObject.put("mdateArray", mdateArray);
        jsonObject.put("mrecoveryArray", mrecoveryArray);
        jsonObject.put("mcheckArray", mcheckArray);

        /* ---------------------------实时收运记录---------------------------*/
        RecoveryRecord rr = new RecoveryRecord();
        rr.setParams(params);
        List<RecoveryRecord> rrList = recoveryRecordService.selectRecoveryRecordList(rr);
        if (rrList != null && rrList.size() > 20) {
            rrList = rrList.subList(0, 20);
        }
        List<RecoveryRecordVo> recoveryRecordVos = new ArrayList<>();
        for (RecoveryRecord r : rrList) {
            RecoveryRecordVo recoveryRecordVo = new RecoveryRecordVo();
            BeanUtils.copyProperties(r, recoveryRecordVo);
            SysUser user = userService.selectUserById(r.getUserId());
            recoveryRecordVo.setUser(user);
            recoveryRecordVos.add(recoveryRecordVo);
        }
        jsonObject.put("rrList", recoveryRecordVos);
        
        Long opetime = Duration.between(beginTime, LocalDateTime.now()).toMillis();
        log.info("首页数据请求时长:" + opetime);
        return jsonObject;
    }

}