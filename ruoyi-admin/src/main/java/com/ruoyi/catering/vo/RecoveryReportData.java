package com.ruoyi.catering.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.common.utils.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-08-26 10:37
 */
@Data
public class RecoveryReportData {
    private Integer reportCount;

    private Long restaurantId;

    private String location;

    private String longitude;

    private String latitude;

    private Long userId;

    private String carNo;

    private String garbageId;

    private String weight;

    private String img;

    private String recycleImg;

    public RecoveryRecord toRecoveryRecord() {
        RecoveryRecord recoveryRecord = new RecoveryRecord();
        recoveryRecord.setRestaurantId(restaurantId);
        recoveryRecord.setLocation(location);
        recoveryRecord.setLongitude(longitude);
        recoveryRecord.setLatitude(latitude);
        recoveryRecord.setUserId(userId);
        recoveryRecord.setCarNo(carNo);
        recoveryRecord.setImg(img);
        recoveryRecord.setRecycleImg(recycleImg);
        recoveryRecord.setGarbageId(Long.parseLong(garbageId));
        if(StringUtils.isNotEmpty(weight)){
            recoveryRecord.setWeight(Double.parseDouble(weight));
        }
        return recoveryRecord;
    }

    public List<RecoveryRecord> toRecoveryRecordList() {
        List<RecoveryRecord> recoveryRecordList = new ArrayList<>();
        String[] garbageIdArray = garbageId.split(",");
        String[] weightArray = weight.split(",");
        for (int i = 0; i < reportCount; i++) {
            RecoveryRecord recoveryRecord = new RecoveryRecord();
            recoveryRecord.setRestaurantId(restaurantId);
            recoveryRecord.setLocation(location);
            recoveryRecord.setLongitude(longitude);
            recoveryRecord.setLatitude(latitude);
            recoveryRecord.setUserId(userId);
            recoveryRecord.setCarNo(carNo);
            recoveryRecord.setImg(img);
            recoveryRecord.setRecycleImg(recycleImg);
            recoveryRecord.setGarbageId(Long.parseLong(garbageIdArray[i]));
            if(StringUtils.isNotEmpty(weightArray[i])){
                recoveryRecord.setWeight(Double.parseDouble(weightArray[i]));
            }
            recoveryRecordList.add(recoveryRecord);
        }
        return recoveryRecordList;
    }
}