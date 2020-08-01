package com.ruoyi.catering.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.FeedbackMapper;
import com.ruoyi.catering.domain.Feedback;
import com.ruoyi.catering.service.IFeedbackService;
import com.ruoyi.common.core.text.Convert;

/**
 * 意见反馈Service业务层处理
 * 
 * @author lsy
 * @date 2020-07-08
 */
@Service
public class FeedbackServiceImpl implements IFeedbackService 
{
    @Autowired
    private FeedbackMapper feedbackMapper;

    /**
     * 查询意见反馈
     * 
     * @param feedbackId 意见反馈ID
     * @return 意见反馈
     */
    @Override
    public Feedback selectFeedbackById(Long feedbackId)
    {
        return feedbackMapper.selectFeedbackById(feedbackId);
    }

    /**
     * 查询意见反馈列表
     * 
     * @param feedback 意见反馈
     * @return 意见反馈
     */
    @Override
    public List<Feedback> selectFeedbackList(Feedback feedback)
    {
        return feedbackMapper.selectFeedbackList(feedback);
    }

    /**
     * 新增意见反馈
     * 
     * @param feedback 意见反馈
     * @return 结果
     */
    @Override
    public int insertFeedback(Feedback feedback)
    {
        feedback.setCreateTime(DateUtils.getNowDate());
        return feedbackMapper.insertFeedback(feedback);
    }

    /**
     * 修改意见反馈
     * 
     * @param feedback 意见反馈
     * @return 结果
     */
    @Override
    public int updateFeedback(Feedback feedback)
    {
        feedback.setUpdateTime(DateUtils.getNowDate());
        return feedbackMapper.updateFeedback(feedback);
    }

    /**
     * 删除意见反馈对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFeedbackByIds(String ids)
    {
        return feedbackMapper.deleteFeedbackByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除意见反馈信息
     * 
     * @param feedbackId 意见反馈ID
     * @return 结果
     */
    @Override
    public int deleteFeedbackById(Long feedbackId)
    {
        return feedbackMapper.deleteFeedbackById(feedbackId);
    }
}
