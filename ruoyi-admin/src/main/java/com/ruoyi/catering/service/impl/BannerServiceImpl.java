package com.ruoyi.catering.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.catering.mapper.BannerMapper;
import com.ruoyi.catering.domain.Banner;
import com.ruoyi.catering.service.IBannerService;
import com.ruoyi.common.core.text.Convert;

/**
 * 轮播图Service业务层处理
 * 
 * @author lsy
 * @date 2020-07-12
 */
@Service
public class BannerServiceImpl implements IBannerService 
{
    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 查询轮播图
     * 
     * @param bannerId 轮播图ID
     * @return 轮播图
     */
    @Override
    public Banner selectBannerById(Long bannerId)
    {
        return bannerMapper.selectBannerById(bannerId);
    }

    /**
     * 查询轮播图列表
     * 
     * @param banner 轮播图
     * @return 轮播图
     */
    @Override
    public List<Banner> selectBannerList(Banner banner)
    {
        return bannerMapper.selectBannerList(banner);
    }

    /**
     * 新增轮播图
     * 
     * @param banner 轮播图
     * @return 结果
     */
    @Override
    public int insertBanner(Banner banner)
    {
        banner.setCreateTime(DateUtils.getNowDate());
        return bannerMapper.insertBanner(banner);
    }

    /**
     * 修改轮播图
     * 
     * @param banner 轮播图
     * @return 结果
     */
    @Override
    public int updateBanner(Banner banner)
    {
        banner.setUpdateTime(DateUtils.getNowDate());
        return bannerMapper.updateBanner(banner);
    }

    /**
     * 删除轮播图对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBannerByIds(String ids)
    {
        return bannerMapper.deleteBannerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除轮播图信息
     * 
     * @param bannerId 轮播图ID
     * @return 结果
     */
    @Override
    public int deleteBannerById(Long bannerId)
    {
        return bannerMapper.deleteBannerById(bannerId);
    }
}
