package com.ruoyi.api;

import com.ruoyi.catering.domain.CheckRecord;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.ICheckRecordService;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.vo.CheckRecordVo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Api(value = "接口对接", tags = {"接口对接"})
public class CheckRecordApiController {
    @Autowired
    private ICheckRecordService checkRecordService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IRestaurantService restaurantService;

    @PostMapping(value = "save")
    public AjaxResult save(CheckRecord checkRecord) {
        Restaurant restaurant = restaurantService.selectRestaurantById(checkRecord.getRestaurantId());
        if (restaurant != null && (StringUtils.isEmpty(restaurant.getLongitude()) || StringUtils.isEmpty(restaurant.getLatitude()))) {
            restaurant.setLongitude(checkRecord.getLongitude());
            restaurant.setLatitude(checkRecord.getLatitude());
            restaurantService.updateRestaurant(restaurant);
        }
        checkRecord.setCheckDate(new Date());
//        checkRecord.setCreateBy(checkRecord.getUserId().toString());
        int result = checkRecordService.insertCheckRecord(checkRecord);
        if (result <= 0) {
            return AjaxResult.error("上报失败");
        }
        return AjaxResult.success();
    }

    //获取商户检查详情
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

    //根据记录id查询检查详情
    @GetMapping(value = "/getDataById")
    public AjaxResult getDataById(Long recordId) {
        CheckRecord checkRecord = checkRecordService.selectCheckRecordById(recordId);
        return AjaxResult.success(checkRecord);
    }
}
