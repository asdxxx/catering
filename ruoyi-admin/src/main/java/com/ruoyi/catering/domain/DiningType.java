package com.ruoyi.catering.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 餐饮类型对象 cy_dining_type
 * 
 * @author lsy
 * @date 2020-07-07
 */
public class DiningType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 餐饮类型id */
    private Long typeId;

    /** 类型名称 */
    @Excel(name = "类型名称")
    private String name;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用", prompt = "0=正常,1=停用")
    private Integer status;

    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
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
            .append("typeId", getTypeId())
            .append("name", getName())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
