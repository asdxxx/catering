package com.ruoyi.catering.mapper;

import java.util.List;
import com.ruoyi.catering.domain.MsgNotice;

/**
 * 消息通知Mapper接口
 * 
 * @author lsy
 * @date 2020-08-11
 */
public interface MsgNoticeMapper 
{
    /**
     * 查询消息通知
     * 
     * @param noticeId 消息通知ID
     * @return 消息通知
     */
    public MsgNotice selectMsgNoticeById(Long noticeId);

    /**
     * 查询消息通知列表
     * 
     * @param msgNotice 消息通知
     * @return 消息通知集合
     */
    public List<MsgNotice> selectMsgNoticeList(MsgNotice msgNotice);

    /**
     * 新增消息通知
     * 
     * @param msgNotice 消息通知
     * @return 结果
     */
    public int insertMsgNotice(MsgNotice msgNotice);

    /**
     * 修改消息通知
     * 
     * @param msgNotice 消息通知
     * @return 结果
     */
    public int updateMsgNotice(MsgNotice msgNotice);

    /**
     * 删除消息通知
     * 
     * @param noticeId 消息通知ID
     * @return 结果
     */
    public int deleteMsgNoticeById(Long noticeId);

    /**
     * 批量删除消息通知
     * 
     * @param noticeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteMsgNoticeByIds(String[] noticeIds);
}
