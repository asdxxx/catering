package com.ruoyi.catering.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.SlideshowMapper;
import com.ruoyi.catering.domain.Slideshow;
import com.ruoyi.catering.service.ISlideshowService;
import com.ruoyi.common.core.text.Convert;

/**
 * 轮播图Service业务层处理
 * 
 * @author lsy
 * @date 2020-07-07
 */
@Service
public class SlideshowServiceImpl implements ISlideshowService 
{
    @Autowired
    private SlideshowMapper slideshowMapper;

    /**
     * 查询轮播图
     * 
     * @param slideshowId 轮播图ID
     * @return 轮播图
     */
    @Override
    public Slideshow selectSlideshowById(Long slideshowId)
    {
        return slideshowMapper.selectSlideshowById(slideshowId);
    }

    /**
     * 查询轮播图列表
     * 
     * @param slideshow 轮播图
     * @return 轮播图
     */
    @Override
    public List<Slideshow> selectSlideshowList(Slideshow slideshow)
    {
        return slideshowMapper.selectSlideshowList(slideshow);
    }

    /**
     * 新增轮播图
     * 
     * @param slideshow 轮播图
     * @return 结果
     */
    @Override
    public int insertSlideshow(Slideshow slideshow)
    {
        slideshow.setCreateTime(DateUtils.getNowDate());
        return slideshowMapper.insertSlideshow(slideshow);
    }

    /**
     * 修改轮播图
     * 
     * @param slideshow 轮播图
     * @return 结果
     */
    @Override
    public int updateSlideshow(Slideshow slideshow)
    {
        slideshow.setUpdateTime(DateUtils.getNowDate());
        return slideshowMapper.updateSlideshow(slideshow);
    }

    /**
     * 删除轮播图对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSlideshowByIds(String ids)
    {
        return slideshowMapper.deleteSlideshowByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除轮播图信息
     * 
     * @param slideshowId 轮播图ID
     * @return 结果
     */
    @Override
    public int deleteSlideshowById(Long slideshowId)
    {
        return slideshowMapper.deleteSlideshowById(slideshowId);
    }
}
