package com.ruoyi.catering.mapper;

import java.util.List;
import com.ruoyi.catering.domain.Notice;

/**
 * 通知公告Mapper接口
 * 
 * @author lsy
 * @date 2020-07-07
 */
public interface NoticeMapper 
{
    /**
     * 查询通知公告
     * 
     * @param noticeId 通知公告ID
     * @return 通知公告
     */
    public Notice selectNoticeById(Long noticeId);

    /**
     * 查询通知公告列表
     * 
     * @param notice 通知公告
     * @return 通知公告集合
     */
    public List<Notice> selectNoticeList(Notice notice);

    /**
     * 新增通知公告
     * 
     * @param notice 通知公告
     * @return 结果
     */
    public int insertNotice(Notice notice);

    /**
     * 修改通知公告
     * 
     * @param notice 通知公告
     * @return 结果
     */
    public int updateNotice(Notice notice);

    /**
     * 删除通知公告
     * 
     * @param noticeId 通知公告ID
     * @return 结果
     */
    public int deleteNoticeById(Long noticeId);

    /**
     * 批量删除通知公告
     * 
     * @param noticeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoticeByIds(String[] noticeIds);
}