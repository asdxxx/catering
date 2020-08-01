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
import com.ruoyi.catering.domain.DiningType;
import com.ruoyi.catering.service.IDiningTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 餐饮类型Controller
 * 
 * @author lsy
 * @date 2020-07-07
 */
@Controller
@RequestMapping("/catering/diningType")
public class DiningTypeController extends BaseController
{
    private String prefix = "catering/diningType";

    @Autowired
    private IDiningTypeService diningTypeService;

    @RequiresPermissions("catering:diningType:view")
    @GetMapping()
    public String diningType()
    {
        return prefix + "/diningType";
    }

    /**
     * 查询餐饮类型列表
     */
    @RequiresPermissions("catering:diningType:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DiningType diningType)
    {
        startPage();
        List<DiningType> list = diningTypeService.selectDiningTypeList(diningType);
        return getDataTable(list);
    }

    /**
     * 导出餐饮类型列表
     */
    @RequiresPermissions("catering:diningType:export")
    @Log(title = "餐饮类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DiningType diningType)
    {
        List<DiningType> list = diningTypeService.selectDiningTypeList(diningType);
        ExcelUtil<DiningType> util = new ExcelUtil<DiningType>(DiningType.class);
        return util.exportExcel(list, "diningType");
    }

    /**
     * 新增餐饮类型
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存餐饮类型
     */
    @RequiresPermissions("catering:diningType:add")
    @Log(title = "餐饮类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DiningType diningType)
    {
        return toAjax(diningTypeService.insertDiningType(diningType));
    }

    /**
     * 修改餐饮类型
     */
    @GetMapping("/edit/{typeId}")
    public String edit(@PathVariable("typeId") Long typeId, ModelMap mmap)
    {
        DiningType diningType = diningTypeService.selectDiningTypeById(typeId);
        mmap.put("diningType", diningType);
        return prefix + "/edit";
    }

    /**
     * 修改保存餐饮类型
     */
    @RequiresPermissions("catering:diningType:edit")
    @Log(title = "餐饮类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DiningType diningType)
    {
        return toAjax(diningTypeService.updateDiningType(diningType));
    }

    /**
     * 删除餐饮类型
     */
    @RequiresPermissions("catering:diningType:remove")
    @Log(title = "餐饮类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(diningTypeService.deleteDiningTypeByIds(ids));
    }
}
