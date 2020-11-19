package com.ruoyi.catering.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 意见反馈对象 cy_feedback
 *
 * @author lsy
 * @date 2020-07-08
 */
public class Feedback extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 意见反馈id
     */
    private Long feedbackId;

    /**
     * 反馈来源（1商家 2回收人员）
     */
    @Excel(name = "反馈来源", readConverterExp = "1=商家,2=回收人员")
    private Integer type;

    /**
     * 用户id
     */
//    @Excel(name = "用户id")
    private Long userId;

    /**
     * 餐馆id
     */
//    @Excel(name = "餐馆id")
    private Long restaurantId;

    /**
     * 建议内容
     */
    @Excel(name = "建议内容")
    private String content;

    /**
     * 取证照片
     */
//    @Excel(name = "取证照片")
    private String img;

    /**
     * 是否已读（N未读 Y已读）
     */
    @Excel(name = "是否已读", readConverterExp = "N=未读,Y=已读")
    private String hasRead;

    /**
     * 状态（0未处理 1已处理）
     */
    @Excel(name = "状态", readConverterExp = "0=未处理,1=已处理")
    private Integer status;

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setHasRead(String hasRead) {
        this.hasRead = hasRead;
    }

    public String getHasRead() {
        return hasRead;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("feedbackId", getFeedbackId())
                .append("type", getType())
                .append("userId", getUserId())
                .append("restaurantId", getRestaurantId())
                .append("content", getContent())
                .append("img", getImg())
                .append("hasRead", getHasRead())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
