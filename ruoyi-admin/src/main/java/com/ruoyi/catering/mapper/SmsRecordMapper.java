package com.ruoyi.catering.mapper;

import java.util.List;
import com.ruoyi.catering.domain.SmsRecord;

/**
 * 短信发送记录Mapper接口
 * 
 * @author lsy
 * @date 2020-07-08
 */
public interface SmsRecordMapper 
{
    /**
     * 查询短信发送记录
     * 
     * @param recordId 短信发送记录ID
     * @return 短信发送记录
     */
    public SmsRecord selectSmsRecordById(Long recordId);

    /**
     * 查询短信发送记录列表
     * 
     * @param smsRecord 短信发送记录
     * @return 短信发送记录集合
     */
    public List<SmsRecord> selectSmsRecordList(SmsRecord smsRecord);

    /**
     * 新增短信发送记录
     * 
     * @param smsRecord 短信发送记录
     * @return 结果
     */
    public int insertSmsRecord(SmsRecord smsRecord);

    /**
     * 修改短信发送记录
     * 
     * @param smsRecord 短信发送记录
     * @return 结果
     */
    public int updateSmsRecord(SmsRecord smsRecord);

    /**
     * 删除短信发送记录
     * 
     * @param recordId 短信发送记录ID
     * @return 结果
     */
    public int deleteSmsRecordById(Long recordId);

    /**
     * 批量删除短信发送记录
     * 
     * @param recordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSmsRecordByIds(String[] recordIds);
}
