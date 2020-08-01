package com.ruoyi.catering.controller;

import com.github.pagehelper.PageInfo;
import com.ruoyi.catering.domain.*;
import com.ruoyi.catering.service.IDiningTypeService;
import com.ruoyi.catering.service.IGasTypeService;
import com.ruoyi.catering.service.IRegionService;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.vo.RestaurantImportData;
import com.ruoyi.catering.vo.RestaurantVo;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    private IDiningTypeService diningTypeService;
    @Autowired
    private IRegionService regionService;
    @Autowired
    private IGasTypeService gasTypeService;
    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("catering:restaurant:view")
    @GetMapping()
    public String restaurant(Model model) {
        model.addAttribute("diningTypes", diningTypeService.selectDiningTypeList(new DiningType()));
//        model.addAttribute("depts", deptService.selectDeptList(new SysDept()));
//        model.addAttribute("regions", regionService.selectRegionList(new Region()));
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
     * 导出餐饮单位信息列表
     */
    @DataScope(deptAlias = "d")
    @RequiresPermissions("catering:restaurant:export")
    @Log(title = "餐饮单位信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Restaurant restaurant) {
        List<Restaurant> list = restaurantService.selectRestaurantList(restaurant);
        List<RestaurantVo> restaurantVos = new ArrayList<>();
        for (Restaurant r : list) {
            RestaurantVo restaurantVo = toVo(r);
            restaurantVo.setQrCodeData("https://www.xiha.work/cy/index.html?appkey=df33cb8a73a3ae45749bd1ac6ddeb5e4&restaurantId=" + r.getRestaurantId() + "&name=" + r.getName());
            restaurantVos.add(restaurantVo);
        }
        ExcelUtil<RestaurantVo> util = new ExcelUtil<RestaurantVo>(RestaurantVo.class);
        return util.exportExcel(restaurantVos, "restaurant");
    }

    /**
     * 新增餐饮单位信息
     */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("diningTypes", diningTypeService.selectDiningTypeList(new DiningType()));
//        model.addAttribute("regions", regionService.selectRegionList(new Region()));
        model.addAttribute("gasTypes", gasTypeService.selectGasTypeList(new GasType()));
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
//        Region region = regionService.selectRegionById(restaurant.getRegionId());
//        restaurantVo.setRegion(region);
        SysDept dept = deptService.selectDeptById(restaurant.getDeptId());
        restaurantVo.setDept(dept);
        mmap.put("restaurant", restaurantVo);
        mmap.put("diningTypes", diningTypeService.selectDiningTypeList(new DiningType()));
        List<GasType> gasTypes = gasTypeService.selectGasTypeList(new GasType());
        if (StringUtils.isNotEmpty(restaurant.getGasTypeId())) {
            String[] ids = restaurant.getGasTypeId().split(",");
            for (String id : ids) {
                for (GasType gasType : gasTypes) {
                    if (gasType.getTypeId().toString().equals(id)) {
                        gasType.setStatus(1);
                        break;
                    }
                }
            }
        }
        mmap.put("gasTypes", gasTypes);
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
//            restaurant.setShortName(data.getShortName());
            restaurant.setLegalPerson(data.getLegalPerson());
            restaurant.setPremises(data.getPremises());
            restaurant.setSignDate(data.getSignDate());
            restaurant.setExpireDate(data.getExpireDate());
//            if (StringUtils.isNotEmpty(data.getDiningType())) {
//                DiningType diningType = new DiningType();
//                diningType.setName(data.getDiningType());
//                List<DiningType> diningTypes = diningTypeService.selectDiningTypeList(diningType);
//                if (diningTypes != null && diningTypes.size() > 0) {
//                    restaurant.setDiningTypeId(diningTypes.get(0).getTypeId());
//                }
//            }
            restaurant.setTel(data.getTel());

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


//            if (StringUtils.isNotEmpty(data.getRegion())) {
//                Region region = new Region();
//                region.setRegionName(data.getRegion());
//                List<Region> regions = regionService.selectRegionList(region);
//                if (regions != null && regions.size() > 0) {
//                    restaurant.setRegionId(regions.get(0).getRegionId());
//                }
//            }
//            restaurant.setArea(data.getArea());
//            restaurant.setLongitude(data.getLongitude());
//            restaurant.setLatitude(data.getLatitude());
//            if (StringUtils.isNotEmpty(data.getHasOilWaterSeparator())) {
//                if (data.getHasOilWaterSeparator().equals("有")) {
//                    restaurant.setHasOilWaterSeparator(0);
//                } else if (data.getHasOilWaterSeparator().equals("无")) {
//                    restaurant.setHasOilWaterSeparator(1);
//                }
//            }
//            if (StringUtils.isNotEmpty(data.getHasFumeCleaner())) {
//                if (data.getHasFumeCleaner().equals("有")) {
//                    restaurant.setHasFumeCleaner(0);
//                } else if (data.getHasFumeCleaner().equals("无")) {
//                    restaurant.setHasFumeCleaner(1);
//                }
//            }
//            if (StringUtils.isNotEmpty(data.getHasDischargePermit())) {
//                if (data.getHasDischargePermit().equals("有")) {
//                    restaurant.setHasDischargePermit(0);
//                } else if (data.getHasDischargePermit().equals("无")) {
//                    restaurant.setHasDischargePermit(1);
//                }
//            }
//            restaurant.setDischargeNo(data.getDischargeNo());
//            restaurant.setValidityDay(data.getValidityDay());
//            restaurant.setClosedDay(data.getClosedDay());
//            if (StringUtils.isNotEmpty(data.getGasType())) {
//                String[] typeNames = data.getGasType().split(",");
//                String gasTypeId = "";
//                for (String name : typeNames) {
//                    GasType gasType = new GasType();
//                    gasType.setName(name);
//                    List<GasType> gasTypes = gasTypeService.selectGasTypeList(gasType);
//                    if (gasTypes != null && gasTypes.size() > 0) {
//                        gasTypeId += gasTypes.get(0).getTypeId() + ",";
//                    }
//                }
//                if (StringUtils.isNotEmpty(gasTypeId)) {
//                    gasTypeId = gasTypeId.substring(0, gasTypeId.length() - 1);
//                    restaurant.setGasTypeId(gasTypeId);
//                }
//            }
//            if (StringUtils.isNotEmpty(data.getHaskwoRecoveryAgreement())) {
//                if (data.getHaskwoRecoveryAgreement().equals("已签")) {
//                    restaurant.setHaskwoRecoveryAgreement(0);
//                } else if (data.getHaskwoRecoveryAgreement().equals("未签")) {
//                    restaurant.setHaskwoRecoveryAgreement(1);
//                }
//            }
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

    public RestaurantVo toVo(Restaurant restaurant) {
        RestaurantVo restaurantVo = new RestaurantVo();
        BeanUtils.copyProperties(restaurant, restaurantVo);
//        if (restaurant.getDiningTypeId() != null) {
//            DiningType diningType = diningTypeService.selectDiningTypeById(restaurant.getDiningTypeId());
//            restaurantVo.setDiningType(diningType);
//        }
        SysDept dept = deptService.selectDeptById(restaurant.getDeptId());
        restaurantVo.setDept(dept);
//        Region region = regionService.selectRegionById(restaurant.getRegionId());
//        restaurantVo.setRegion(region);
//        if (StringUtils.isNotEmpty(restaurant.getGasTypeId())) {
//            String[] ids = restaurant.getGasTypeId().split(",");
//            String gasTypeNames = "";
//            for (String id : ids) {
//                GasType gasType = gasTypeService.selectGasTypeById(Long.parseLong(id));
//                gasTypeNames += gasType.getName() + ",";
//            }
//            gasTypeNames = gasTypeNames.substring(0, gasTypeNames.length() - 1);
//            restaurantVo.setGasTypeNames(gasTypeNames);
//        }
        return restaurantVo;
    }
}
