package com.ruoyi.catering.service;

import java.util.List;
import com.ruoyi.catering.domain.DiningType;

/**
 * 餐饮类型Service接口
 * 
 * @author lsy
 * @date 2020-07-07
 */
public interface IDiningTypeService 
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
     * 批量删除餐饮类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDiningTypeByIds(String ids);

    /**
     * 删除餐饮类型信息
     * 
     * @param typeId 餐饮类型ID
     * @return 结果
     */
    public int deleteDiningTypeById(Long typeId);
}
