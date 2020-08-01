package com.ruoyi.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.catering.domain.*;
import com.ruoyi.catering.service.IDiningTypeService;
import com.ruoyi.catering.service.IGasTypeService;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.service.IWarnMsgService;
import com.ruoyi.catering.utils.BaseUtil;
import com.ruoyi.catering.utils.BusinessUtil;
import com.ruoyi.catering.vo.RestaurantVo;
import com.ruoyi.catering.vo.WarnMsgVo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysRoleDeptMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
@Api(value = "接口对接", tags = {"接口对接"})
public class RestaurantApiController {
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private IDiningTypeService diningTypeService;
    @Autowired
    private IGasTypeService gasTypeService;
    @Autowired
    private IWarnMsgService warnMsgService;
    @Autowired
    private ISysUserService userService;

    //商户列表
    @GetMapping(value = "/getListByUserId")
    public AjaxResult getListByUserId(Long userId) {
        SysUser sysUser = userService.selectUserById(userId);
        String sqlString = BaseUtil.dataScopeFilter(sysUser);
        List<Restaurant> restaurants = restaurantService.canRecycle(sqlString, null, "");
        return AjaxResult.success(restaurants);
    }

    //模糊查询
    @GetMapping(value = "/fuzzyQuery")
    public AjaxResult fuzzyQuery(Long userId, String name) {
        SysUser sysUser = userService.selectUserById(userId);
        String sqlString = BaseUtil.dataScopeFilter(sysUser);
        List<Restaurant> restaurants = restaurantService.canRecycle(sqlString, null, name);
//        List<Restaurant> restaurants = restaurantService.canRecycle(userId, null, name);
        return AjaxResult.success(restaurants);
    }

    //根据商户id查询商户
    @GetMapping(value = "/getDataById")
    public AjaxResult getDataById(Long restaurantId) {
        Restaurant restaurant = restaurantService.selectRestaurantById(restaurantId);
        return AjaxResult.success(restaurant);
    }

    @RequestMapping(value = "/edit")
    //修改店铺信息
    public AjaxResult edit(Restaurant restaurant) {
        int result = restaurantService.updateRestaurant(restaurant);
        if (result <= 0) {
            return AjaxResult.error("修改失败");
        }
        return AjaxResult.success("修改成功");
    }

//    @GetMapping(value = "/getListByName")
//    //模糊查询餐馆列表
//    public AjaxResult getListByName(String name) {
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName(name);
//        List<Restaurant> restaurantList = restaurantService.selectRestaurantList(restaurant);
//        return AjaxResult.success(restaurantList);
//    }
//
//    @RequestMapping(value = "/bind")
//    //微信绑定商户
//    public AjaxResult bind(String openid, String name) {
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName(name);
//        List<Restaurant> restaurantList = restaurantService.selectRestaurantList(restaurant);
//        if (restaurantList == null || restaurantList.size() == 0) {
//            return AjaxResult.error("未查找到商铺信息");
//        }
//        if (restaurantList.size() > 1) {
//            return AjaxResult.error("存在重名店铺，请联系管理员");
//        }
//        restaurant = restaurantList.get(0);
//        if (StringUtils.isNotEmpty(restaurant.getOpenid())) {
//            return AjaxResult.error("该商铺信息已被绑定");
//        }
//        restaurant.setOpenid(openid);
//        int result = restaurantService.updateRestaurant(restaurant);
//        if (result <= 0) {
//            return AjaxResult.error("信息更新失败");
//        }
//        return AjaxResult.success(restaurant);
//    }
//
//    @GetMapping("/getRedReason")
//    //餐饮单位存在的问题
//    public AjaxResult getRedReason(Long restaurantId) {
//        Restaurant restaurant = restaurantService.selectRestaurantById(restaurantId);
//        JSONArray jsonArray = new JSONArray();
//        String isinfo = BusinessUtil.isinfo(restaurant);
//        if ("red".equals(isinfo)) {
//            jsonArray.add("商户信息未填写完整");
//        }
//        String isrecovery = BusinessUtil.isGetSumWeight(restaurant);
//        if ("red".equals(isrecovery)) {
//            jsonArray.add("厨余或废油在规定时间内未回收");
//        }
//        String isWarn = BusinessUtil.getWarning(restaurantId);
//        if ("red".equals(isWarn)) {
//            jsonArray.add("餐饮单位存在投诉信息");
//        }
//        return AjaxResult.success(jsonArray);
//    }
//
//    @GetMapping(value = "/getDetailInfo")
//    //餐馆详情查询
//    public AjaxResult getDetailInfo(Long id) {
//        Restaurant restaurant = restaurantService.selectRestaurantById(id);
//        if (restaurant == null) {
//            return AjaxResult.error("未查询到餐馆信息");
//        }
//        RestaurantVo restaurantVo = new RestaurantVo();
//        BeanUtils.copyProperties(restaurant, restaurantVo);
//        DiningType diningType = diningTypeService.selectDiningTypeById(restaurant.getDiningTypeId());
//        restaurantVo.setDiningType(diningType);
//        if (StringUtils.isNotBlank(restaurant.getGasTypeId())) {
//            String[] gasTypeIds = restaurant.getGasTypeId().split(",");
//            List<GasType> gasTypeList = new ArrayList<>();
//            String gasTypeNames = "";
//            for (String g : gasTypeIds) {
//                GasType gasType = gasTypeService.selectGasTypeById(Long.parseLong(g));
//                gasTypeNames += gasType.getName() + ",";
//                gasTypeList.add(gasType);
//            }
//            gasTypeNames = gasTypeNames.substring(0, gasTypeNames.length() - 1);
//            restaurantVo.setGasTypeNames(gasTypeNames);
//            restaurantVo.setGasTypes(gasTypeList);
//        }
//        if (restaurantVo.getDiningTypeId() != null) {
//            List<DiningType> typeList = diningTypeService.selectDiningTypeList(new DiningType());
//            for (int i = 0; i < typeList.size(); i++) {
//                if (restaurantVo.getDiningTypeId() == typeList.get(i).getTypeId()) {
//                    restaurantVo.setRestaurantKindsIndex(i);
//                    break;
//                }
//            }
//        }
//        return AjaxResult.success(restaurantVo);
//    }
//
//    @RequestMapping(value = "/unbound")
//    //解除绑定
//    public AjaxResult unbound(String openid, Long restaurantId) {
//        Restaurant restaurant = restaurantService.selectRestaurantById(restaurantId);
//        if (restaurant == null) {
//            return AjaxResult.error("未查询到店铺信息");
//        }
//        if (StringUtils.isEmpty(restaurant.getOpenid()) || !restaurant.getOpenid().equals(openid)) {
//            return AjaxResult.error("绑定信息错误");
//        }
//        restaurant.setOpenid("");
//        int result = restaurantService.updateRestaurant(restaurant);
//        if (result <= 0) {
//            return AjaxResult.error("解除绑定失败");
//        }
//        return AjaxResult.success("解除绑定成功");
//    }
//
//    @GetMapping(value = "/getGasTypeList")
//    //获取垃圾分类列表
//    public AjaxResult getGasTypeList(Long restaurantId) {
//        List<GasType> typeList = gasTypeService.selectGasTypeList(new GasType());
//        if (restaurantId != null) {
//            Restaurant restaurant = restaurantService.selectRestaurantById(restaurantId);
//            if (StringUtils.isNotEmpty(restaurant.getGasTypeId())) {
//                String[] gasTypeIds = restaurant.getGasTypeId().split(",");
//                for (String g : gasTypeIds) {
//                    for (GasType gt : typeList) {
//                        if (g.equals(gt.getTypeId().toString())) {
//                            gt.setHasChecked("true");
//                        }
//                    }
//                }
//            }
//        }
//
//        for (GasType gt : typeList) {
//            if (StringUtils.isEmpty(gt.getHasChecked())) {
//                gt.setHasChecked("false");
//            }
//        }
//        return AjaxResult.success(typeList);
//    }
//
//    /**
//     * 获取发送消息中队
//     */
//    @GetMapping(value = "/getMsgList1")
//    public JSONObject getMsgList1(Long id, Integer type) {
//        JSONObject jsonObject = new JSONObject();
//        WarnMsg warnMsg = new WarnMsg();
//        //中队人员信息
//        warnMsg.setRestaurantId(id);
//        warnMsg.setType(type);
//        warnMsg.setMsgType(0);
//        List<WarnMsg> warnMsgs = warnMsgService.selectWarnMsgList(warnMsg);
//        jsonObject.put("success", "true");
//        jsonObject.put("msgList1", warnMsgs);
//        jsonObject.put("msgList2", null);
//        jsonObject.put("msg", "获取成功");
//        return jsonObject;
//    }
}