package com.ruoyi.catering.controller;

import java.util.List;
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
import com.ruoyi.catering.domain.GasType;
import com.ruoyi.catering.service.IGasTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 燃气类型Controller
 * 
 * @author lsy
 * @date 2020-07-08
 */
@Controller
@RequestMapping("/catering/gasType")
public class GasTypeController extends BaseController
{
    private String prefix = "catering/gasType";

    @Autowired
    private IGasTypeService gasTypeService;

    @RequiresPermissions("catering:gasType:view")
    @GetMapping()
    public String gasType()
    {
        return prefix + "/gasType";
    }

    /**
     * 查询燃气类型列表
     */
    @RequiresPermissions("catering:gasType:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GasType gasType)
    {
        startPage();
        List<GasType> list = gasTypeService.selectGasTypeList(gasType);
        return getDataTable(list);
    }

    /**
     * 导出燃气类型列表
     */
    @RequiresPermissions("catering:gasType:export")
    @Log(title = "燃气类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GasType gasType)
    {
        List<GasType> list = gasTypeService.selectGasTypeList(gasType);
        ExcelUtil<GasType> util = new ExcelUtil<GasType>(GasType.class);
        return util.exportExcel(list, "gasType");
    }

    /**
     * 新增燃气类型
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存燃气类型
     */
    @RequiresPermissions("catering:gasType:add")
    @Log(title = "燃气类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GasType gasType)
    {
        return toAjax(gasTypeService.insertGasType(gasType));
    }

    /**
     * 修改燃气类型
     */
    @GetMapping("/edit/{typeId}")
    public String edit(@PathVariable("typeId") Long typeId, ModelMap mmap)
    {
        GasType gasType = gasTypeService.selectGasTypeById(typeId);
        mmap.put("gasType", gasType);
        return prefix + "/edit";
    }

    /**
     * 修改保存燃气类型
     */
    @RequiresPermissions("catering:gasType:edit")
    @Log(title = "燃气类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GasType gasType)
    {
        return toAjax(gasTypeService.updateGasType(gasType));
    }

    /**
     * 删除燃气类型
     */
    @RequiresPermissions("catering:gasType:remove")
    @Log(title = "燃气类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(gasTypeService.deleteGasTypeByIds(ids));
    }
}
