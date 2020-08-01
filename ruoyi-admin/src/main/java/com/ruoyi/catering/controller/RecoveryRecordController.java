package com.ruoyi.catering.controller;

import com.github.pagehelper.PageInfo;
import com.ruoyi.catering.domain.Garbage;
import com.ruoyi.catering.domain.RecoveryRecord;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.IGarbageService;
import com.ruoyi.catering.service.IRecoveryRecordService;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.vo.RecoveryRecordVo;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 回收记录Controller
 * 
 * @author lsy
 * @date 2020-07-08
 */
@Controller
@RequestMapping("/catering/recoveryRecord")
public class RecoveryRecordController extends BaseController
{
    private String prefix = "catering/recoveryRecord";

    @Autowired
    private IRecoveryRecordService recoveryRecordService;
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IGarbageService garbageService;
    @Autowired
    private ISysRoleService roleService;

    @RequiresPermissions("catering:recoveryRecord:view")
    @GetMapping()
    public String recoveryRecord(Model model)
    {
        model.addAttribute("garbages",garbageService.selectGarbageList(new Garbage()));
        return prefix + "/recoveryRecord";
    }

    /**
     * 查询回收记录列表
     */
//    @DataScope(deptAlias = "d", userAlias = "u")
    @DataScope(deptAlias = "d")
    @RequiresPermissions("catering:recoveryRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RecoveryRecord recoveryRecord)
    {
        startPage();
        List<RecoveryRecord> list = recoveryRecordService.selectRecoveryRecordList(recoveryRecord);
        List<RecoveryRecordVo> recoveryRecordVos = new ArrayList<>();
        for (RecoveryRecord rr : list) {
            RecoveryRecordVo recoveryRecordVo = toVo(rr);
            recoveryRecordVos.add(recoveryRecordVo);
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(recoveryRecordVos);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
//        return getDataTable(list);
    }

    /**
     * 导出回收记录列表
     */
    @RequiresPermissions("catering:recoveryRecord:export")
    @Log(title = "回收记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RecoveryRecord recoveryRecord)
    {
        List<RecoveryRecord> list = recoveryRecordService.selectRecoveryRecordList(recoveryRecord);
        List<RecoveryRecordVo> recoveryRecordVos = new ArrayList<>();
        for(RecoveryRecord rr:list){
            RecoveryRecordVo recoveryRecordVo = toVo(rr);
            recoveryRecordVos.add(recoveryRecordVo);
        }
        ExcelUtil<RecoveryRecordVo> util = new ExcelUtil<RecoveryRecordVo>(RecoveryRecordVo.class);
        return util.exportExcel(recoveryRecordVos, "回收记录");
//        ExcelUtil<RecoveryRecord> util = new ExcelUtil<RecoveryRecord>(RecoveryRecord.class);
//        return util.exportExcel(list, "recoveryRecord");
    }

    /**
     * 新增回收记录
     */
    @GetMapping("/add")
    public String add(Model model)
    {
        model.addAttribute("garbages", garbageService.selectGarbageList(new Garbage()));
        return prefix + "/add";
    }

    /**
     * 新增保存回收记录
     */
    @RequiresPermissions("catering:recoveryRecord:add")
    @Log(title = "回收记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RecoveryRecord recoveryRecord)
    {
        return toAjax(recoveryRecordService.insertRecoveryRecord(recoveryRecord));
    }

    /**
     * 修改回收记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RecoveryRecord recoveryRecord = recoveryRecordService.selectRecoveryRecordById(id);
        RecoveryRecordVo recoveryRecordVo = toVo(recoveryRecord);
        mmap.put("recoveryRecord", recoveryRecordVo);
        mmap.put("garbages", garbageService.selectGarbageList(new Garbage()));
        return prefix + "/edit";
    }

    /**
     * 修改保存回收记录
     */
    @RequiresPermissions("catering:recoveryRecord:edit")
    @Log(title = "回收记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RecoveryRecord recoveryRecord)
    {
        return toAjax(recoveryRecordService.updateRecoveryRecord(recoveryRecord));
    }

    /**
     * 删除回收记录
     */
    @RequiresPermissions("catering:recoveryRecord:remove")
    @Log(title = "回收记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(recoveryRecordService.deleteRecoveryRecordByIds(ids));
    }

    public RecoveryRecordVo toVo(RecoveryRecord recoveryRecord) {
        RecoveryRecordVo recoveryRecordVo = new RecoveryRecordVo();
        BeanUtils.copyProperties(recoveryRecord, recoveryRecordVo);
        Restaurant restaurant = restaurantService.selectRestaurantById(recoveryRecord.getRestaurantId());
        recoveryRecordVo.setRestaurant(restaurant);
        SysUser user = userService.selectUserById(recoveryRecord.getUserId());
        recoveryRecordVo.setUser(user);
        Garbage garbage = garbageService.selectGarbageById(recoveryRecord.getGarbageId());
        recoveryRecordVo.setGarbage(garbage);
        return recoveryRecordVo;
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
