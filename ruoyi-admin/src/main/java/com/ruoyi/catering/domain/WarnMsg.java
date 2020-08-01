package com.ruoyi.catering.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 告警信息对象 cy_warn_msg
 * 
 * @author lsy
 * @date 2020-07-12
 */
public class WarnMsg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 告警消息id */
    private Long id;

    /** 消息来源（1商家 2回收人员） */
    @Excel(name = "消息来源", readConverterExp = "1=商家,2=回收人员")
    private Integer type;

    /** 对应的意见反馈 */
//    @Excel(name = "对应的意见反馈")
    private Long feedbackId;

    /** 餐馆id */
//    @Excel(name = "餐馆id")
    private Long restaurantId;

    /** 信息内容 */
    @Excel(name = "信息内容")
    private String content;

    /** 消息类型（0普通消息 3投诉信息） */
    @Excel(name = "消息类型", readConverterExp = "0=普通消息,3=投诉信息")
    private Integer msgType;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "0=未处理,3=已处理")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }
    public void setFeedbackId(Long feedbackId) 
    {
        this.feedbackId = feedbackId;
    }

    public Long getFeedbackId() 
    {
        return feedbackId;
    }
    public void setRestaurantId(Long restaurantId) 
    {
        this.restaurantId = restaurantId;
    }

    public Long getRestaurantId() 
    {
        return restaurantId;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setMsgType(Integer msgType) 
    {
        this.msgType = msgType;
    }

    public Integer getMsgType() 
    {
        return msgType;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("feedbackId", getFeedbackId())
            .append("restaurantId", getRestaurantId())
            .append("content", getContent())
            .append("msgType", getMsgType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
