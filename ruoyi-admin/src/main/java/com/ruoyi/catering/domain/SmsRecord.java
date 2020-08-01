package com.ruoyi.catering.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 短信发送记录对象 cy_sms_record
 * 
 * @author lsy
 * @date 2020-07-08
 */
public class SmsRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 短信发送id */
    private Long recordId;

    /** 接收人 */
    @Excel(name = "接收人")
    private String name;

    /** 发送类型 */
//    @Excel(name = "发送类型")
    private Long garbageId;

    /** 餐馆id */
//    @Excel(name = "餐馆id")
    private Long restaurantId;

    /** 餐馆名称 */
    @Excel(name = "餐馆名称")
    private String restaurantName;

    /** 手机号 */
    @Excel(name = "手机号")
    private String mobile;

    /** 短信内容 */
    @Excel(name = "短信内容")
    private String content;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "0=失败,1=成功")
    private Integer status;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setGarbageId(Long garbageId) 
    {
        this.garbageId = garbageId;
    }

    public Long getGarbageId() 
    {
        return garbageId;
    }
    public void setRestaurantId(Long restaurantId) 
    {
        this.restaurantId = restaurantId;
    }

    public Long getRestaurantId() 
    {
        return restaurantId;
    }
    public void setRestaurantName(String restaurantName) 
    {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantName() 
    {
        return restaurantName;
    }
    public void setMobile(String mobile) 
    {
        this.mobile = mobile;
    }

    public String getMobile() 
    {
        return mobile;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
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
            .append("recordId", getRecordId())
            .append("name", getName())
            .append("garbageId", getGarbageId())
            .append("restaurantId", getRestaurantId())
            .append("restaurantName", getRestaurantName())
            .append("mobile", getMobile())
            .append("content", getContent())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
