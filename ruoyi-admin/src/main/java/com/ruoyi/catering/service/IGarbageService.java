package com.ruoyi.catering.service;

import java.util.List;
import com.ruoyi.catering.domain.Garbage;

/**
 * 垃圾类型Service接口
 * 
 * @author lsy
 * @date 2020-07-08
 */
public interface IGarbageService 
{
    /**
     * 查询垃圾类型
     * 
     * @param garbageId 垃圾类型ID
     * @return 垃圾类型
     */
    public Garbage selectGarbageById(Long garbageId);

    /**
     * 查询垃圾类型列表
     * 
     * @param garbage 垃圾类型
     * @return 垃圾类型集合
     */
    public List<Garbage> selectGarbageList(Garbage garbage);

    /**
     * 新增垃圾类型
     * 
     * @param garbage 垃圾类型
     * @return 结果
     */
    public int insertGarbage(Garbage garbage);

    /**
     * 修改垃圾类型
     * 
     * @param garbage 垃圾类型
     * @return 结果
     */
    public int updateGarbage(Garbage garbage);

    /**
     * 批量删除垃圾类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGarbageByIds(String ids);

    /**
     * 删除垃圾类型信息
     * 
     * @param garbageId 垃圾类型ID
     * @return 结果
     */
    public int deleteGarbageById(Long garbageId);
}
