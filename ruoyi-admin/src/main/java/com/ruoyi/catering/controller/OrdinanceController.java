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
import com.ruoyi.catering.domain.Ordinance;
import com.ruoyi.catering.service.IOrdinanceService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 市容条例Controller
 * 
 * @author lsy
 * @date 2020-07-07
 */
@Controller
@RequestMapping("/catering/ordinance")
public class OrdinanceController extends BaseController
{
    private String prefix = "catering/ordinance";

    @Autowired
    private IOrdinanceService ordinanceService;

    @RequiresPermissions("catering:ordinance:view")
    @GetMapping()
    public String ordinance()
    {
        return prefix + "/ordinance";
    }

    /**
     * 查询市容条例列表
     */
    @RequiresPermissions("catering:ordinance:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Ordinance ordinance)
    {
        startPage();
        List<Ordinance> list = ordinanceService.selectOrdinanceList(ordinance);
        return getDataTable(list);
    }

    /**
     * 导出市容条例列表
     */
    @RequiresPermissions("catering:ordinance:export")
    @Log(title = "市容条例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Ordinance ordinance)
    {
        List<Ordinance> list = ordinanceService.selectOrdinanceList(ordinance);
        ExcelUtil<Ordinance> util = new ExcelUtil<Ordinance>(Ordinance.class);
        return util.exportExcel(list, "ordinance");
    }

    /**
     * 新增市容条例
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存市容条例
     */
    @RequiresPermissions("catering:ordinance:add")
    @Log(title = "市容条例", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Ordinance ordinance)
    {
        return toAjax(ordinanceService.insertOrdinance(ordinance));
    }

    /**
     * 修改市容条例
     */
    @GetMapping("/edit/{ordinanceId}")
    public String edit(@PathVariable("ordinanceId") Long ordinanceId, ModelMap mmap)
    {
        Ordinance ordinance = ordinanceService.selectOrdinanceById(ordinanceId);
        mmap.put("ordinance", ordinance);
        return prefix + "/edit";
    }

    /**
     * 修改保存市容条例
     */
    @RequiresPermissions("catering:ordinance:edit")
    @Log(title = "市容条例", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Ordinance ordinance)
    {
        return toAjax(ordinanceService.updateOrdinance(ordinance));
    }

    /**
     * 删除市容条例
     */
    @RequiresPermissions("catering:ordinance:remove")
    @Log(title = "市容条例", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(ordinanceService.deleteOrdinanceByIds(ids));
    }
}
