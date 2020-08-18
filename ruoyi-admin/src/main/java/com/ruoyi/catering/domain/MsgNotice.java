package com.ruoyi.catering.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 消息通知对象 cy_msg_notice
 *
 * @author lsy
 * @date 2020-08-11
 */
public class MsgNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    private Long noticeId;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private Integer type;

    /**
     * 接收人
     */
    @Excel(name = "接收人")
    private Long userId;

    @Excel(name = "商户")
    private Long restaurantId;

    /**
     * 消息内容
     */
    @Excel(name = "消息内容")
    private String content;

    /**
     * 是否已读
     */
    @Excel(name = "是否已读")
    private String hasRead;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() {
        return noticeId;
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

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
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
                .append("noticeId", getNoticeId())
                .append("type", getType())
                .append("userId", getUserId())
                .append("restaurantId", getRestaurantId())
                .append("content", getContent())
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
