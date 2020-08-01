package com.ruoyi.catering.service;

import java.util.List;
import com.ruoyi.catering.domain.GasType;

/**
 * 燃气类型Service接口
 * 
 * @author lsy
 * @date 2020-07-08
 */
public interface IGasTypeService 
{
    /**
     * 查询燃气类型
     * 
     * @param typeId 燃气类型ID
     * @return 燃气类型
     */
    public GasType selectGasTypeById(Long typeId);

    /**
     * 查询燃气类型列表
     * 
     * @param gasType 燃气类型
     * @return 燃气类型集合
     */
    public List<GasType> selectGasTypeList(GasType gasType);

    /**
     * 新增燃气类型
     * 
     * @param gasType 燃气类型
     * @return 结果
     */
    public int insertGasType(GasType gasType);

    /**
     * 修改燃气类型
     * 
     * @param gasType 燃气类型
     * @return 结果
     */
    public int updateGasType(GasType gasType);

    /**
     * 批量删除燃气类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGasTypeByIds(String ids);

    /**
     * 删除燃气类型信息
     * 
     * @param typeId 燃气类型ID
     * @return 结果
     */
    public int deleteGasTypeById(Long typeId);
}
