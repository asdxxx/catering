package com.ruoyi.catering.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.OrdinanceMapper;
import com.ruoyi.catering.domain.Ordinance;
import com.ruoyi.catering.service.IOrdinanceService;
import com.ruoyi.common.core.text.Convert;

/**
 * 市容条例Service业务层处理
 * 
 * @author lsy
 * @date 2020-07-07
 */
@Service
public class OrdinanceServiceImpl implements IOrdinanceService 
{
    @Autowired
    private OrdinanceMapper ordinanceMapper;

    /**
     * 查询市容条例
     * 
     * @param ordinanceId 市容条例ID
     * @return 市容条例
     */
    @Override
    public Ordinance selectOrdinanceById(Long ordinanceId)
    {
        return ordinanceMapper.selectOrdinanceById(ordinanceId);
    }

    /**
     * 查询市容条例列表
     * 
     * @param ordinance 市容条例
     * @return 市容条例
     */
    @Override
    public List<Ordinance> selectOrdinanceList(Ordinance ordinance)
    {
        return ordinanceMapper.selectOrdinanceList(ordinance);
    }

    /**
     * 新增市容条例
     * 
     * @param ordinance 市容条例
     * @return 结果
     */
    @Override
    public int insertOrdinance(Ordinance ordinance)
    {
        ordinance.setCreateTime(DateUtils.getNowDate());
        return ordinanceMapper.insertOrdinance(ordinance);
    }

    /**
     * 修改市容条例
     * 
     * @param ordinance 市容条例
     * @return 结果
     */
    @Override
    public int updateOrdinance(Ordinance ordinance)
    {
        ordinance.setUpdateTime(DateUtils.getNowDate());
        return ordinanceMapper.updateOrdinance(ordinance);
    }

    /**
     * 删除市容条例对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOrdinanceByIds(String ids)
    {
        return ordinanceMapper.deleteOrdinanceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除市容条例信息
     * 
     * @param ordinanceId 市容条例ID
     * @return 结果
     */
    @Override
    public int deleteOrdinanceById(Long ordinanceId)
    {
        return ordinanceMapper.deleteOrdinanceById(ordinanceId);
    }
}
