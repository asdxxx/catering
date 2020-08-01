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
     * 微信openid
     */
//    @Excel(name = "微信openid")
    private String openid;

    /**
     * 客户编码
     */
//    @Excel(name = "客户编码")
    private String restaurantNo;
    /**
     * 店名
     */
    @Excel(name = "店名")
    private String name;

    /**
     * 所属区域id
     */
//    @Excel(name = "所属区域id")
//    private Long regionId;
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
     * 店铺照片
     */
//    @Excel(name = "店铺照片")
    private String storePhoto;
    /**
     * 简称
     */
//    @Excel(name = "简称")
    private String shortName;

    /**
     * 餐饮类型id
     */
    //    @Excel(name = "餐饮类型id")
    private Long diningTypeId;

    /**
     * 面积
     */
//    @Excel(name = "面积")
    private BigDecimal area;

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
     * 有无油水分离器（0有 1无）
     */
//    @Excel(name = "有无油水分离器", readConverterExp = "0=有,1=无")
    private Integer hasOilWaterSeparator;

    /**
     * 有无油烟净化器（0有 1无）
     */
//    @Excel(name = "有无油烟净化器", readConverterExp = "0=有,1=无")
    private Integer hasFumeCleaner;

    /**
     * 有无排水许可证（0有 1无）
     */
//    @Excel(name = "有无排水许可证", readConverterExp = "0=有,1=无")
    private Integer hasDischargePermit;

    /**
     * 排水许可证号
     */
//    @Excel(name = "排水许可证号")
    private String dischargeNo;

    /**
     * 排水许可证有效期
     */
//    @Excel(name = "排水许可证有效期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validityDay;

    /**
     * 停业日期
     */
//    @Excel(name = "停业日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date closedDay;

    /**
     * 燃气类型id
     */
//    @Excel(name = "燃气类型id")
    private String gasTypeId;

    /**
     * 废油回收协议（0已签 1未签）
     */
//    @Excel(name = "废油回收协议", readConverterExp = "0=已签,1=未签")
    private Integer haskwoRecoveryAgreement;

    /**
     * 排水许可证照片
     */
//    @Excel(name = "排水许可证照片")
    private String dischargePhoto;

    /**
     * 油水分离器照片
     */
//    @Excel(name = "油水分离器照片")
    private String separatorPhoto;

    /**
     * 油烟净化器照片
     */
//    @Excel(name = "油烟净化器照片")
    private String cleanerPhoto;

    /**
     * 中科协议照片
     */
//    @Excel(name = "中科协议照片")
    private String oilPhoto;

    /**
     * 证件照片
     */
//    @Excel(name = "证件照片")
    private String idPhoto;

    /**
     * 二维码
     */
//    @Excel(name = "二维码")
    private String qrCode;

    /**
     * 废油短信发送状态（0已发送 1未发送）
     */
//    @Excel(name = "废油短信发送状态", readConverterExp = "0=已发送,1=未发送")
    private Integer haskwosms;

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

    public String getRestaurantNo() {
        return restaurantNo;
    }

    public void setRestaurantNo(String restaurantNo) {
        this.restaurantNo = restaurantNo;
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

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
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

    public void setDiningTypeId(Long diningTypeId) {
        this.diningTypeId = diningTypeId;
    }

    public Long getDiningTypeId() {
        return diningTypeId;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

//    public void setRegionId(Long regionId) {
//        this.regionId = regionId;
//    }
//
//    public Long getRegionId() {
//        return regionId;
//    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getArea() {
        return area;
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

    public void setHasOilWaterSeparator(Integer hasOilWaterSeparator) {
        this.hasOilWaterSeparator = hasOilWaterSeparator;
    }

    public Integer getHasOilWaterSeparator() {
        return hasOilWaterSeparator;
    }

    public void setHasFumeCleaner(Integer hasFumeCleaner) {
        this.hasFumeCleaner = hasFumeCleaner;
    }

    public Integer getHasFumeCleaner() {
        return hasFumeCleaner;
    }

    public void setHasDischargePermit(Integer hasDischargePermit) {
        this.hasDischargePermit = hasDischargePermit;
    }

    public Integer getHasDischargePermit() {
        return hasDischargePermit;
    }

    public void setDischargeNo(String dischargeNo) {
        this.dischargeNo = dischargeNo;
    }

    public String getDischargeNo() {
        return dischargeNo;
    }

    public void setValidityDay(Date validityDay) {
        this.validityDay = validityDay;
    }

    public Date getValidityDay() {
        return validityDay;
    }

    public void setClosedDay(Date closedDay) {
        this.closedDay = closedDay;
    }

    public Date getClosedDay() {
        return closedDay;
    }

    public void setGasTypeId(String gasTypeId) {
        this.gasTypeId = gasTypeId;
    }

    public String getGasTypeId() {
        return gasTypeId;
    }

    public void setHaskwoRecoveryAgreement(Integer haskwoRecoveryAgreement) {
        this.haskwoRecoveryAgreement = haskwoRecoveryAgreement;
    }

    public Integer getHaskwoRecoveryAgreement() {
        return haskwoRecoveryAgreement;
    }

    public void setStorePhoto(String storePhoto) {
        this.storePhoto = storePhoto;
    }

    public String getStorePhoto() {
        return storePhoto;
    }

    public void setDischargePhoto(String dischargePhoto) {
        this.dischargePhoto = dischargePhoto;
    }

    public String getDischargePhoto() {
        return dischargePhoto;
    }

    public void setSeparatorPhoto(String separatorPhoto) {
        this.separatorPhoto = separatorPhoto;
    }

    public String getSeparatorPhoto() {
        return separatorPhoto;
    }

    public void setCleanerPhoto(String cleanerPhoto) {
        this.cleanerPhoto = cleanerPhoto;
    }

    public String getCleanerPhoto() {
        return cleanerPhoto;
    }

    public void setOilPhoto(String oilPhoto) {
        this.oilPhoto = oilPhoto;
    }

    public String getOilPhoto() {
        return oilPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setHaskwosms(Integer haskwosms) {
        this.haskwosms = haskwosms;
    }

    public Integer getHaskwosms() {
        return haskwosms;
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
                .append("restaurantId", getRestaurantId())
                .append("openid", getOpenid())
                .append("name", getName())
                .append("shortName", getShortName())
                .append("legalPerson", getLegalPerson())
                .append("premises", getPremises())
                .append("diningTypeId", getDiningTypeId())
                .append("tel", getTel())
//                .append("regionId", getRegionId())
                .append("deptId", getDeptId())
                .append("area", getArea())
                .append("longitude", getLongitude())
                .append("latitude", getLatitude())
                .append("hasOilWaterSeparator", getHasOilWaterSeparator())
                .append("hasFumeCleaner", getHasFumeCleaner())
                .append("hasDischargePermit", getHasDischargePermit())
                .append("dischargeNo", getDischargeNo())
                .append("validityDay", getValidityDay())
                .append("closedDay", getClosedDay())
                .append("gasTypeId", getGasTypeId())
                .append("haskwoRecoveryAgreement", getHaskwoRecoveryAgreement())
                .append("storePhoto", getStorePhoto())
                .append("dischargePhoto", getDischargePhoto())
                .append("separatorPhoto", getSeparatorPhoto())
                .append("cleanerPhoto", getCleanerPhoto())
                .append("oilPhoto", getOilPhoto())
                .append("idPhoto", getIdPhoto())
                .append("qrCode", getQrCode())
                .append("haskwosms", getHaskwosms())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
