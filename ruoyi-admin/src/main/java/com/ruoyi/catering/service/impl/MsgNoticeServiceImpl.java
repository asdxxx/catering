package com.ruoyi.catering.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.MsgNoticeMapper;
import com.ruoyi.catering.domain.MsgNotice;
import com.ruoyi.catering.service.IMsgNoticeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 消息通知Service业务层处理
 * 
 * @author lsy
 * @date 2020-08-11
 */
@Service
public class MsgNoticeServiceImpl implements IMsgNoticeService 
{
    @Autowired
    private MsgNoticeMapper msgNoticeMapper;

    /**
     * 查询消息通知
     * 
     * @param noticeId 消息通知ID
     * @return 消息通知
     */
    @Override
    public MsgNotice selectMsgNoticeById(Long noticeId)
    {
        return msgNoticeMapper.selectMsgNoticeById(noticeId);
    }

    /**
     * 查询消息通知列表
     * 
     * @param msgNotice 消息通知
     * @return 消息通知
     */
    @Override
    public List<MsgNotice> selectMsgNoticeList(MsgNotice msgNotice)
    {
        return msgNoticeMapper.selectMsgNoticeList(msgNotice);
    }

    /**
     * 新增消息通知
     * 
     * @param msgNotice 消息通知
     * @return 结果
     */
    @Override
    public int insertMsgNotice(MsgNotice msgNotice)
    {
        msgNotice.setCreateTime(DateUtils.getNowDate());
        return msgNoticeMapper.insertMsgNotice(msgNotice);
    }

    /**
     * 修改消息通知
     * 
     * @param msgNotice 消息通知
     * @return 结果
     */
    @Override
    public int updateMsgNotice(MsgNotice msgNotice)
    {
        msgNotice.setUpdateTime(DateUtils.getNowDate());
        return msgNoticeMapper.updateMsgNotice(msgNotice);
    }

    /**
     * 删除消息通知对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMsgNoticeByIds(String ids)
    {
        return msgNoticeMapper.deleteMsgNoticeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除消息通知信息
     * 
     * @param noticeId 消息通知ID
     * @return 结果
     */
    @Override
    public int deleteMsgNoticeById(Long noticeId)
    {
        return msgNoticeMapper.deleteMsgNoticeById(noticeId);
    }
}
