package com.ruoyi.catering.mapper;

import java.util.List;
import com.ruoyi.catering.domain.Banner;

/**
 * 轮播图Mapper接口
 * 
 * @author lsy
 * @date 2020-07-12
 */
public interface BannerMapper 
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
     * 删除轮播图
     * 
     * @param bannerId 轮播图ID
     * @return 结果
     */
    public int deleteBannerById(Long bannerId);

    /**
     * 批量删除轮播图
     * 
     * @param bannerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBannerByIds(String[] bannerIds);
}
