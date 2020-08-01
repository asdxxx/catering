package com.ruoyi.catering.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.DiningTypeMapper;
import com.ruoyi.catering.domain.DiningType;
import com.ruoyi.catering.service.IDiningTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 餐饮类型Service业务层处理
 * 
 * @author lsy
 * @date 2020-07-07
 */
@Service
public class DiningTypeServiceImpl implements IDiningTypeService 
{
    @Autowired
    private DiningTypeMapper diningTypeMapper;

    /**
     * 查询餐饮类型
     * 
     * @param typeId 餐饮类型ID
     * @return 餐饮类型
     */
    @Override
    public DiningType selectDiningTypeById(Long typeId)
    {
        return diningTypeMapper.selectDiningTypeById(typeId);
    }

    /**
     * 查询餐饮类型列表
     * 
     * @param diningType 餐饮类型
     * @return 餐饮类型
     */
    @Override
    public List<DiningType> selectDiningTypeList(DiningType diningType)
    {
        return diningTypeMapper.selectDiningTypeList(diningType);
    }

    /**
     * 新增餐饮类型
     * 
     * @param diningType 餐饮类型
     * @return 结果
     */
    @Override
    public int insertDiningType(DiningType diningType)
    {
        diningType.setCreateTime(DateUtils.getNowDate());
        return diningTypeMapper.insertDiningType(diningType);
    }

    /**
     * 修改餐饮类型
     * 
     * @param diningType 餐饮类型
     * @return 结果
     */
    @Override
    public int updateDiningType(DiningType diningType)
    {
        diningType.setUpdateTime(DateUtils.getNowDate());
        return diningTypeMapper.updateDiningType(diningType);
    }

    /**
     * 删除餐饮类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDiningTypeByIds(String ids)
    {
        return diningTypeMapper.deleteDiningTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除餐饮类型信息
     * 
     * @param typeId 餐饮类型ID
     * @return 结果
     */
    @Override
    public int deleteDiningTypeById(Long typeId)
    {
        return diningTypeMapper.deleteDiningTypeById(typeId);
    }
}
