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
import com.ruoyi.catering.domain.Garbage;
import com.ruoyi.catering.service.IGarbageService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 垃圾类型Controller
 * 
 * @author lsy
 * @date 2020-07-08
 */
@Controller
@RequestMapping("/catering/garbage")
public class GarbageController extends BaseController
{
    private String prefix = "catering/garbage";

    @Autowired
    private IGarbageService garbageService;

    @RequiresPermissions("catering:garbage:view")
    @GetMapping()
    public String garbage()
    {
        return prefix + "/garbage";
    }

    /**
     * 查询垃圾类型列表
     */
    @RequiresPermissions("catering:garbage:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Garbage garbage)
    {
        startPage();
        List<Garbage> list = garbageService.selectGarbageList(garbage);
        return getDataTable(list);
    }

    /**
     * 导出垃圾类型列表
     */
    @RequiresPermissions("catering:garbage:export")
    @Log(title = "垃圾类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Garbage garbage)
    {
        List<Garbage> list = garbageService.selectGarbageList(garbage);
        ExcelUtil<Garbage> util = new ExcelUtil<Garbage>(Garbage.class);
        return util.exportExcel(list, "garbage");
    }

    /**
     * 新增垃圾类型
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存垃圾类型
     */
    @RequiresPermissions("catering:garbage:add")
    @Log(title = "垃圾类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Garbage garbage)
    {
        return toAjax(garbageService.insertGarbage(garbage));
    }

    /**
     * 修改垃圾类型
     */
    @GetMapping("/edit/{garbageId}")
    public String edit(@PathVariable("garbageId") Long garbageId, ModelMap mmap)
    {
        Garbage garbage = garbageService.selectGarbageById(garbageId);
        mmap.put("garbage", garbage);
        return prefix + "/edit";
    }

    /**
     * 修改保存垃圾类型
     */
    @RequiresPermissions("catering:garbage:edit")
    @Log(title = "垃圾类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Garbage garbage)
    {
        return toAjax(garbageService.updateGarbage(garbage));
    }

    /**
     * 删除垃圾类型
     */
    @RequiresPermissions("catering:garbage:remove")
    @Log(title = "垃圾类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(garbageService.deleteGarbageByIds(ids));
    }
}
