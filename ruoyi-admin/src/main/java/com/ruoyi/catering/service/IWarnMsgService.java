package com.ruoyi.catering.service;

import java.util.List;
import com.ruoyi.catering.domain.WarnMsg;

/**
 * 告警信息Service接口
 * 
 * @author lsy
 * @date 2020-07-12
 */
public interface IWarnMsgService 
{
    /**
     * 查询告警信息
     * 
     * @param id 告警信息ID
     * @return 告警信息
     */
    public WarnMsg selectWarnMsgById(Long id);

    /**
     * 查询告警信息列表
     * 
     * @param warnMsg 告警信息
     * @return 告警信息集合
     */
    public List<WarnMsg> selectWarnMsgList(WarnMsg warnMsg);

    /**
     * 新增告警信息
     * 
     * @param warnMsg 告警信息
     * @return 结果
     */
    public int insertWarnMsg(WarnMsg warnMsg);

    /**
     * 修改告警信息
     * 
     * @param warnMsg 告警信息
     * @return 结果
     */
    public int updateWarnMsg(WarnMsg warnMsg);

    /**
     * 批量删除告警信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWarnMsgByIds(String ids);

    /**
     * 删除告警信息信息
     * 
     * @param id 告警信息ID
     * @return 结果
     */
    public int deleteWarnMsgById(Long id);
}
