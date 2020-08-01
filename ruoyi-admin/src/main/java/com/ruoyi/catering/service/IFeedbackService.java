package com.ruoyi.catering.service;

import java.util.List;
import com.ruoyi.catering.domain.Feedback;

/**
 * 意见反馈Service接口
 * 
 * @author lsy
 * @date 2020-07-08
 */
public interface IFeedbackService 
{
    /**
     * 查询意见反馈
     * 
     * @param feedbackId 意见反馈ID
     * @return 意见反馈
     */
    public Feedback selectFeedbackById(Long feedbackId);

    /**
     * 查询意见反馈列表
     * 
     * @param feedback 意见反馈
     * @return 意见反馈集合
     */
    public List<Feedback> selectFeedbackList(Feedback feedback);

    /**
     * 新增意见反馈
     * 
     * @param feedback 意见反馈
     * @return 结果
     */
    public int insertFeedback(Feedback feedback);

    /**
     * 修改意见反馈
     * 
     * @param feedback 意见反馈
     * @return 结果
     */
    public int updateFeedback(Feedback feedback);

    /**
     * 批量删除意见反馈
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFeedbackByIds(String ids);

    /**
     * 删除意见反馈信息
     * 
     * @param feedbackId 意见反馈ID
     * @return 结果
     */
    public int deleteFeedbackById(Long feedbackId);
}
