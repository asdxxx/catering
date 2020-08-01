package com.ruoyi.catering.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 回收记录对象 cy_recovery_record
 *
 * @author lsy
 * @date 2020-07-08
 */
public class RecoveryRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 回收记录id
     */
    private Long id;

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
     * 回收人员id
     */
//    @Excel(name = "回收人员id")
    private Long userId;

    /**
     * 回收车号
     */
    @Excel(name = "回收车号")
    private String carNo;

    /**
     * 垃圾分类id
     */
//    @Excel(name = "垃圾分类id")
    private Long garbageId;

    /**
     * 回收重量
     */
    @Excel(name = "回收重量")
    private Long weight;

    /**
     * 取证照片
     */
//    @Excel(name = "取证照片")
    private String img;

    /**
     * 回收日期
     */
    @Excel(name = "回收日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recoveryDate;

    /**
     * 状态
     */
//    @Excel(name = "状态", readConverterExp = "1=上报")
    private Integer status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setGarbageId(Long garbageId) {
        this.garbageId = garbageId;
    }

    public Long getGarbageId() {
        return garbageId;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getWeight() {
        return weight;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setRecoveryDate(Date recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public Date getRecoveryDate() {
        return recoveryDate;
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
                .append("id", getId())
                .append("restaurantId", getRestaurantId())
                .append("location", getLocation())
                .append("longitude", getLongitude())
                .append("latitude", getLatitude())
                .append("userId", getUserId())
                .append("carNo", getCarNo())
                .append("garbageId", getGarbageId())
                .append("weight", getWeight())
                .append("img", getImg())
                .append("recoveryDate", getRecoveryDate())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
