package com.ruoyi.catering.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.catering.domain.RecoveryRecord;

/**
 * 回收记录Service接口
 *
 * @author lsy
 * @date 2020-07-08
 */
public interface IRecoveryRecordService {
    /**
     * 查询回收记录
     *
     * @param id 回收记录ID
     * @return 回收记录
     */
    public RecoveryRecord selectRecoveryRecordById(Long id);

    /**
     * 查询回收记录列表
     *
     * @param recoveryRecord 回收记录
     * @return 回收记录集合
     */
    public List<RecoveryRecord> selectRecoveryRecordList(RecoveryRecord recoveryRecord);

    /**
     * 新增回收记录
     *
     * @param recoveryRecord 回收记录
     * @return 结果
     */
    public int insertRecoveryRecord(RecoveryRecord recoveryRecord);

    /**
     * 修改回收记录
     *
     * @param recoveryRecord 回收记录
     * @return 结果
     */
    public int updateRecoveryRecord(RecoveryRecord recoveryRecord);

    /**
     * 批量删除回收记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRecoveryRecordByIds(String ids);

    /**
     * 删除回收记录信息
     *
     * @param id 回收记录ID
     * @return 结果
     */
    public int deleteRecoveryRecordById(Long id);

    public RecoveryRecord selectLastRecoveryRecord(Long restaurantId);

    public List<RecoveryRecord> selectListByRestaurantId(String ids);

    //    public double sumWeight(RecoveryRecord recoveryRecord);
    public double sumWeight(RecoveryRecord recoveryRecord, String restaurantIds);


    //    public List<Map> getDailyData(RecoveryRecord recoveryRecord);
    public List<Map> getDailyData(RecoveryRecord recoveryRecord, String restaurantIds);

    public List<RecoveryRecord> selectNearlyList(String restaurantIds, int count);
}
