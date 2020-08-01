package com.ruoyi.catering.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.SmsRecordMapper;
import com.ruoyi.catering.domain.SmsRecord;
import com.ruoyi.catering.service.ISmsRecordService;
import com.ruoyi.common.core.text.Convert;

/**
 * 短信发送记录Service业务层处理
 * 
 * @author lsy
 * @date 2020-07-08
 */
@Service
public class SmsRecordServiceImpl implements ISmsRecordService 
{
    @Autowired
    private SmsRecordMapper smsRecordMapper;

    /**
     * 查询短信发送记录
     * 
     * @param recordId 短信发送记录ID
     * @return 短信发送记录
     */
    @Override
    public SmsRecord selectSmsRecordById(Long recordId)
    {
        return smsRecordMapper.selectSmsRecordById(recordId);
    }

    /**
     * 查询短信发送记录列表
     * 
     * @param smsRecord 短信发送记录
     * @return 短信发送记录
     */
    @Override
    public List<SmsRecord> selectSmsRecordList(SmsRecord smsRecord)
    {
        return smsRecordMapper.selectSmsRecordList(smsRecord);
    }

    /**
     * 新增短信发送记录
     * 
     * @param smsRecord 短信发送记录
     * @return 结果
     */
    @Override
    public int insertSmsRecord(SmsRecord smsRecord)
    {
        smsRecord.setCreateTime(DateUtils.getNowDate());
        return smsRecordMapper.insertSmsRecord(smsRecord);
    }

    /**
     * 修改短信发送记录
     * 
     * @param smsRecord 短信发送记录
     * @return 结果
     */
    @Override
    public int updateSmsRecord(SmsRecord smsRecord)
    {
        smsRecord.setUpdateTime(DateUtils.getNowDate());
        return smsRecordMapper.updateSmsRecord(smsRecord);
    }

    /**
     * 删除短信发送记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSmsRecordByIds(String ids)
    {
        return smsRecordMapper.deleteSmsRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除短信发送记录信息
     * 
     * @param recordId 短信发送记录ID
     * @return 结果
     */
    @Override
    public int deleteSmsRecordById(Long recordId)
    {
        return smsRecordMapper.deleteSmsRecordById(recordId);
    }
}
