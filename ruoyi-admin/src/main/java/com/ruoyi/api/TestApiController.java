package com.ruoyi.api;

import com.ruoyi.catering.domain.CheckRecord;
import com.ruoyi.catering.utils.RedisUtil;
import com.ruoyi.catering.vo.CheckRecordVo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-10-27 17:23
 */
@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestApiController {
    @Autowired
    private ISysConfigService configService;

    @GetMapping(value = "/test")
    public AjaxResult getListByRestaurantId(Double longitude, Double latitude) {
        log.info("测试接口调用");
        double radius = 1;
        String value = configService.selectConfigByKey("catering.restaurant.radius");
        log.info("半径{}", value);
        try {
            radius = Double.parseDouble(value);
        } catch (Exception e) {
            log.error(e.toString());
        }
        GeoCoordinate geoCoordinate = new GeoCoordinate(longitude, latitude);
        List<GeoRadiusResponse> list = RedisUtil.geoRadius("restaurant", geoCoordinate, radius);
        return AjaxResult.success(list);
    }
}