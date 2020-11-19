package com.ruoyi.api;

import com.ruoyi.catering.constants.RestaurantConstant;
import com.ruoyi.catering.domain.*;
import com.ruoyi.catering.service.*;
import com.ruoyi.catering.utils.BaseUtil;
import com.ruoyi.catering.utils.BusinessUtil;
import com.ruoyi.catering.utils.ListPageUtil;
import com.ruoyi.catering.utils.RedisUtil;
import com.ruoyi.catering.data.RestaurantQueryData;
import com.ruoyi.catering.vo.CheckRecordVo;
import com.ruoyi.catering.vo.RestaurantVo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-12 20:28
 */
@Slf4j
@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "餐馆信息接口")
public class RestaurantApiController {
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IRecoveryRecordService recoveryRecordService;
    @Autowired
    private ICheckRecordService checkRecordService;
    @Autowired
    private ISysConfigService configService;

    @ApiOperation("获取用户餐馆列表")
    @GetMapping(value = "/getListByUserId")
    public AjaxResult getListByUserId(Long userId) {
        SysUser sysUser = userService.selectUserById(userId);
        String sqlString = BaseUtil.dataScopeFilter(sysUser);
        List<Restaurant> restaurants = restaurantService.canRecycle(sqlString, null, "");
        return AjaxResult.success(restaurants);
    }

    @ApiOperation("模糊查询用户餐馆列表")
    @GetMapping(value = "/fuzzyQuery")
    public AjaxResult fuzzyQuery(Long userId, String name, String isRecovered, String isChecked, String isClosed, PageDomain pageDomain) {
        if (userId == null) {
            return AjaxResult.error("未识别当前用户信息");
        }
        if (pageDomain.getPageNum() == null) {
            pageDomain.setPageNum(1);
        }
        if (pageDomain.getPageSize() == null) {
            pageDomain.setPageSize(10);
        }
        SysUser sysUser = userService.selectUserById(userId);
        String sqlString = BaseUtil.dataScopeFilter(sysUser);
        List<Restaurant> restaurants = restaurantService.canRecycle(sqlString, null, name);
        //已停业餐馆
        if (StringUtils.isNotEmpty(isClosed) && isClosed.equals("Y")) {
            List<Restaurant> restaurantList = new ArrayList<>();
            for (Restaurant restaurant : restaurants) {
                if (restaurant.getStatus() == 1) {
                    restaurantList.add(restaurant);
                }
            }
            ListPageUtil<Restaurant> pageUtil = new ListPageUtil<>(restaurantList, pageDomain.getPageNum(), pageDomain.getPageSize());
            return AjaxResult.success(pageUtil);
        }
        if (StringUtils.isNotEmpty(isRecovered) || StringUtils.isNotEmpty(isChecked)) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.add(Calendar.DATE, -3);
            Date smallDate = cal.getTime();
            cal.add(Calendar.DATE, -3);
            Date mediumDate = cal.getTime();
            cal.add(Calendar.DATE, -3);
            Date largeDate = cal.getTime();
            String ids = "";
            //餐馆id集合
            for (Restaurant restaurant : restaurants) {
                ids += restaurant.getRestaurantId() + ",";
            }
            if (ids.length() > 0) {
                ids = ids.substring(0, ids.length() - 1);
            }
            List<Restaurant> restaurantList = new ArrayList<>();
            if (StringUtils.isNotEmpty(isRecovered) && isRecovered.equals("Y")) {
                //已回收餐馆
                List<RecoveryRecord> recoveryRecords = recoveryRecordService.selectListByRestaurantId(ids);
                for (RecoveryRecord rr : recoveryRecords) {
                    Restaurant r = restaurantService.selectRestaurantById(rr.getRestaurantId());
                    int size = r.getSize();
                    Date date = size == 2 ? mediumDate : size == 3 ? largeDate : smallDate;
                    if (rr.getRecoveryDate().after(date)) {
                        restaurantList.add(r);
                    }
                }
                ListPageUtil<Restaurant> pageUtil = new ListPageUtil<>(restaurantList, pageDomain.getPageNum(), pageDomain.getPageSize());
                return AjaxResult.success(pageUtil);
            } else if (StringUtils.isNotEmpty(isRecovered) && isRecovered.equals("N")) {
                //未回收餐馆
                List<RecoveryRecord> recoveryRecords = recoveryRecordService.selectListByRestaurantId(ids);
                List<Long> hasIds = new ArrayList<>();
                for (RecoveryRecord rr : recoveryRecords) {
                    Restaurant r = restaurantService.selectRestaurantById(rr.getRestaurantId());
                    int size = r.getSize();
                    Date date = size == 2 ? mediumDate : size == 3 ? largeDate : smallDate;
                    if (rr.getRecoveryDate().after(date)) {
                        hasIds.add(rr.getRestaurantId());
                    }
                }
                for (Restaurant restaurant : restaurants) {
                    if (restaurant.getStatus() != null && restaurant.getStatus() == 1) {
                        continue;
                    }
                    if (!hasIds.contains(restaurant.getRestaurantId())) {
                        restaurantList.add(restaurant);
                    }
                }
                ListPageUtil<Restaurant> pageUtil = new ListPageUtil<>(restaurantList, pageDomain.getPageNum(), pageDomain.getPageSize());
                return AjaxResult.success(pageUtil);
            } else if (StringUtils.isNotEmpty(isChecked) && isChecked.equals("Y")) {
                //已检查餐馆
                List<CheckRecord> checkRecords = checkRecordService.selectListByRestaurantId(ids);
                for (CheckRecord cr : checkRecords) {
                    Restaurant r = restaurantService.selectRestaurantById(cr.getRestaurantId());
                    restaurantList.add(r);
//                    int size = r.getSize();
//                    Date date = size == 2 ? mediumDate : size == 3 ? largeDate : smallDate;
//                    if (cr.getCheckDate().after(date)) {
//                        restaurantList.add(r);
//                    }
                }
                ListPageUtil<Restaurant> pageUtil = new ListPageUtil<>(restaurantList, pageDomain.getPageNum(), pageDomain.getPageSize());
                return AjaxResult.success(pageUtil);
            } else if (StringUtils.isNotEmpty(isChecked) && isChecked.equals("N")) {
                //未检查餐馆
                List<CheckRecord> checkRecords = checkRecordService.selectListByRestaurantId(ids);
                List<Long> hasIds = new ArrayList<>();
                for (CheckRecord cr : checkRecords) {
                    hasIds.add(cr.getRestaurantId());
//                    Restaurant r = restaurantService.selectRestaurantById(cr.getRestaurantId());
//                    int size = r.getSize();
//                    Date date = size == 2 ? mediumDate : size == 3 ? largeDate : smallDate;
//                    if (cr.getCheckDate().after(date)) {
//                        hasIds.add(cr.getRestaurantId());
//                    }
                }
                for (Restaurant restaurant : restaurants) {
                    if (restaurant.getStatus() != null && restaurant.getStatus() == 1) {
                        continue;
                    }
                    if (!hasIds.contains(restaurant.getRestaurantId())) {
                        restaurantList.add(restaurant);
                    }
                }
                ListPageUtil<Restaurant> pageUtil = new ListPageUtil<>(restaurantList, pageDomain.getPageNum(), pageDomain.getPageSize());
                return AjaxResult.success(pageUtil);
            }
        }
        ListPageUtil<Restaurant> pageUtil = new ListPageUtil<>(restaurants, pageDomain.getPageNum(), pageDomain.getPageSize());
        return AjaxResult.success(pageUtil);
    }

    @ApiOperation("查询商户详情")
    @GetMapping(value = "/getDataById")
    public AjaxResult getDataById(Long restaurantId) {
        Restaurant restaurant = restaurantService.selectRestaurantById(restaurantId);
        return AjaxResult.success(restaurant);
    }

    @ApiOperation("修改商户信息")
    @RequestMapping(value = "/edit")
    public AjaxResult edit(Restaurant restaurant) {
        int result = restaurantService.updateRestaurant(restaurant);
        if (result <= 0) {
            return AjaxResult.error("修改失败");
        }
        return AjaxResult.success("修改成功");
    }

    @ApiOperation("新增商户信息")
    @RequestMapping(value = "/add")
    public AjaxResult add(Restaurant restaurant) {
        int result = restaurantService.insertRestaurant(restaurant);
        if (result <= 0) {
            return AjaxResult.error("新增失败");
        }
        return AjaxResult.success("新增成功");
    }

    @ApiOperation("根据当前登录人员获取地图餐馆列表")
    @RequestMapping(value = "/getMapList")
    public AjaxResult getMapList(Long userId, Double longitude, Double latitude) {
        if (longitude == null || latitude == null) {
            return AjaxResult.error("未获取到当前定位");
        }
        double radius = 1;
        String value = configService.selectConfigByKey("catering.restaurant.radius");
        try {
            radius = Double.parseDouble(value);
        } catch (Exception e) {

        }
        GeoCoordinate geoCoordinate = new GeoCoordinate(longitude, latitude);
        List<GeoRadiusResponse> list = RedisUtil.geoRadius("restaurant", geoCoordinate, radius);
        if (list == null || list.size() == 0) {
            return AjaxResult.success(null);
        }
        List<String> idList = list.stream().map(GeoRadiusResponse -> GeoRadiusResponse.getMemberByString()).collect(Collectors.toList());
        String[] ids = idList.stream().toArray(String[]::new);
        SysUser user = userService.selectUserById(userId);
        if (user == null) {
            return AjaxResult.error("未获取到当前用户信息");
        }
        String sqlString = BaseUtil.dataScopeFilter(user);
        RestaurantQueryData queryData = new RestaurantQueryData();
        queryData.setSqlString(sqlString);
        queryData.setRestaurantIds(ids);
        List<Restaurant> restaurantList = restaurantService.selectList(queryData);
        List<RestaurantVo> restaurantVos = new ArrayList<>();
        for (Restaurant restaurant : restaurantList) {
            RestaurantVo restaurantVo = new RestaurantVo();
            BeanUtils.copyProperties(restaurant, restaurantVo);
            String colorStatus = "";
            if (restaurant.getStatus() != null && restaurant.getStatus() == 1) {
                colorStatus = RestaurantConstant.CLOSE;
            } else {
                boolean isRecovered = BusinessUtil.isRecovered(restaurant);
                if (isRecovered) {
                    colorStatus = RestaurantConstant.IS_RECOVERYD;
                } else {
                    colorStatus = RestaurantConstant.UN_RECOVERY;
                }
            }
            restaurantVo.setColorStatus(colorStatus);
            restaurantVos.add(restaurantVo);
        }
        return AjaxResult.success(restaurantVos);
    }
}