package com.ruoyi.catering.mapper;

import java.util.List;
import com.ruoyi.catering.domain.Ordinance;

/**
 * 市容条例Mapper接口
 * 
 * @author lsy
 * @date 2020-07-07
 */
public interface OrdinanceMapper 
{
    /**
     * 查询市容条例
     * 
     * @param ordinanceId 市容条例ID
     * @return 市容条例
     */
    public Ordinance selectOrdinanceById(Long ordinanceId);

    /**
     * 查询市容条例列表
     * 
     * @param ordinance 市容条例
     * @return 市容条例集合
     */
    public List<Ordinance> selectOrdinanceList(Ordinance ordinance);

    /**
     * 新增市容条例
     * 
     * @param ordinance 市容条例
     * @return 结果
     */
    public int insertOrdinance(Ordinance ordinance);

    /**
     * 修改市容条例
     * 
     * @param ordinance 市容条例
     * @return 结果
     */
    public int updateOrdinance(Ordinance ordinance);

    /**
     * 删除市容条例
     * 
     * @param ordinanceId 市容条例ID
     * @return 结果
     */
    public int deleteOrdinanceById(Long ordinanceId);

    /**
     * 批量删除市容条例
     * 
     * @param ordinanceIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrdinanceByIds(String[] ordinanceIds);
}
