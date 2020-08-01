package com.ruoyi.catering.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.GarbageMapper;
import com.ruoyi.catering.domain.Garbage;
import com.ruoyi.catering.service.IGarbageService;
import com.ruoyi.common.core.text.Convert;

/**
 * 垃圾类型Service业务层处理
 * 
 * @author lsy
 * @date 2020-07-08
 */
@Service
public class GarbageServiceImpl implements IGarbageService 
{
    @Autowired
    private GarbageMapper garbageMapper;

    /**
     * 查询垃圾类型
     * 
     * @param garbageId 垃圾类型ID
     * @return 垃圾类型
     */
    @Override
    public Garbage selectGarbageById(Long garbageId)
    {
        return garbageMapper.selectGarbageById(garbageId);
    }

    /**
     * 查询垃圾类型列表
     * 
     * @param garbage 垃圾类型
     * @return 垃圾类型
     */
    @Override
    public List<Garbage> selectGarbageList(Garbage garbage)
    {
        return garbageMapper.selectGarbageList(garbage);
    }

    /**
     * 新增垃圾类型
     * 
     * @param garbage 垃圾类型
     * @return 结果
     */
    @Override
    public int insertGarbage(Garbage garbage)
    {
        garbage.setCreateTime(DateUtils.getNowDate());
        return garbageMapper.insertGarbage(garbage);
    }

    /**
     * 修改垃圾类型
     * 
     * @param garbage 垃圾类型
     * @return 结果
     */
    @Override
    public int updateGarbage(Garbage garbage)
    {
        garbage.setUpdateTime(DateUtils.getNowDate());
        return garbageMapper.updateGarbage(garbage);
    }

    /**
     * 删除垃圾类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGarbageByIds(String ids)
    {
        return garbageMapper.deleteGarbageByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除垃圾类型信息
     * 
     * @param garbageId 垃圾类型ID
     * @return 结果
     */
    @Override
    public int deleteGarbageById(Long garbageId)
    {
        return garbageMapper.deleteGarbageById(garbageId);
    }
}
