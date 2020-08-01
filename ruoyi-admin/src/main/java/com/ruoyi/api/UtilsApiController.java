package com.ruoyi.api;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ruoyi.catering.domain.*;
import com.ruoyi.catering.service.*;
import com.ruoyi.catering.utils.BaseUtil;
import com.ruoyi.catering.utils.BusinessUtil;
import com.ruoyi.catering.vo.RestaurantVo;
import com.ruoyi.catering.vo.UserData;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
@Api(value = "接口对接", tags = {"接口对接"})
public class UtilsApiController {
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private IWarnMsgService warnMsgService;
    @Autowired
    private IDiningTypeService diningTypeService;
    @Autowired
    private ServerConfig serverConfig;
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

    //用户登录
    @PostMapping("/checkLogin")
    @ResponseBody
    public AjaxResult checkLogin(String loginName, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password, false);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            SysUser user = ShiroUtils.getSysUser();
            UserData userData = new UserData();
            userData.setUserId(user.getUserId());
            userData.setLoginName(user.getLoginName());
            userData.setUserName(user.getUserName());
            userData.setPhonenumber(user.getPhonenumber());
            userData.setAvatar(user.getAvatar());
            List<SysRole> roles = user.getRoles();
            if (roles == null || roles.size() == 0) {
                return AjaxResult.error("未知角色，无法登陆");
            }
            if (roles.get(0).getRoleName().contains("回收")) {
                userData.setType(2);
                userData.setCarNo(user.getRemark());
            } else {
                userData.setType(1);
            }
            return AjaxResult.success(userData);
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return AjaxResult.error(msg);
        }
    }

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

    //登陆后首页回收情况
    @GetMapping(value = "/recoveryCounts")
    public AjaxResult recoveryCounts(Long userId) {
        SysUser sysUser = userService.selectUserById(userId);
        String sqlString = BaseUtil.dataScopeFilter(sysUser);
        List<Restaurant> restaurants = restaurantService.canRecycle(sqlString, null, "");
//        List<Restaurant> restaurants = restaurantService.canRecycle(userId, null, "");
        int unRecoveredCount = 0;
        int recoveredCount = 0;
        int closedCount = 0;
        List<String> noticeMsgs = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getStatus() != null && restaurant.getStatus() == 1) {
                closedCount++;
                continue;
            }
            boolean isRecovered = BusinessUtil.isRecovered(restaurant);
            if (isRecovered) {
                recoveredCount++;
            } else {
                unRecoveredCount++;
                noticeMsgs.add(restaurant.getName() + "已超过回收时间，请尽快去回收！");
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("noticeMsgs", noticeMsgs);
        jsonObject.put("unRecoveredCount", unRecoveredCount);
        jsonObject.put("recoveredCount", recoveredCount);
        jsonObject.put("closedCount", closedCount);
        return AjaxResult.success(jsonObject);
    }

    //登陆后首页检查情况
    @GetMapping(value = "/checkCounts")
    public AjaxResult checkCounts(Long userId) {
        SysUser sysUser = userService.selectUserById(userId);
        String sqlString = BaseUtil.dataScopeFilter(sysUser);
        List<Restaurant> restaurants = restaurantService.canRecycle(sqlString, null, "");
//        List<Restaurant> restaurants = restaurantService.canRecycle(userId, null, "");
        int unCheckedCount = 0;
        int checkedCount = 0;
        int closedCount = 0;
        List<String> noticeMsgs = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getStatus() == 1) {
                closedCount++;
                continue;
            }
            boolean isChecked = BusinessUtil.isChecked(restaurant);
            if (isChecked) {
                checkedCount++;
            } else {
                unCheckedCount++;
                noticeMsgs.add(restaurant.getName() + "已超过检查时间，请尽快去检查！");
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("noticeMsgs", noticeMsgs);
        jsonObject.put("unCheckedCount", unCheckedCount);
        jsonObject.put("checkedCount", checkedCount);
        jsonObject.put("closedCount", closedCount);
        return AjaxResult.success(jsonObject);
    }

    //扫一扫上报
    @RequestMapping(value = "/scan")
    public AjaxResult scan(Long userId, Long restaurantId) {
        SysUser sysUser = userService.selectUserById(userId);
        String sqlString = BaseUtil.dataScopeFilter(sysUser);
        List<Restaurant> restaurants = restaurantService.canRecycle(sqlString, restaurantId, "");
//        List<Restaurant> restaurants = restaurantService.canRecycle(userId, restaurantId, "");
        if (restaurants == null || restaurants.size() != 1) {
            return AjaxResult.error("未查询到餐馆信息");
        }
        Restaurant restaurant = restaurants.get(0);
        if (restaurant.getStatus().equals("1")) {
            return AjaxResult.error("餐馆为停业状态");
        }
        return AjaxResult.success(restaurant);
//        SysDept dept = deptService.selectDeptById(restaurant.getDeptId());
//        RestaurantVo restaurantVo = new RestaurantVo();
//        BeanUtils.copyProperties(restaurant, restaurantVo);
//        restaurantVo.setDept(dept);
//        return AjaxResult.success(restaurantVo);
    }

    //回收类型
    @GetMapping(value = "/getGarbageList")
    public AjaxResult getGarbageList() {
        List<Garbage> garbageList = garbageService.selectGarbageList(new Garbage());
        return AjaxResult.success(garbageList);
    }

    //区域列表
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

    //上传图片
    @RequestMapping(value = "/uploadImg")
    public AjaxResult uploadImg(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        if (file == null) {
            return AjaxResult.error("上传失败");
        }
        // 上传文件路径
        String filePath = Global.getUploadPath();
        String fileName = FileUploadUtils.upload(filePath, file);
        String url = serverConfig.getUrl() + fileName;
        return AjaxResult.success("上传成功", url);
    }

    @RequestMapping(value = "/createQrCode")
    public AjaxResult createQrCode(String restaurantIds) {
        String[] ids = restaurantIds.split(",");
        for (String id : ids) {
            Long restaurantId = Long.parseLong(id);
            Restaurant restaurant = restaurantService.selectRestaurantById(restaurantId);
            if (restaurant == null) {
                return AjaxResult.error("未查询到id为" + id + "的餐馆信息");
            }
            SysDept dept = deptService.selectDeptById(restaurant.getDeptId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("restaurantId", restaurantId);
            jsonObject.put("name", restaurant.getName());
            jsonObject.put("appkey", "df33cb8a73a3ae45749bd1ac6ddeb5e4");
            String content = jsonObject.toJSONString();
            try {
                content = new String(content.getBytes("UTF-8"), "iso-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
//                getLogoQRCode(content, restaurant.getShortName(), restaurant.getName(), dept.getDeptName().replace("街道", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return AjaxResult.success("二维码生成成功");
    }


//    @GetMapping(value = "/getOpenid")
//    public JSONObject getOpenid(String js_code) {
//        String appid = "wx303943768745023b";
//        String secret = "dd4392f5e24592c90a87979b0936a5ff";
//        String grant_type = "authorization_code";
//        String result = HttpUtils.sendGet("https://api.weixin.qq.com/sns/jscode2session", "appid=" + appid + "&secret=" + secret + "&js_code=" + js_code + "&grant_type=" + grant_type);
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        return jsonObject;
//    }
//
//    @GetMapping(value = "/hasBind")
//    public AjaxResult hasBind(String openid) {
//        Restaurant restaurant = new Restaurant();
//        restaurant.setOpenid(openid);
//        List<Restaurant> restaurantList = restaurantService.selectRestaurantList(restaurant);
//        if (restaurantList == null || restaurantList.size() == 0) {
//            return AjaxResult.error("未绑定", openid);
//        }
//        restaurant = restaurantList.get(0);
//        return AjaxResult.success("获取成功", restaurant);
//    }
//
//    @GetMapping(value = "/getQrCode")
//    //获取二维码图片
//    public AjaxResult getQrCode(Long id, HttpServletRequest request) {
//        Restaurant restaurant = restaurantService.selectRestaurantById(id);
//        if (StringUtils.isEmpty(restaurant.getQrCode())) {
//            return AjaxResult.error("暂无二维码");
//        }
//        String tmpPath = request.getServletContext().getRealPath("/static") + File.separator + "temporary";
//        File tmpFilePath = new File(tmpPath);
//        if (!tmpFilePath.exists()) {
//            tmpFilePath.mkdirs();
//        }
//        try {
//            String img = restaurant.getQrCode();
//            int one = img.lastIndexOf("/");
//            String imgName = img.substring((one + 1), img.length());
//            DownloadImage.download(img, imgName, tmpPath);
//            return AjaxResult.success("获取成功", imgName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "/hasRead")
//    //更新信息是否已读
//    public AjaxResult hasRead(WarnMsg warnMsg) {
//        warnMsgService.updateWarnMsg(warnMsg);
//        return AjaxResult.success("更新成功");
//    }
//
//    @GetMapping(value = "/getDiningTypeList")
//    //获取垃圾分类列表
//    public JSONObject getDiningTypeList() {
//        JSONObject jsonObject = new JSONObject();
//        List<DiningType> typeList = diningTypeService.selectDiningTypeList(new DiningType());
//        String[] typeArray = new String[typeList.size()];
//        int i = 0;
//        for (DiningType dt : typeList) {
//            typeArray[i] = dt.getName();
//            i++;
//        }
//        jsonObject.put("code", 0);
//        jsonObject.put("msg", "获取成功");
//        jsonObject.put("typeList", typeList);
//        jsonObject.put("typeArray", typeArray);
//        return jsonObject;
//    }
//

//    @ResponseBody
//    @RequestMapping("md5")
//    public String EncoderByMd5(String str) {
//        try {
//            // 得到一个信息摘要器
//            MessageDigest digest = MessageDigest.getInstance("md5");
//            byte[] result = digest.digest(str.getBytes());
//            StringBuffer buffer = new StringBuffer();
//            // 把每一个byte 做一个与运算 0xff;
//            for (byte b : result) {
//                // 与运算
//                int number = b & 0xff;
//                // 加盐
//                String str1 = Integer.toHexString(number);
//                if (str1.length() == 1) {
//                    buffer.append("0");
//                }
//                buffer.append(str1);
//            }
//            // 标准的md5加密后的结果
//            return buffer.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
}