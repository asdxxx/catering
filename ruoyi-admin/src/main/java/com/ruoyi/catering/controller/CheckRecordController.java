package com.ruoyi.catering.controller;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ruoyi.catering.domain.Garbage;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.vo.CheckRecordVo;
import com.ruoyi.catering.vo.RecoveryRecordVo;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.catering.domain.CheckRecord;
import com.ruoyi.catering.service.ICheckRecordService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检查记录Controller
 *
 * @author lsy
 * @date 2020-07-23
 */
@Controller
@RequestMapping("/catering/checkRecord")
public class CheckRecordController extends BaseController {
    private String prefix = "catering/checkRecord";

    @Autowired
    private ICheckRecordService checkRecordService;
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysRoleService roleService;

    @RequiresPermissions("catering:checkRecord:view")
    @GetMapping()
    public String checkRecord() {
        return prefix + "/checkRecord";
    }

    /**
     * 查询检查记录列表
     */
    @DataScope(deptAlias = "d")
    @RequiresPermissions("catering:checkRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CheckRecord checkRecord) {
        startPage();
        List<CheckRecord> list = checkRecordService.selectCheckRecordList(checkRecord);
        List<CheckRecordVo> checkRecordVos = new ArrayList<>();
        for (CheckRecord cr : list) {
            CheckRecordVo checkRecordVo = toVo(cr);
            checkRecordVos.add(checkRecordVo);
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(checkRecordVos);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
//        return getDataTable(list);
    }

    /**
     * 导出检查记录列表
     */
    @RequiresPermissions("catering:checkRecord:export")
    @Log(title = "检查记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CheckRecord checkRecord) {
        List<CheckRecord> list = checkRecordService.selectCheckRecordList(checkRecord);
        List<CheckRecordVo> checkRecordVos = new ArrayList<>();
        for(CheckRecord cr:list){
            CheckRecordVo checkRecordVo = toVo(cr);
            checkRecordVos.add(checkRecordVo);
        }
        ExcelUtil<CheckRecordVo> util = new ExcelUtil<CheckRecordVo>(CheckRecordVo.class);
        return util.exportExcel(checkRecordVos, "检查记录");
    }

    /**
     * 新增检查记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存检查记录
     */
    @RequiresPermissions("catering:checkRecord:add")
    @Log(title = "检查记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CheckRecord checkRecord) {
        return toAjax(checkRecordService.insertCheckRecord(checkRecord));
    }

    /**
     * 修改检查记录
     */
    @GetMapping("/edit/{recordId}")
    public String edit(@PathVariable("recordId") Long recordId, ModelMap mmap) {
        CheckRecord checkRecord = checkRecordService.selectCheckRecordById(recordId);
        CheckRecordVo checkRecordVo = toVo(checkRecord);
        mmap.put("checkRecord", checkRecordVo);
        return prefix + "/edit";
    }

    /**
     * 修改保存检查记录
     */
    @RequiresPermissions("catering:checkRecord:edit")
    @Log(title = "检查记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CheckRecord checkRecord) {
        return toAjax(checkRecordService.updateCheckRecord(checkRecord));
    }

    /**
     * 删除检查记录
     */
    @RequiresPermissions("catering:checkRecord:remove")
    @Log(title = "检查记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(checkRecordService.deleteCheckRecordByIds(ids));
    }

    public CheckRecordVo toVo(CheckRecord checkRecord) {
        CheckRecordVo checkRecordVo = new CheckRecordVo();
        BeanUtils.copyProperties(checkRecord, checkRecordVo);
        Restaurant restaurant = restaurantService.selectRestaurantById(checkRecord.getRestaurantId());
        checkRecordVo.setRestaurant(restaurant);
        SysUser user = userService.selectUserById(checkRecord.getUserId());
        checkRecordVo.setUser(user);
        return checkRecordVo;
    }

    /**
     * 选择用户
     */
    @GetMapping("/selectUser")
    public String selectUser(ModelMap mmap) {
        List<SysRole> sysRoles = roleService.selectRolesByUserId(ShiroUtils.getUserId());
        mmap.put("role", sysRoles.get(0));
        return prefix + "/selectUser";
    }

    /**
     * 选择店铺
     */
    @GetMapping("/selectRestaurant")
    public String selectRestaurant(ModelMap mmap) {
        return prefix + "/selectRestaurant";
    }
}
