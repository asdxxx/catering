package com.ruoyi.catering.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 市容条例对象 cy_ordinance
 * 
 * @author lsy
 * @date 2020-07-07
 */
public class Ordinance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 条例id */
    private Long ordinanceId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 简介 */
    @Excel(name = "简介")
    private String introduction;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    public void setOrdinanceId(Long ordinanceId) 
    {
        this.ordinanceId = ordinanceId;
    }

    public Long getOrdinanceId() 
    {
        return ordinanceId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setIntroduction(String introduction) 
    {
        this.introduction = introduction;
    }

    public String getIntroduction() 
    {
        return introduction;
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
            .append("ordinanceId", getOrdinanceId())
            .append("title", getTitle())
            .append("introduction", getIntroduction())
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
