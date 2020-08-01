package com.ruoyi.catering.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 轮播图对象 cy_slideshow
 * 
 * @author lsy
 * @date 2020-07-07
 */
public class Slideshow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 轮播图id */
    private Long slideshowId;

    /** 场景编号 */
    @Excel(name = "场景编号")
    private Integer scenarioNo;

    /** 使用场景 */
    @Excel(name = "使用场景")
    private String scenarioName;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String url;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    public void setSlideshowId(Long slideshowId) 
    {
        this.slideshowId = slideshowId;
    }

    public Long getSlideshowId() 
    {
        return slideshowId;
    }
    public void setScenarioNo(Integer scenarioNo) 
    {
        this.scenarioNo = scenarioNo;
    }

    public Integer getScenarioNo() 
    {
        return scenarioNo;
    }
    public void setScenarioName(String scenarioName) 
    {
        this.scenarioName = scenarioName;
    }

    public String getScenarioName() 
    {
        return scenarioName;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
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
            .append("slideshowId", getSlideshowId())
            .append("scenarioNo", getScenarioNo())
            .append("scenarioName", getScenarioName())
            .append("url", getUrl())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
