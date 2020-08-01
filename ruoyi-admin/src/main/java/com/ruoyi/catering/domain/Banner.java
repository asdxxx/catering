package com.ruoyi.catering.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 轮播图对象 cy_banner
 * 
 * @author lsy
 * @date 2020-07-12
 */
public class Banner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 轮播图id */
    private Long bannerId;

    /** 首页轮播图 */
    @Excel(name = "首页轮播图")
    private String indexUrl;

    /** 商户轮播图 */
    @Excel(name = "商户轮播图")
    private String merchantUrl;

    /** 回收轮播图 */
    @Excel(name = "回收轮播图")
    private String collectorUrl;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    public void setBannerId(Long bannerId) 
    {
        this.bannerId = bannerId;
    }

    public Long getBannerId() 
    {
        return bannerId;
    }
    public void setIndexUrl(String indexUrl) 
    {
        this.indexUrl = indexUrl;
    }

    public String getIndexUrl() 
    {
        return indexUrl;
    }
    public void setMerchantUrl(String merchantUrl) 
    {
        this.merchantUrl = merchantUrl;
    }

    public String getMerchantUrl() 
    {
        return merchantUrl;
    }
    public void setCollectorUrl(String collectorUrl) 
    {
        this.collectorUrl = collectorUrl;
    }

    public String getCollectorUrl() 
    {
        return collectorUrl;
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
            .append("bannerId", getBannerId())
            .append("indexUrl", getIndexUrl())
            .append("merchantUrl", getMerchantUrl())
            .append("collectorUrl", getCollectorUrl())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
