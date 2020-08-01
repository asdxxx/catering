package com.ruoyi.catering.mapper;

import java.util.List;
import com.ruoyi.catering.domain.DiningType;

/**
 * 餐饮类型Mapper接口
 * 
 * @author lsy
 * @date 2020-07-07
 */
public interface DiningTypeMapper 
{
    /**
     * 查询餐饮类型
     * 
     * @param typeId 餐饮类型ID
     * @return 餐饮类型
     */
    public DiningType selectDiningTypeById(Long typeId);

    /**
     * 查询餐饮类型列表
     * 
     * @param diningType 餐饮类型
     * @return 餐饮类型集合
     */
    public List<DiningType> selectDiningTypeList(DiningType diningType);

    /**
     * 新增餐饮类型
     * 
     * @param diningType 餐饮类型
     * @return 结果
     */
    public int insertDiningType(DiningType diningType);

    /**
     * 修改餐饮类型
     * 
     * @param diningType 餐饮类型
     * @return 结果
     */
    public int updateDiningType(DiningType diningType);

    /**
     * 删除餐饮类型
     * 
     * @param typeId 餐饮类型ID
     * @return 结果
     */
    public int deleteDiningTypeById(Long typeId);

    /**
     * 批量删除餐饮类型
     * 
     * @param typeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDiningTypeByIds(String[] typeIds);
}
