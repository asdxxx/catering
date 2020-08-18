package com.ruoyi.catering.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.RecoveryRecordMapper;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.service.IRecoveryRecordService;
import com.ruoyi.common.core.text.Convert;

/**
 * 回收记录Service业务层处理
 *
 * @author lsy
 * @date 2020-07-08
 */
@Service
public class RecoveryRecordServiceImpl implements IRecoveryRecordService {
    @Autowired
    private RecoveryRecordMapper recoveryRecordMapper;

    /**
     * 查询回收记录
     *
     * @param id 回收记录ID
     * @return 回收记录
     */
    @Override
    public RecoveryRecord selectRecoveryRecordById(Long id) {
        return recoveryRecordMapper.selectRecoveryRecordById(id);
    }

    /**
     * 查询回收记录列表
     *
     * @param recoveryRecord 回收记录
     * @return 回收记录
     */
    @Override
    public List<RecoveryRecord> selectRecoveryRecordList(RecoveryRecord recoveryRecord) {
        return recoveryRecordMapper.selectRecoveryRecordList(recoveryRecord);
    }

    /**
     * 新增回收记录
     *
     * @param recoveryRecord 回收记录
     * @return 结果
     */
    @Override
    public int insertRecoveryRecord(RecoveryRecord recoveryRecord) {
        recoveryRecord.setCreateTime(DateUtils.getNowDate());
        return recoveryRecordMapper.insertRecoveryRecord(recoveryRecord);
    }

    /**
     * 修改回收记录
     *
     * @param recoveryRecord 回收记录
     * @return 结果
     */
    @Override
    public int updateRecoveryRecord(RecoveryRecord recoveryRecord) {
        recoveryRecord.setUpdateTime(DateUtils.getNowDate());
        return recoveryRecordMapper.updateRecoveryRecord(recoveryRecord);
    }

    /**
     * 删除回收记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRecoveryRecordByIds(String ids) {
        return recoveryRecordMapper.deleteRecoveryRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除回收记录信息
     *
     * @param id 回收记录ID
     * @return 结果
     */
    @Override
    public int deleteRecoveryRecordById(Long id) {
        return recoveryRecordMapper.deleteRecoveryRecordById(id);
    }

    public RecoveryRecord selectLastRecoveryRecord(Long restaurantId) {
        return recoveryRecordMapper.selectLastRecoveryRecord(restaurantId);
    }

    @Override
    public List<RecoveryRecord> selectListByRestaurantId(String ids) {
        return recoveryRecordMapper.selectListByRestaurantId(Convert.toStrArray(ids));
    }

    @Override
    public double sumWeight(RecoveryRecord recoveryRecord) {
        return recoveryRecordMapper.sumWeight(recoveryRecord);
    }

    @Override
    public List<Map> getDailyData(RecoveryRecord recoveryRecord) {
        return recoveryRecordMapper.getDailyData(recoveryRecord);
    }

}
