package com.ruoyi.catering.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 餐饮单位信息对象 cy_restaurant
 *
 * @author lsy
 * @date 2020-07-08
 */
public class Restaurant extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 餐饮单位id
     */
    private Long restaurantId;

    /**
     * 店名
     */
    @Excel(name = "店名")
    private String name;

    /**
     * 所属区域id
     */
    private Long deptId;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String legalPerson;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String tel;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String premises;

    @Excel(name = "合同签订日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date signDate;

    @Excel(name = "合同到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;

    /**
     * 店铺规格
     */
    @Excel(name = "店铺规格", readConverterExp = "1=小型,2=中型,3=大型")
    private Integer size;

    /**
     * 商户性质
     */
    @Excel(name = "商户性质", readConverterExp = "1=普通,2=国企,3=事业单位,4=政府")
    private Integer nature;

    /**
     * 店铺照片
     */
//    @Excel(name = "店铺照片")
    private String storePhoto;

    /**
     * 经度
     */
//    @Excel(name = "经度")
    private String longitude;

    /**
     * 纬度
     */
//    @Excel(name = "纬度")
    private String latitude;

    /**
     * 二维码
     */
//    @Excel(name = "二维码")
    private String qrCode;

    /**
     * 营业状态
     */
    @Excel(name = "营业状态", readConverterExp = "0=开业,1=停业")
    private Integer status;

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setPremises(String premises) {
        this.premises = premises;
    }

    public String getPremises() {
        return premises;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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

    public void setStorePhoto(String storePhoto) {
        this.storePhoto = storePhoto;
    }

    public String getStorePhoto() {
        return storePhoto;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("restaurantId", getRestaurantId())
                .append("name", getName())
                .append("legalPerson", getLegalPerson())
                .append("premises", getPremises())
                .append("tel", getTel())
                .append("deptId", getDeptId())
                .append("longitude", getLongitude())
                .append("latitude", getLatitude())
                .append("storePhoto", getStorePhoto())
                .append("qrCode", getQrCode())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
