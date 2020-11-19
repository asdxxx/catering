package com.ruoyi.api;

import com.github.pagehelper.PageInfo;
import com.ruoyi.catering.data.PageData;
import com.ruoyi.catering.domain.Garbage;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.IGarbageService;
import com.ruoyi.catering.service.IRecoveryRecordService;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.utils.BusinessUtil;
import com.ruoyi.catering.utils.MyPageUtil;
import com.ruoyi.catering.utils.RedisUtil;
import com.ruoyi.catering.vo.RecoveryRecordVo;
import com.ruoyi.catering.data.RecoveryReportData;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-13 08:50
 */
@Slf4j
@RestController
@RequestMapping("/api/recoveryRecord")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "回收记录接口")
public class RecoveryRecordApiController {
    @Autowired
    private IRecoveryRecordService recoveryRecordService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IGarbageService garbageService;
    @Autowired
    private IRestaurantService restaurantService;

    @ApiOperation("回收上报")
    @PostMapping(value = "save")
    public AjaxResult save(RecoveryReportData recoveryReportData) {
        Restaurant restaurant = restaurantService.selectRestaurantById(recoveryReportData.getRestaurantId());
        if (restaurant == null) {
            return AjaxResult.error("未查询到餐馆信息");
        }
        if (BusinessUtil.updateCoordinates(restaurant, recoveryReportData.getLongitude(), recoveryReportData.getLatitude())) {
            DecimalFormat df = new DecimalFormat("0.000000");
            restaurant.setLongitude(df.format(Double.parseDouble(recoveryReportData.getLongitude())));
            restaurant.setLatitude(df.format(Double.parseDouble(recoveryReportData.getLatitude())));
            restaurantService.updateRestaurant(restaurant);

            Jedis redis = RedisUtil.getJedis();
            String memberName = restaurant.getRestaurantId() + "";
            GeoCoordinate geoCoordinate = new GeoCoordinate(Long.parseLong(restaurant.getLongitude()), Long.parseLong(restaurant.getLatitude()));
            Map add = new HashMap();
            add.put(memberName, geoCoordinate);
            redis.geoadd("restaurant", add);
        }
        Integer count = recoveryReportData.getReportCount();
        if (count == null || count <= 0) {
            return AjaxResult.error("请上报数据");
        }
        if (count == 1) {
            RecoveryRecord recoveryRecord = recoveryReportData.toRecoveryRecord();
            if (recoveryRecord.getWeight() <= 0) {
                return AjaxResult.error("上报数量不能为空");
            }
            recoveryRecord.setRecoveryDate(new Date());
            recoveryRecord.setSize(restaurant.getSize());
            int result = recoveryRecordService.insertRecoveryRecord(recoveryRecord);
            if (result <= 0) {
                return AjaxResult.error("上报失败");
            }
        } else {
            List<RecoveryRecord> recoveryRecordList = recoveryReportData.toRecoveryRecordList();
            boolean flag = false;
            for (RecoveryRecord rr : recoveryRecordList) {
                if (rr.getWeight() <= 0) {
                    continue;
                }
                flag = true;
                rr.setRecoveryDate(new Date());
                rr.setSize(restaurant.getSize());
                int result = recoveryRecordService.insertRecoveryRecord(rr);
                if (result <= 0) {
                    return AjaxResult.error("上报失败");
                }
            }
            if (!flag) {
                return AjaxResult.error("上报数量不能都为空");
            }
        }
        return AjaxResult.success("上报成功");
    }

    @ApiOperation("获取商户回收列表")
    @GetMapping(value = "/getListByRestaurantId")
    public AjaxResult getListByRestaurantId(Long restaurantId) {
        RecoveryRecord recoveryRecord = new RecoveryRecord();
        recoveryRecord.setRestaurantId(restaurantId);
        List<RecoveryRecord> recoveryRecordList = recoveryRecordService.selectRecoveryRecordList(recoveryRecord);
        List<RecoveryRecordVo> recoveryRecordVos = new ArrayList<>();
        for (RecoveryRecord rr : recoveryRecordList) {
            RecoveryRecordVo recoveryRecordVo = new RecoveryRecordVo();
            BeanUtils.copyProperties(rr, recoveryRecordVo);
            SysUser user = userService.selectUserById(rr.getUserId());
            recoveryRecordVo.setUser(user);
            Garbage garbage = garbageService.selectGarbageById(rr.getGarbageId());
            recoveryRecordVo.setGarbage(garbage);
            recoveryRecordVos.add(recoveryRecordVo);
        }
        return AjaxResult.success(recoveryRecordVos);
    }

    @ApiOperation("获取回收列表")
    @GetMapping(value = "/queryList")
    public AjaxResult queryList(Long restaurantId) {
        PageDomain pageDomain = MyPageUtil.startPage();
        RecoveryRecord recoveryRecord = new RecoveryRecord();
        recoveryRecord.setRestaurantId(restaurantId);
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isEmpty(pageDomain.getOrderBy())) {
            params.put("dataScope", "order by recovery_date desc");
        }
        recoveryRecord.setParams(params);
        List<RecoveryRecord> recoveryRecordList = recoveryRecordService.selectRecoveryRecordList(recoveryRecord);
        List<RecoveryRecordVo> recoveryRecordVos = new ArrayList<>();
        for (RecoveryRecord rr : recoveryRecordList) {
            RecoveryRecordVo recoveryRecordVo = new RecoveryRecordVo();
            BeanUtils.copyProperties(rr, recoveryRecordVo);
            SysUser user = userService.selectUserById(rr.getUserId());
            recoveryRecordVo.setUser(user);
            Garbage garbage = garbageService.selectGarbageById(rr.getGarbageId());
            recoveryRecordVo.setGarbage(garbage);
            Restaurant restaurant = restaurantService.selectRestaurantById(rr.getRestaurantId());
            recoveryRecordVo.setRestaurant(restaurant);
            recoveryRecordVos.add(recoveryRecordVo);
        }
        PageData pageData = new PageData(recoveryRecordVos, pageDomain.getPageNum(), pageDomain.getPageSize(), (int) new PageInfo(recoveryRecordList).getTotal());
        return AjaxResult.success(pageData);
    }

    @ApiOperation("获取回收详情")
    @CrossOrigin
    @GetMapping(value = "/detailById")
    public AjaxResult detailById(Long recordId) {
        RecoveryRecord recoveryRecord = recoveryRecordService.selectRecoveryRecordById(recordId);
        RecoveryRecordVo recoveryRecordVo = new RecoveryRecordVo();
        BeanUtils.copyProperties(recoveryRecord, recoveryRecordVo);
        SysUser user = userService.selectUserById(recoveryRecord.getUserId());
        recoveryRecordVo.setUser(user);
        Garbage garbage = garbageService.selectGarbageById(recoveryRecord.getGarbageId());
        recoveryRecordVo.setGarbage(garbage);
        Restaurant restaurant = restaurantService.selectRestaurantById(recoveryRecord.getRestaurantId());
        recoveryRecordVo.setRestaurant(restaurant);
        return AjaxResult.success(recoveryRecordVo);
    }
}