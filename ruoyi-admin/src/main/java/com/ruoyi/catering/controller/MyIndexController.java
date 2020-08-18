package com.ruoyi.catering.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.catering.domain.CheckRecord;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.service.ICheckRecordService;
import com.ruoyi.catering.service.IRecoveryRecordService;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.service.ISysDeptService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @program: catering
 * @description: 首页数据获取
 * @author: liu sheng yin
 * @create: 2020-08-17 08:47
 */
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

    @DataScope(deptAlias = "d")
    @GetMapping(value = "indexData")
    @ResponseBody
    public JSONObject indexData(Long deptId, String startDate, String endDate) {
        Restaurant restaurant = new Restaurant();
        restaurant.setDeptId(deptId);
        List<Restaurant> restaurantList = restaurantService.selectRestaurantList(restaurant);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "获取成功");
        jsonObject.put("restaurantCount", restaurantList.size());

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
        List<RecoveryRecord> recoveryRecords = recoveryRecordService.selectListByRestaurantId(ids);
        int recoveredCount = 0;
        for (RecoveryRecord rr : recoveryRecords) {
            Restaurant r = restaurantService.selectRestaurantById(rr.getRestaurantId());
            int size = r.getSize();
            Date date = size == 2 ? mediumDate : size == 3 ? largeDate : smallDate;
            if (rr.getRecoveryDate().after(date)) {
                recoveredCount++;
            }
        }
        jsonObject.put("recoveredCount", recoveredCount);

        List<CheckRecord> checkRecords = checkRecordService.selectListByRestaurantId(ids);
        int checkedCount = 0;
        for (CheckRecord cr : checkRecords) {
            Restaurant r = restaurantService.selectRestaurantById(cr.getRestaurantId());
            int size = r.getSize();
            Date date = size == 2 ? mediumDate : size == 3 ? largeDate : smallDate;
            if (cr.getCheckDate().after(date)) {
                checkedCount++;
            }
        }
        jsonObject.put("checkedCount", checkedCount);

        RecoveryRecord recoveryRecord = new RecoveryRecord();
        recoveryRecord.setGarbageId(1L);
        double gutterOilWeight = recoveryRecordService.sumWeight(recoveryRecord);
        recoveryRecord.setGarbageId(2L);
        double oldOilWeight = recoveryRecordService.sumWeight(recoveryRecord);
        jsonObject.put("gutterOilWeight", gutterOilWeight);
        jsonObject.put("oldOilWeight", oldOilWeight);

        cal.add(Calendar.DATE, 3);
        Map<String, Object> params = new HashMap<>();
        params.put("beginRecoveryDate", cal.getTime());

        RecoveryRecord dailyRecoveryRecord = new RecoveryRecord();
        dailyRecoveryRecord.setParams(params);
        List<Map> mapList = recoveryRecordService.getDailyData(recoveryRecord);
        String[] dateArray = new String[7];
        int[] recoveryArray = new int[7];
        for (int i = 0; i < 7; i++) {
            dateArray[i] = DateUtils.parseDateToStr("MM-dd", cal.getTime());
            cal.add(Calendar.DATE, 1);
        }
        jsonObject.put("dateArray", dateArray);
        return jsonObject;
    }

}