package com.ruoyi.catering.service;

import java.util.List;
import com.ruoyi.catering.domain.Banner;

/**
 * 轮播图Service接口
 * 
 * @author lsy
 * @date 2020-07-12
 */
public interface IBannerService 
{
    /**
     * 查询轮播图
     * 
     * @param bannerId 轮播图ID
     * @return 轮播图
     */
    public Banner selectBannerById(Long bannerId);

    /**
     * 查询轮播图列表
     * 
     * @param banner 轮播图
     * @return 轮播图集合
     */
    public List<Banner> selectBannerList(Banner banner);

    /**
     * 新增轮播图
     * 
     * @param banner 轮播图
     * @return 结果
     */
    public int insertBanner(Banner banner);

    /**
     * 修改轮播图
     * 
     * @param banner 轮播图
     * @return 结果
     */
    public int updateBanner(Banner banner);

    /**
     * 批量删除轮播图
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBannerByIds(String ids);

    /**
     * 删除轮播图信息
     * 
     * @param bannerId 轮播图ID
     * @return 结果
     */
    public int deleteBannerById(Long bannerId);
}
