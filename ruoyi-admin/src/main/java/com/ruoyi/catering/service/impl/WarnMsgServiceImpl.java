package com.ruoyi.catering.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.WarnMsgMapper;
import com.ruoyi.catering.domain.WarnMsg;
import com.ruoyi.catering.service.IWarnMsgService;
import com.ruoyi.common.core.text.Convert;

/**
 * 告警信息Service业务层处理
 * 
 * @author lsy
 * @date 2020-07-12
 */
@Service
public class WarnMsgServiceImpl implements IWarnMsgService 
{
    @Autowired
    private WarnMsgMapper warnMsgMapper;

    /**
     * 查询告警信息
     * 
     * @param id 告警信息ID
     * @return 告警信息
     */
    @Override
    public WarnMsg selectWarnMsgById(Long id)
    {
        return warnMsgMapper.selectWarnMsgById(id);
    }

    /**
     * 查询告警信息列表
     * 
     * @param warnMsg 告警信息
     * @return 告警信息
     */
    @Override
    public List<WarnMsg> selectWarnMsgList(WarnMsg warnMsg)
    {
        return warnMsgMapper.selectWarnMsgList(warnMsg);
    }

    /**
     * 新增告警信息
     * 
     * @param warnMsg 告警信息
     * @return 结果
     */
    @Override
    public int insertWarnMsg(WarnMsg warnMsg)
    {
        warnMsg.setCreateTime(DateUtils.getNowDate());
        return warnMsgMapper.insertWarnMsg(warnMsg);
    }

    /**
     * 修改告警信息
     * 
     * @param warnMsg 告警信息
     * @return 结果
     */
    @Override
    public int updateWarnMsg(WarnMsg warnMsg)
    {
        warnMsg.setUpdateTime(DateUtils.getNowDate());
        return warnMsgMapper.updateWarnMsg(warnMsg);
    }

    /**
     * 删除告警信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWarnMsgByIds(String ids)
    {
        return warnMsgMapper.deleteWarnMsgByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除告警信息信息
     * 
     * @param id 告警信息ID
     * @return 结果
     */
    @Override
    public int deleteWarnMsgById(Long id)
    {
        return warnMsgMapper.deleteWarnMsgById(id);
    }
}
