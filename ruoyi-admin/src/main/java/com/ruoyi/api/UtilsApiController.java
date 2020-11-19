package com.ruoyi.api;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.catering.constants.UserConstant;
import com.ruoyi.catering.domain.*;
import com.ruoyi.catering.service.*;
import com.ruoyi.catering.utils.BaseUtil;
import com.ruoyi.catering.utils.BusinessUtil;
import com.ruoyi.catering.data.UserData;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-07-12 19:04
 */
@Slf4j
@RestController
@RequestMapping("/api/utils")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "通用接口")
public class UtilsApiController {
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private IRecoveryRecordService recoveryRecordService;
    @Autowired
    private IGarbageService garbageService;
    @Autowired
    private SysPasswordService passwordService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ICheckRecordService checkRecordService;

    @ApiOperation("用户登录")
    @PostMapping("/checkLogin")
    @ResponseBody
    public AjaxResult checkLogin(String loginName, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password, false);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            SysUser user = ShiroUtils.getSysUser();
            String type = user.getType();
//            if (!"1".equals(type) && !"2".equals(type)) {
//                return AjaxResult.error("未知角色，无法登陆");
//            }
            UserData userData = new UserData();
            userData.setUserId(user.getUserId());
//            if (!"2".equals(type)) {
//                type = "1";
//            }
            userData.setType(Integer.parseInt(type));
            userData.setLoginName(user.getLoginName());
            userData.setUserName(user.getUserName());
            userData.setPhonenumber(user.getPhonenumber());
            userData.setAvatar(user.getAvatar());
//            List<SysRole> roles = user.getRoles();
//            if (roles == null || roles.size() == 0) {
//                return AjaxResult.error("未知角色，无法登陆");
//            }
//            if (roles.get(0).getRoleKey().contains("recycle")) {
//                userData.setType(2);
//                userData.setCarNo(user.getRemark());
//            } else {
//                userData.setType(1);
//            }
            return AjaxResult.success(userData);
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return AjaxResult.error(msg);
        }
    }

    @ApiOperation("重置密码")
    @PostMapping("/resetPassword")
    @ResponseBody
    public AjaxResult resetPassword(String loginName, String oldPassword, String newPassword) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, oldPassword, false);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            SysUser user = userService.selectUserByLoginName(loginName);
            user.setSalt(ShiroUtils.randomSalt());
            user.setPassword(passwordService.encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
            if (userService.resetUserPwd(user) > 0) {
                if (ShiroUtils.getUserId().longValue() == user.getUserId().longValue()) {
                    ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
                }
                return AjaxResult.success("密码修改成功");
            }
            return AjaxResult.error("密码修改失败");
        } catch (AuthenticationException e) {
            String msg = "密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return AjaxResult.error(msg);
        }
    }

    @ApiOperation("首页回收情况")
    @GetMapping(value = "/recoveryCounts")
    public AjaxResult recoveryCounts(Long userId) {
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
        SysUser sysUser = userService.selectUserById(userId);
        String sqlString = BaseUtil.dataScopeFilter(sysUser);
        List<Restaurant> restaurants = restaurantService.canRecycle(sqlString, null, "");
        String ids = "";
        for (Restaurant restaurant : restaurants) {
            ids += restaurant.getRestaurantId() + ",";
        }
        if (ids.length() > 0) {
            ids = ids.substring(0, ids.length() - 1);
        }
        List<RecoveryRecord> recoveryRecords = recoveryRecordService.selectListByRestaurantId(ids);

        int unRecoveredCount = 0;
        int recoveredCount = 0;
        int closedCount = 0;
        List<String> noticeMsgs = new ArrayList<>();
        List<Long> hasIds = new ArrayList<>();
        for (RecoveryRecord rr : recoveryRecords) {
//            Restaurant r = restaurantService.selectRestaurantById(rr.getRestaurantId());
//            int size = r.getSize();
//            Date date = size == 2 ? mediumDate : size == 3 ? largeDate : smallDate;
            Integer size = rr.getSize();
            Date date = size == null ? smallDate : size == 2 ? mediumDate : size == 3 ? largeDate : smallDate;
            if (rr.getRecoveryDate().after(date)) {
                recoveredCount++;
                hasIds.add(rr.getRestaurantId());
            }
        }
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getStatus() != null && restaurant.getStatus() == 1) {
                closedCount++;
                continue;
            }
            if (!hasIds.contains(restaurant.getRestaurantId())) {
                unRecoveredCount++;
                if (unRecoveredCount <= 50) {
                    noticeMsgs.add(restaurant.getName() + "已超过回收时间，请尽快去回收！");
                }
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("noticeMsgs", noticeMsgs);
        jsonObject.put("unRecoveredCount", unRecoveredCount);
        jsonObject.put("recoveredCount", recoveredCount);
        jsonObject.put("closedCount", closedCount);
        return AjaxResult.success(jsonObject);
    }

    @ApiOperation("首页检查情况")
    @GetMapping(value = "/checkCounts")
    public AjaxResult checkCounts(Long userId) {
        SysUser sysUser = userService.selectUserById(userId);
        String sqlString = BaseUtil.dataScopeFilter(sysUser);
        List<Restaurant> restaurants = restaurantService.canRecycle(sqlString, null, "");

        String ids = "";
        for (Restaurant restaurant : restaurants) {
            ids += restaurant.getRestaurantId() + ",";
        }
        if (ids.length() > 0) {
            ids = ids.substring(0, ids.length() - 1);
        }
        List<CheckRecord> checkRecords = checkRecordService.selectListByRestaurantId(ids);
        int unCheckedCount = 0;
        int checkedCount = checkRecords.size();
        int closedCount = 0;
        List<String> noticeMsgs = new ArrayList<>();
        List<Long> hasIds = new ArrayList<>();
        for (CheckRecord cr : checkRecords) {
            hasIds.add(cr.getRestaurantId());
        }
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getStatus() == 1) {
                closedCount++;
                continue;
            }
            if (!hasIds.contains(restaurant.getRestaurantId())) {
                unCheckedCount++;
                if (unCheckedCount <= 50) {
                    noticeMsgs.add(restaurant.getName() + "未检查，请尽快去检查！");
                }
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("noticeMsgs", noticeMsgs);
        jsonObject.put("unCheckedCount", unCheckedCount);
        jsonObject.put("checkedCount", checkedCount);
        jsonObject.put("closedCount", closedCount);
        return AjaxResult.success(jsonObject);
    }
//    @ApiOperation("首页检查情况")
//    @GetMapping(value = "/checkCounts")
//    public AjaxResult checkCounts(Long userId) {
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.add(Calendar.DATE, -3);
//        Date smallDate = cal.getTime();
//        cal.add(Calendar.DATE, -3);
//        Date mediumDate = cal.getTime();
//        cal.add(Calendar.DATE, -3);
//        Date largeDate = cal.getTime();
//
//        SysUser sysUser = userService.selectUserById(userId);
//        String sqlString = BaseUtil.dataScopeFilter(sysUser);
//        List<Restaurant> restaurants = restaurantService.canRecycle(sqlString, null, "");
//
//        String ids = "";
//        for (Restaurant restaurant : restaurants) {
//            ids += restaurant.getRestaurantId() + ",";
//        }
//        if (ids.length() > 0) {
//            ids = ids.substring(0, ids.length() - 1);
//        }
//        List<CheckRecord> checkRecords = checkRecordService.selectListByRestaurantId(ids);
//        int unCheckedCount = 0;
//        int checkedCount = 0;
//        int closedCount = 0;
//        List<String> noticeMsgs = new ArrayList<>();
//        List<Long> hasIds = new ArrayList<>();
//        for (CheckRecord cr : checkRecords) {
//            Integer size = cr.getSize();
//            Date date = size == null ? smallDate : size == 2 ? mediumDate : size == 3 ? largeDate : smallDate;
//            if (cr.getCheckDate().after(date)) {
//                checkedCount++;
//                hasIds.add(cr.getRestaurantId());
//            }
//        }
//        for (Restaurant restaurant : restaurants) {
//            if (restaurant.getStatus() == 1) {
//                closedCount++;
//                continue;
//            }
//            if (!hasIds.contains(restaurant.getRestaurantId())) {
//                unCheckedCount++;
//                if (unCheckedCount <= 50) {
//                    noticeMsgs.add(restaurant.getName() + "已超过检查时间，请尽快去检查！");
//                }
//            }
//        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("noticeMsgs", noticeMsgs);
//        jsonObject.put("unCheckedCount", unCheckedCount);
//        jsonObject.put("checkedCount", checkedCount);
//        jsonObject.put("closedCount", closedCount);
//        return AjaxResult.success(jsonObject);
//    }

    @ApiOperation("微信扫一扫")
    @CrossOrigin
    @RequestMapping(value = "/wxScan")
    public AjaxResult wxScan(Long restaurantId) {
        Restaurant restaurant = restaurantService.selectRestaurantById(restaurantId);
        if (restaurant == null) {
            return AjaxResult.error("未查询到商户信息");
        }
        JSONObject jsonObject = new JSONObject();
        boolean isChecked = BusinessUtil.isQualified(restaurantId);
        jsonObject.put("status", isChecked ? "合格" : "不合格");
        jsonObject.put("restaurant", restaurant);
        List<SysUser> userList = userService.selectRecycleByDeptId(restaurant.getDeptId());
        SysUser user = new SysUser();
        if (userList != null && userList.size() > 0) {
            user = userList.get(0);
        }
        jsonObject.put("user", user);
        RecoveryRecord recoveryRecord = new RecoveryRecord();
        recoveryRecord.setRestaurantId(restaurantId);
        List<RecoveryRecord> recoveryRecords = recoveryRecordService.selectRecoveryRecordList(recoveryRecord);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //获取格式化格式
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());         //塞入当前日期
        c.set(Calendar.HOUR_OF_DAY, 0);   //将时分秒设置成0，便于格式获取，若格式的为yyyy-MM-dd则不需要
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.DATE, -6);
        Date time = c.getTime();
        String needtime = format.format(time);
        Map params = new HashMap();
        params.put("beginRecoveryDate",needtime);
        recoveryRecord.setParams(params);
        List<RecoveryRecord> weekRecordList = recoveryRecordService.selectRecoveryRecordList(recoveryRecord);
        jsonObject.put("recoveryRecordList", recoveryRecords);
        jsonObject.put("weekRecordList", weekRecordList);
        return AjaxResult.success(jsonObject);
    }

    @ApiOperation("小程序扫一扫")
    @RequestMapping(value = "/scan")
    public AjaxResult scan(Long userId, Long restaurantId) {
        SysUser sysUser = userService.selectUserById(userId);
        String sqlString = BaseUtil.dataScopeFilter(sysUser);
        List<Restaurant> restaurants = restaurantService.canRecycle(sqlString, restaurantId, "");
        if (restaurants == null || restaurants.size() != 1) {
            return AjaxResult.error("未查询到商户信息");
        }
        Restaurant restaurant = restaurants.get(0);
        if (restaurant.getStatus().equals("1")) {
            return AjaxResult.error("商户为停业状态");
        }
        return AjaxResult.success(restaurant);
    }

    @ApiOperation("回收类型列表")
    @GetMapping(value = "/getGarbageList")
    public AjaxResult getGarbageList() {
        List<Garbage> garbageList = garbageService.selectGarbageList(new Garbage());
        return AjaxResult.success(garbageList);
    }

    @ApiOperation("查询区域列表")
    @GetMapping(value = "/getDeptList")
    public AjaxResult getDeptList(Long userId, String type) {
        SysUser sysUser = userService.selectUserById(userId);
        String sqlString = BaseUtil.dataScopeFilter(sysUser);
        List<SysDept> deptList = deptService.selectDeptListByUserId(sqlString);
        if (StringUtils.isNotEmpty(type) && type.equals("tree")) {
            List<Ztree> ztrees = new ArrayList<Ztree>();
            for (SysDept dept : deptList) {
                if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
                    Ztree ztree = new Ztree();
                    ztree.setId(dept.getDeptId());
                    ztree.setpId(dept.getParentId());
                    ztree.setName(dept.getDeptName());
                    ztree.setTitle(dept.getDeptName());
                    ztrees.add(ztree);
                }
            }
            return AjaxResult.success(ztrees);
        }
        return AjaxResult.success(deptList);
    }

    @ApiOperation("查询区域管理人员列表")
    @GetMapping(value = "/getManagerList")
    public AjaxResult getManagerList() {
        SysUser user = new SysUser();
        user.setType(UserConstant.MANAGER);
        List<SysUser> userList = userService.selectUserList(user);
        return AjaxResult.success(userList);
    }

    @ApiOperation("上传图片")
    @RequestMapping(value = "/uploadImg")
    public AjaxResult uploadImg(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        if (file == null) {
            return AjaxResult.error("上传失败");
        }
        // 上传文件路径
        String filePath = Global.getUploadPath();
        String fileName = FileUploadUtils.upload(filePath, file);
//        String url = serverConfig.getUrl() + fileName;
        String url = "http://www.xiha.work:8900" + fileName;
        return AjaxResult.success("上传成功", url);
    }

    @ApiOperation("测试")
    @RequestMapping(value = "/test")
    public AjaxResult test() {
        List<Restaurant> restaurantList = restaurantService.selectRestaurantList(new Restaurant());
        DecimalFormat df = new DecimalFormat("0.000000");
        for (Restaurant restaurant : restaurantList) {
            String longitude = restaurant.getLongitude();
            String latitude = restaurant.getLatitude();
            if (StringUtils.isEmpty(longitude) || StringUtils.isEmpty(latitude)) {
                continue;
            }
            if (longitude.equals("undefined") || latitude.equals("undefined")) {
                restaurant.setLongitude("");
                restaurant.setLatitude("");
                restaurantService.updateRestaurant(restaurant);
                continue;
            }
//            String a = longitude.split(".")[1];
//            String b = latitude.split(".")[1];
//            if (a.length() <= 6 && b.length() <= 6) {
//                continue;
//            }
            restaurant.setLongitude(df.format(Double.parseDouble(longitude)));
            restaurant.setLatitude(df.format(Double.parseDouble(latitude)));
            restaurantService.updateRestaurant(restaurant);
        }
        return AjaxResult.success(restaurantList.size() + "");
    }

    @ApiOperation("测试")
    @RequestMapping(value = "/test1")
    public AjaxResult test1() {
        List<RecoveryRecord> recoveryRecordList = recoveryRecordService.selectRecoveryRecordList(new RecoveryRecord());
        int result = 0;
        for (RecoveryRecord rr : recoveryRecordList) {
            Restaurant r = restaurantService.selectRestaurantById(rr.getRestaurantId());
            rr.setSize(r.getSize());
            result += recoveryRecordService.updateRecoveryRecord(rr);
        }
        return AjaxResult.success(result);
    }

    @ApiOperation("测试")
    @RequestMapping(value = "/test2")
    public AjaxResult test2() {
        List<CheckRecord> checkRecordList = checkRecordService.selectCheckRecordList(new CheckRecord());
        int result = 0;
        for (CheckRecord cr : checkRecordList) {
            Restaurant r = restaurantService.selectRestaurantById(cr.getRestaurantId());
            cr.setSize(r.getSize());
            result += checkRecordService.updateCheckRecord(cr);
        }
        return AjaxResult.success(result);
    }

    @ApiOperation("测试")
    @RequestMapping(value = "/test3")
    public AjaxResult test3() {
        List<SysUser> userList = userService.selectUserList(new SysUser());
        for (SysUser user : userList) {
            List<SysRole> roles = user.getRoles();

            if (roles != null && roles.size() > 0) {
                SysRole role = roles.get(0);
                if (role.getRoleKey().contains("recycle")) {
                    user.setType("2");
                    userService.updateUserInfo(user);
                    continue;
                } else if (role.getRoleKey().contains("manager")) {
                    user.setType("1");
                    userService.updateUserInfo(user);
                    continue;
                } else if (role.getRoleKey().equals("law")) {
                    user.setType("4");
                    userService.updateUserInfo(user);
                    continue;
                } else if (role.getRoleKey().equals("common")) {
                    user.setType("3");
                    userService.updateUserInfo(user);
                    continue;
                }
            }
        }
        return AjaxResult.success();
    }


    @ApiOperation("测试")
    @RequestMapping(value = "/test4")
    public AjaxResult test4(CheckRecord checkRecord) {
        Restaurant restaurant = restaurantService.selectRestaurantById(checkRecord.getRestaurantId());
        if (restaurant == null) {
            return AjaxResult.error("未查询到餐馆信息");
        }
        DecimalFormat df = new DecimalFormat("0.000000");
        restaurant.setLongitude(df.format(Double.parseDouble(checkRecord.getLongitude())));
        restaurant.setLatitude(df.format(Double.parseDouble(checkRecord.getLatitude())));
        restaurantService.updateRestaurant(restaurant);
        return AjaxResult.success();
    }

}