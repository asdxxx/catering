package com.ruoyi.catering.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.catering.domain.CheckRecord;
import com.ruoyi.catering.domain.RecoveryRecord;

/**
 * 检查记录Service接口
 * 
 * @author lsy
 * @date 2020-07-23
 */
public interface ICheckRecordService 
{
    /**
     * 查询检查记录
     * 
     * @param recordId 检查记录ID
     * @return 检查记录
     */
    public CheckRecord selectCheckRecordById(Long recordId);

    /**
     * 查询检查记录列表
     * 
     * @param checkRecord 检查记录
     * @return 检查记录集合
     */
    public List<CheckRecord> selectCheckRecordList(CheckRecord checkRecord);

    /**
     * 新增检查记录
     * 
     * @param checkRecord 检查记录
     * @return 结果
     */
    public int insertCheckRecord(CheckRecord checkRecord);

    /**
     * 修改检查记录
     * 
     * @param checkRecord 检查记录
     * @return 结果
     */
    public int updateCheckRecord(CheckRecord checkRecord);

    /**
     * 批量删除检查记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCheckRecordByIds(String ids);

    /**
     * 删除检查记录信息
     * 
     * @param recordId 检查记录ID
     * @return 结果
     */
    public int deleteCheckRecordById(Long recordId);

    public List<CheckRecord> selectListByRestaurantId(String ids);

    public List<Map> getDailyData(CheckRecord checkRecord);

}
