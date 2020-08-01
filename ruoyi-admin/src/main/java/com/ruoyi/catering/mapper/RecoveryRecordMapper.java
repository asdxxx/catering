package com.ruoyi.catering.mapper;

import java.util.List;

import com.ruoyi.catering.domain.RecoveryRecord;

/**
 * 回收记录Mapper接口
 *
 * @author lsy
 * @date 2020-07-08
 */
public interface RecoveryRecordMapper {
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
     * 删除回收记录
     *
     * @param id 回收记录ID
     * @return 结果
     */
    public int deleteRecoveryRecordById(Long id);

    /**
     * 批量删除回收记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRecoveryRecordByIds(String[] ids);

    public RecoveryRecord selectLastRecoveryRecord(Long restaurantId);
}
