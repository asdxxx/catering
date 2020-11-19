package com.ruoyi.catering.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.data.RestData;
import com.ruoyi.catering.utils.RedisUtil;
import com.ruoyi.catering.vo.RestaurantExportVo;
import com.ruoyi.catering.data.RestaurantImportData;
import com.ruoyi.catering.vo.RestaurantVo;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.MapDataUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 餐饮单位信息Controller
 *
 * @author lsy
 * @date 2020-07-08
 */
@Controller
@RequestMapping("/catering/restaurant")
public class RestaurantController extends BaseController {
    private String prefix = "catering/restaurant";

    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysUserService userService;

    @RequiresPermissions("catering:restaurant:view")
    @GetMapping()
    public String restaurant() {
        return prefix + "/restaurant";
    }

    /**
     * 查询餐饮单位信息列表
     */
    @DataScope(deptAlias = "d")
    @RequiresPermissions("catering:restaurant:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Restaurant restaurant) {
        startPage();
        List<Restaurant> list = restaurantService.selectRestaurantList(restaurant);

        List<RestaurantVo> restaurantVos = new ArrayList<>();
        for (Restaurant r : list) {
            RestaurantVo restaurantVo = toVo(r);
            restaurantVos.add(restaurantVo);
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(restaurantVos);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
        //return getDataTable(restaurantVos);
    }

    /**
     * 新增餐饮单位信息
     */
    @GetMapping("/add")
    public String add(Model model) {
        return prefix + "/add";
    }

    /**
     * 新增保存餐饮单位信息
     */
    @RequiresPermissions("catering:restaurant:add")
    @Log(title = "餐饮单位信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Restaurant restaurant) {
        restaurant.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(restaurantService.insertRestaurant(restaurant));
    }

    /**
     * 修改餐饮单位信息
     */
    @GetMapping("/edit/{restaurantId}")
    public String edit(@PathVariable("restaurantId") Long restaurantId, ModelMap mmap) {
        Restaurant restaurant = restaurantService.selectRestaurantById(restaurantId);
        RestaurantVo restaurantVo = new RestaurantVo();
        BeanUtils.copyProperties(restaurant, restaurantVo);
        SysDept dept = deptService.selectDeptById(restaurant.getDeptId());
        restaurantVo.setDept(dept);
        mmap.put("restaurant", restaurantVo);
        return prefix + "/edit";
    }

    /**
     * 修改保存餐饮单位信息
     */
    @RequiresPermissions("catering:restaurant:edit")
    @Log(title = "餐饮单位信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Restaurant restaurant) {
        return toAjax(restaurantService.updateRestaurant(restaurant));
    }

    /**
     * 删除餐饮单位信息
     */
    @RequiresPermissions("catering:restaurant:remove")
    @Log(title = "餐饮单位信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(restaurantService.deleteRestaurantByIds(ids));
    }

    /**
     * 查看餐饮单位信息详情
     */
    @RequiresPermissions("catering:restaurant:detail")
    @Log(title = "餐饮单位信息", businessType = BusinessType.OTHER)
    @GetMapping("/detail/{restaurantId}")
    public String detail(@PathVariable("restaurantId") Long restaurantId, ModelMap mmap) {
        Restaurant restaurant = restaurantService.selectRestaurantById(restaurantId);
        RestaurantVo restaurantVo = toVo(restaurant);
        mmap.put("restaurant", restaurantVo);
        return prefix + "/detail";
    }

    /**
     * 更新坐标缓存
     */
    @RequiresPermissions("system:restaurant:remove")
    @Log(title = "更新坐标缓存", businessType = BusinessType.CLEAN)
    @GetMapping("/updateCache")
    @ResponseBody
    public AjaxResult updateCache() {
        Jedis redis = RedisUtil.getJedis();
        redis.del("restaurant");
        List<Restaurant> restaurantList = restaurantService.selectRestaurantList(new Restaurant());
        Map add = new HashMap();
        for (Restaurant restaurant : restaurantList) {
            if (StringUtils.isNotEmpty(restaurant.getLongitude()) && StringUtils.isNotEmpty(restaurant.getLatitude())) {
                String memberName = restaurant.getRestaurantId() + "";
                double longitude = Double.valueOf(restaurant.getLongitude());
                double latitude = Double.valueOf(restaurant.getLatitude());
                GeoCoordinate geoCoordinate = new GeoCoordinate(longitude, latitude);
                add.put(memberName, geoCoordinate);
            }
        }
        redis.geoadd("restaurant", add);
        return success(add.size() + "");
    }

    public RestaurantVo toVo(Restaurant restaurant) {
        RestaurantVo restaurantVo = new RestaurantVo();
        BeanUtils.copyProperties(restaurant, restaurantVo);
        SysDept dept = deptService.selectDeptById(restaurant.getDeptId());
        restaurantVo.setDept(dept);
        return restaurantVo;
    }

    /**
     * 导入模版
     */
    @RequiresPermissions("catering:restaurant:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<RestaurantImportData> util = new ExcelUtil<RestaurantImportData>(RestaurantImportData.class);
        return util.importTemplateExcel("餐饮信息表");
//        ExcelUtil<Restaurant> util = new ExcelUtil<Restaurant>(Restaurant.class);
//        return util.importTemplateExcel("restaurantList");
    }

    /**
     * 导入餐饮单位信息
     */
    @RequiresPermissions("catering:restaurant:import")
    @Log(title = "餐饮单位信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
//        ExcelUtil<Restaurant> util = new ExcelUtil<Restaurant>(Restaurant.class);
//        List<Restaurant> restaurantList = util.importExcel(file.getInputStream());
        ExcelUtil<RestaurantImportData> util = new ExcelUtil<RestaurantImportData>(RestaurantImportData.class);
        List<RestaurantImportData> dataList = util.importExcel(file.getInputStream());
        List<Restaurant> restaurantList = new ArrayList<>();
        for (RestaurantImportData data : dataList) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(data.getName());
            restaurant.setLegalPerson(data.getLegalPerson());
            restaurant.setPremises(data.getPremises());
            restaurant.setSignDate(data.getSignDate());
            restaurant.setExpireDate(data.getExpireDate());
            restaurant.setTel(data.getTel());

//            if (StringUtils.isNotEmpty(data.getDept())) {
//                SysDept dept = new SysDept();
//                String after = "";
//                if (data.getStatus().contains("李良双")) {
//                    after = "-1";
//                } else if (data.getStatus().contains("张龙")) {
//                    after = "-2";
//                } else if (data.getStatus().contains("张铁毕")) {
//                    after = "-3";
//                }
//                dept.setDeptName(data.getDept() + after);
//                List<SysDept> deptList = deptService.selectDeptList(dept);
//                if (deptList != null && deptList.size() > 0) {
//                    restaurant.setDeptId(deptList.get(0).getDeptId());
//                }
//            }
            if (StringUtils.isNotEmpty(data.getDept())) {
                SysDept dept = new SysDept();
                dept.setDeptName(data.getDept().substring(0, 2));
                List<SysDept> deptList = deptService.selectDeptList(dept);
                if (deptList != null && deptList.size() > 0) {
                    restaurant.setDeptId(deptList.get(0).getDeptId());
                }
            }

            if (StringUtils.isNotEmpty(data.getSize())) {
                if (data.getSize().equals("小") || data.getSize().contains("小型")) {
                    restaurant.setSize(1);
                } else if (data.getSize().equals("中") || data.getSize().contains("中型")) {
                    restaurant.setSize(2);
                } else if (data.getSize().equals("大") || data.getSize().contains("大型")) {
                    restaurant.setSize(3);
                }
            }

            if (StringUtils.isNotEmpty(data.getNature())) {
                if (data.getNature().contains("普通")) {
                    restaurant.setNature(1);
                } else if (data.getNature().contains("国企")) {
                    restaurant.setNature(2);
                } else if (data.getNature().contains("事业单位")) {
                    restaurant.setNature(3);
                } else if (data.getNature().contains("政府")) {
                    restaurant.setNature(4);
                }
            }

            if (StringUtils.isNotEmpty(data.getStatus())) {
                if (data.getStatus().equals("开业") || data.getStatus().equals("营业")) {
                    restaurant.setStatus(0);
                } else if (data.getStatus().equals("停业")) {
                    restaurant.setStatus(1);
                }
            }
            restaurantList.add(restaurant);
        }

        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = restaurantService.importRestaurantList(restaurantList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * 导出餐饮单位信息列表
     */
    @DataScope(deptAlias = "d")
    @RequiresPermissions("catering:restaurant:export")
    @Log(title = "餐饮单位信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Restaurant restaurant) {
//        Map param = new HashMap<>();
//        param.put("dataScope", "order by r.dept_id");
//        restaurant.setParams(param);
        List<Restaurant> list = restaurantService.selectRestaurantList(restaurant);
        List<RestaurantExportVo> restaurantExportVos = new ArrayList<>();
        for (Restaurant r : list) {
            RestaurantExportVo restaurantExportVo = new RestaurantExportVo();
            SysDept dept = deptService.selectDeptById(r.getDeptId());
            restaurantExportVo.setDept(dept.getDeptName());
            restaurantExportVo.setName(r.getName());
            restaurantExportVo.setPremises(r.getPremises());
            restaurantExportVo.setSize(r.getSize() + "");
            restaurantExportVo.setNature(r.getNature() + "");
            List<SysUser> recycleList = userService.selectRecycleByDeptId(r.getDeptId());
            if (recycleList != null && recycleList.size() == 1) {
                restaurantExportVo.setRecycle(recycleList.get(0).getUserName() + recycleList.get(0).getPhonenumber());
            }
            List<SysUser> managerList = userService.selectManagerByDeptId(r.getDeptId());
            if (managerList != null && managerList.size() == 1) {
                restaurantExportVo.setManager(managerList.get(0).getUserName() + managerList.get(0).getPhonenumber());
            }
            restaurantExportVo.setTel("89868986");
            restaurantExportVo.setQrData("https://www.xiha.work:9000/cy/index.html?appkey=df33cb8a73a3ae45749bd1ac6ddeb5e4&restaurantId=" + r.getRestaurantId() + "&name=" + r.getName());
            restaurantExportVos.add(restaurantExportVo);
        }
        ExcelUtil<RestaurantExportVo> util = new ExcelUtil<RestaurantExportVo>(RestaurantExportVo.class);
        return util.exportExcel(restaurantExportVos, "restaurant");
    }

    /**
     * 导入模版
     */
    @RequiresPermissions("catering:restaurant:view")
    @GetMapping("/importTemplate2")
    @ResponseBody
    public AjaxResult importTemplate2() {
        ExcelUtil<RestData> util = new ExcelUtil<RestData>(RestData.class);
        return util.importTemplateExcel("餐饮信息表");
    }

    /**
     * 导入餐饮单位信息
     */
    @RequiresPermissions("catering:restaurant:import")
    @Log(title = "餐饮单位信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData2")
    @ResponseBody
    public AjaxResult importData2(MultipartFile file) throws Exception {
        ExcelUtil<RestData> util = new ExcelUtil<RestData>(RestData.class);
        List<RestData> dataList = util.importExcel(file.getInputStream());
        int successCount = 0;

        Restaurant restaurant = new Restaurant();
        restaurant.setDeptId(0L);
        List<Restaurant> restaurantList = restaurantService.selectRestaurantList(restaurant);
        SysDept dept = new SysDept();
        List<SysDept> deptList = deptService.selectDeptList(dept);
        Map<String, Long> deptMap = new HashMap();
        for (SysDept d : deptList) {
            deptMap.put(d.getDeptName(), d.getDeptId());
        }
        for (Restaurant r : restaurantList) {
            for (RestData data : dataList) {
                if (r.getName().equals(data.getName()) && r.getPremises().equals(data.getPremises())) {
                    r.setDeptId(deptMap.get(data.getStreet()));
                    restaurantService.updateRestaurant(r);
                    successCount++;
                    break;
                }
            }
        }
        return AjaxResult.success(successCount);
    }
//    /**
//     * 导入餐饮单位信息
//     */
//    @RequiresPermissions("catering:restaurant:import")
//    @Log(title = "餐饮单位信息", businessType = BusinessType.IMPORT)
//    @PostMapping("/importData2")
//    @ResponseBody
//    public AjaxResult importData2(MultipartFile file) throws Exception {
//        ExcelUtil<RestData> util = new ExcelUtil<RestData>(RestData.class);
//        List<RestData> dataList = util.importExcel(file.getInputStream());
//        String error = "";
//        int successCount = 0;
//        int errorCount = 0;
//
//        for (RestData data : dataList) {
//            Restaurant restaurant = new Restaurant();
//            SysDept dept = new SysDept();
//            dept.setDeptName(data.getStreet());
//            List<SysDept> deptList = deptService.selectDeptList(dept);
//            if (deptList == null || deptList.size() != 1) {
//                error += data.getName() + ",";
//                errorCount++;
//                continue;
//            }
//            if (StringUtils.isEmpty(data.getQrCode())) {
//                error += data.getName() + ",";
//                errorCount++;
//                continue;
//            }
//            String qrCode = data.getQrCode();
//            int strStartIndex = qrCode.indexOf("restaurantId=");
//            int strEndIndex = qrCode.indexOf("&name=");
//            if (strStartIndex < 0) {
//                error += data.getName() + ",";
//                errorCount++;
//                continue;
//            }
//            if (strEndIndex < 0) {
//                strEndIndex = qrCode.length();
//            }
//            String restaurantId = qrCode.substring(strStartIndex, strEndIndex).substring("restaurantId=".length());
//            restaurant = restaurantService.selectRestaurantById(Long.parseLong(restaurantId));
//            if (restaurant == null) {
//                error += data.getName() + ",";
//                errorCount++;
//                continue;
//            }
////            restaurant.setName(data.getName());
////            restaurant.setPremises(data.getPremises());
////            List<Restaurant> restaurants = restaurantService.selectRestaurantList(restaurant);
////            if (restaurants == null || restaurants.size() != 1) {
////                error += data.getName() + ",";
////                errorCount++;
////                continue;
////            }
////            restaurant = restaurants.get(0);
//            restaurant.setDeptId(deptList.get(0).getDeptId());
//            int result = restaurantService.updateRestaurant(restaurant);
//            if (result > 0) {
//                successCount++;
//            } else {
//                error += data.getName() + ",";
//                errorCount++;
//            }
//        }
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("successCount", successCount);
//        jsonObject.put("errorCount", errorCount);
//        jsonObject.put("error", error);
//        if (errorCount == 0) {
//            return AjaxResult.success(jsonObject);
//        }
//        return AjaxResult.error("失败" + errorCount + "家，信息：" + error);
//    }
//
//        /**
//     * 导出餐饮单位信息列表
//     */
//    @DataScope(deptAlias = "d")
//    @RequiresPermissions("catering:restaurant:export")
//    @Log(title = "餐饮单位信息", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(Restaurant restaurant) {
//        List<Restaurant> list = restaurantService.selectRestaurantList(restaurant);
//        List<RestaurantVo> restaurantVos = new ArrayList<>();
//        for (Restaurant r : list) {
//            RestaurantVo restaurantVo = toVo(r);
//            restaurantVo.setQrCodeData("https://www.xiha.work/cy/index.html?appkey=df33cb8a73a3ae45749bd1ac6ddeb5e4&restaurantId=" + r.getRestaurantId() + "&name=" + r.getName());
//            restaurantVos.add(restaurantVo);
//        }
//        ExcelUtil<RestaurantVo> util = new ExcelUtil<RestaurantVo>(RestaurantVo.class);
//        return util.exportExcel(restaurantVos, "restaurant");
//    }
}
