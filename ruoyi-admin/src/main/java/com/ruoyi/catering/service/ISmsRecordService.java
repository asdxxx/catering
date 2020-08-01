package com.ruoyi.catering.service;

import java.util.List;
import com.ruoyi.catering.domain.SmsRecord;

/**
 * 短信发送记录Service接口
 * 
 * @author lsy
 * @date 2020-07-08
 */
public interface ISmsRecordService 
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
     * 批量删除短信发送记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSmsRecordByIds(String ids);

    /**
     * 删除短信发送记录信息
     * 
     * @param recordId 短信发送记录ID
     * @return 结果
     */
    public int deleteSmsRecordById(Long recordId);
}
