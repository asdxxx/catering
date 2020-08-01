package com.ruoyi.api;

import com.ruoyi.catering.domain.Banner;
import com.ruoyi.catering.domain.Slideshow;
import com.ruoyi.catering.service.IBannerService;
import com.ruoyi.catering.service.ISlideshowService;
import com.ruoyi.catering.vo.BannerVo;
import com.ruoyi.catering.vo.SlideshowVo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-12 12:06
 */
@Slf4j
@RestController
@RequestMapping("/api/banner")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "接口对接", tags = {"接口对接"})
public class BannerApiController {
    @Autowired
    private IBannerService bannerService;

//    @GetMapping("/getBanner")
//    public AjaxResult getBanner() {
//        Banner banner = new Banner();
//        banner.setStatus(0);
//        List<Banner> banners = bannerService.selectBannerList(banner);
//        if (banners == null || banners.size() != 1) {
//            return AjaxResult.error("获取失败");
//        }
//        BannerVo bannerVo = new BannerVo();
//        BeanUtils.copyProperties(banners.get(0), bannerVo);
//        if (StringUtils.isNotEmpty(bannerVo.getIndexUrl())) {
//            String[] indexUrlArray = bannerVo.getIndexUrl().split(",");
//            bannerVo.setIndexUrlArray(indexUrlArray);
//        }
//        if (StringUtils.isNotEmpty(bannerVo.getCollectorUrl())) {
//            String[] collectorUrlArray = bannerVo.getCollectorUrl().split(",");
//            bannerVo.setCollectorUrlArray(collectorUrlArray);
//        }
//        if (StringUtils.isNotEmpty(bannerVo.getMerchantUrl())) {
//            String[] merchantUrlArray = bannerVo.getMerchantUrl().split(",");
//            bannerVo.setMerchantUrlArray(merchantUrlArray);
//        }
//        return AjaxResult.success("获取成功", bannerVo);
//    }
}