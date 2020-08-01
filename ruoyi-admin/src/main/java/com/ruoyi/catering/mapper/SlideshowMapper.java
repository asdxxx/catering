package com.ruoyi.catering.mapper;

import java.util.List;
import com.ruoyi.catering.domain.Slideshow;

/**
 * 轮播图Mapper接口
 * 
 * @author lsy
 * @date 2020-07-07
 */
public interface SlideshowMapper 
{
    /**
     * 查询轮播图
     * 
     * @param slideshowId 轮播图ID
     * @return 轮播图
     */
    public Slideshow selectSlideshowById(Long slideshowId);

    /**
     * 查询轮播图列表
     * 
     * @param slideshow 轮播图
     * @return 轮播图集合
     */
    public List<Slideshow> selectSlideshowList(Slideshow slideshow);

    /**
     * 新增轮播图
     * 
     * @param slideshow 轮播图
     * @return 结果
     */
    public int insertSlideshow(Slideshow slideshow);

    /**
     * 修改轮播图
     * 
     * @param slideshow 轮播图
     * @return 结果
     */
    public int updateSlideshow(Slideshow slideshow);

    /**
     * 删除轮播图
     * 
     * @param slideshowId 轮播图ID
     * @return 结果
     */
    public int deleteSlideshowById(Long slideshowId);

    /**
     * 批量删除轮播图
     * 
     * @param slideshowIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSlideshowByIds(String[] slideshowIds);
}
