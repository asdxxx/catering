package com.ruoyi.api;

import com.github.pagehelper.PageInfo;
import com.ruoyi.catering.data.PageData;
import com.ruoyi.catering.domain.CheckRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.ICheckRecordService;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.utils.BusinessUtil;
import com.ruoyi.catering.utils.MyPageUtil;
import com.ruoyi.catering.utils.RedisUtil;
import com.ruoyi.catering.vo.CheckRecordVo;
import com.ruoyi.catering.data.CheckQueryData;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-23 16:06
 */
@Slf4j
@RestController
@RequestMapping("/api/checkRecord")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "检查上报接口")
public class CheckRecordApiController {
    @Autowired
    private ICheckRecordService checkRecordService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IRestaurantService restaurantService;

    @ApiOperation("检查上报")
    @PostMapping(value = "save")
    public AjaxResult save(CheckRecord checkRecord) {
        Restaurant restaurant = restaurantService.selectRestaurantById(checkRecord.getRestaurantId());
        if (restaurant == null) {
            return AjaxResult.error("未查询到餐馆信息");
        }
        if (BusinessUtil.updateCoordinates(restaurant, checkRecord.getLongitude(), checkRecord.getLatitude())) {
            DecimalFormat df = new DecimalFormat("0.000000");
            restaurant.setLongitude(df.format(Double.parseDouble(checkRecord.getLongitude())));
            restaurant.setLatitude(df.format(Double.parseDouble(checkRecord.getLatitude())));
            restaurantService.updateRestaurant(restaurant);

            Jedis redis = RedisUtil.getJedis();
            String memberName = restaurant.getRestaurantId() + "";
            GeoCoordinate geoCoordinate = new GeoCoordinate(Long.parseLong(restaurant.getLongitude()), Long.parseLong(restaurant.getLatitude()));
            Map add = new HashMap();
            add.put(memberName, geoCoordinate);
            redis.geoadd("restaurant", add);
        }
        checkRecord.setSize(restaurant.getSize());
        checkRecord.setCheckDate(new Date());
        int result = checkRecordService.insertCheckRecord(checkRecord);
        if (result <= 0) {
            return AjaxResult.error("上报失败");
        }
        return AjaxResult.success();
    }

    @ApiOperation("获取商户检查详情")
    @GetMapping(value = "/getListByRestaurantId")
    public AjaxResult getListByRestaurantId(Long restaurantId) {
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setRestaurantId(restaurantId);
        List<CheckRecord> checkRecordList = checkRecordService.selectCheckRecordList(checkRecord);
        List<CheckRecordVo> checkRecordVos = new ArrayList<>();
        for (CheckRecord cr : checkRecordList) {
            CheckRecordVo checkRecordVo = new CheckRecordVo();
            BeanUtils.copyProperties(cr, checkRecordVo);
            SysUser user = userService.selectUserById(cr.getUserId());
            checkRecordVo.setUser(user);
            checkRecordVos.add(checkRecordVo);
        }
        return AjaxResult.success(checkRecordVos);
    }

    @ApiOperation("获取检查记录详情")
    @GetMapping(value = "/getDataById")
    public AjaxResult getDataById(Long recordId) {
        CheckRecord checkRecord = checkRecordService.selectCheckRecordById(recordId);
        return AjaxResult.success(checkRecord);
    }

    @ApiOperation("获取检查列表")
    @GetMapping(value = "/queryList")
    public AjaxResult queryList(CheckQueryData queryData) {
        PageDomain pageDomain = MyPageUtil.startPage();
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setRestaurantId(queryData.getRestaurantId());
        checkRecord.setUserId(queryData.getUserId());
        Map<String, Object> params = new HashMap<>();
        params.put("restaurantName", queryData.getRestaurantName());
        params.put("beginCheckDate", queryData.getStartDate());
        params.put("endCheckDate", queryData.getEndDate());
        if (StringUtils.isEmpty(pageDomain.getOrderBy())) {
            params.put("dataScope", "order by check_date desc");
        }
        checkRecord.setParams(params);
        List<CheckRecord> checkRecordList = checkRecordService.selectCheckRecordList(checkRecord);
        List<CheckRecordVo> checkRecordVos = new ArrayList<>();
        for (CheckRecord cr : checkRecordList) {
            CheckRecordVo checkRecordVo = new CheckRecordVo();
            BeanUtils.copyProperties(cr, checkRecordVo);
            SysUser user = userService.selectUserById(cr.getUserId());
            checkRecordVo.setUser(user);
            Restaurant restaurant = restaurantService.selectRestaurantById(cr.getRestaurantId());
            checkRecordVo.setRestaurant(restaurant);
            checkRecordVos.add(checkRecordVo);
        }

        PageData pageData = new PageData(checkRecordVos, pageDomain.getPageNum(), pageDomain.getPageSize(), (int) new PageInfo(checkRecordList).getTotal());
        return AjaxResult.success(pageData);
    }
}
