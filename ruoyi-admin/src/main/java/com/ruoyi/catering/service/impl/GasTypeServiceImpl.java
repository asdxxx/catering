package com.ruoyi.catering.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.GasTypeMapper;
import com.ruoyi.catering.domain.GasType;
import com.ruoyi.catering.service.IGasTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 燃气类型Service业务层处理
 * 
 * @author lsy
 * @date 2020-07-08
 */
@Service
public class GasTypeServiceImpl implements IGasTypeService 
{
    @Autowired
    private GasTypeMapper gasTypeMapper;

    /**
     * 查询燃气类型
     * 
     * @param typeId 燃气类型ID
     * @return 燃气类型
     */
    @Override
    public GasType selectGasTypeById(Long typeId)
    {
        return gasTypeMapper.selectGasTypeById(typeId);
    }

    /**
     * 查询燃气类型列表
     * 
     * @param gasType 燃气类型
     * @return 燃气类型
     */
    @Override
    public List<GasType> selectGasTypeList(GasType gasType)
    {
        return gasTypeMapper.selectGasTypeList(gasType);
    }

    /**
     * 新增燃气类型
     * 
     * @param gasType 燃气类型
     * @return 结果
     */
    @Override
    public int insertGasType(GasType gasType)
    {
        gasType.setCreateTime(DateUtils.getNowDate());
        return gasTypeMapper.insertGasType(gasType);
    }

    /**
     * 修改燃气类型
     * 
     * @param gasType 燃气类型
     * @return 结果
     */
    @Override
    public int updateGasType(GasType gasType)
    {
        gasType.setUpdateTime(DateUtils.getNowDate());
        return gasTypeMapper.updateGasType(gasType);
    }

    /**
     * 删除燃气类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGasTypeByIds(String ids)
    {
        return gasTypeMapper.deleteGasTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除燃气类型信息
     * 
     * @param typeId 燃气类型ID
     * @return 结果
     */
    @Override
    public int deleteGasTypeById(Long typeId)
    {
        return gasTypeMapper.deleteGasTypeById(typeId);
    }
}
