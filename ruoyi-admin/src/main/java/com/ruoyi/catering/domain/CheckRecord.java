package com.ruoyi.catering.domain;

import java.util.List;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 检查记录对象 cy_check_record
 *
 * @author lsy
 * @date 2020-07-23
 */
public class CheckRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 检查记录id
     */
    private Long recordId;

    /**
     * 检查人员id
     */
//    @Excel(name = "检查人员id")
    private Long userId;

    /**
     * 餐馆id
     */
//    @Excel(name = "餐馆id")
    private Long restaurantId;

    /**
     * 定位地点
     */
    @Excel(name = "定位地点")
    private String location;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 取证照片
     */
//    @Excel(name = "取证照片")
    private String img;

    /**
     * 取证照片
     */
    @Excel(name = "检查内容")
    private String content;

    /**
     * 检查日期
     */
    @Excel(name = "检查日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkDate;

    /**
     * 状态
     */
    @Excel(name = "状态", readConverterExp = "0=合格,1=不合格")
    private Integer status;

    private Integer size;

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getRecordId() {
        return recordId;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("recordId", getRecordId())
                .append("userId", getUserId())
                .append("restaurantId", getRestaurantId())
                .append("location", getLocation())
                .append("longitude", getLongitude())
                .append("latitude", getLatitude())
                .append("img", getImg())
                .append("content", getContent())
                .append("checkDate", getCheckDate())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("size", getSize())
                .toString();
    }
}
