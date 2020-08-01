package com.ruoyi.catering.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.CheckRecordMapper;
import com.ruoyi.catering.domain.CheckRecord;
import com.ruoyi.catering.service.ICheckRecordService;
import com.ruoyi.common.core.text.Convert;

/**
 * 检查记录Service业务层处理
 * 
 * @author lsy
 * @date 2020-07-23
 */
@Service
public class CheckRecordServiceImpl implements ICheckRecordService 
{
    @Autowired
    private CheckRecordMapper checkRecordMapper;

    /**
     * 查询检查记录
     * 
     * @param recordId 检查记录ID
     * @return 检查记录
     */
    @Override
    public CheckRecord selectCheckRecordById(Long recordId)
    {
        return checkRecordMapper.selectCheckRecordById(recordId);
    }

    /**
     * 查询检查记录列表
     * 
     * @param checkRecord 检查记录
     * @return 检查记录
     */
    @Override
    public List<CheckRecord> selectCheckRecordList(CheckRecord checkRecord)
    {
        return checkRecordMapper.selectCheckRecordList(checkRecord);
    }

    /**
     * 新增检查记录
     * 
     * @param checkRecord 检查记录
     * @return 结果
     */
    @Override
    public int insertCheckRecord(CheckRecord checkRecord)
    {
        checkRecord.setCreateTime(DateUtils.getNowDate());
        return checkRecordMapper.insertCheckRecord(checkRecord);
    }

    /**
     * 修改检查记录
     * 
     * @param checkRecord 检查记录
     * @return 结果
     */
    @Override
    public int updateCheckRecord(CheckRecord checkRecord)
    {
        checkRecord.setUpdateTime(DateUtils.getNowDate());
        return checkRecordMapper.updateCheckRecord(checkRecord);
    }

    /**
     * 删除检查记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCheckRecordByIds(String ids)
    {
        return checkRecordMapper.deleteCheckRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除检查记录信息
     * 
     * @param recordId 检查记录ID
     * @return 结果
     */
    @Override
    public int deleteCheckRecordById(Long recordId)
    {
        return checkRecordMapper.deleteCheckRecordById(recordId);
    }
}
