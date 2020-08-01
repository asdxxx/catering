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
import com.ruoyi.catering.domain.Slideshow;
import com.ruoyi.catering.service.ISlideshowService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 轮播图Controller
 * 
 * @author lsy
 * @date 2020-07-07
 */
@Controller
@RequestMapping("/catering/slideshow")
public class SlideshowController extends BaseController
{
    private String prefix = "catering/slideshow";

    @Autowired
    private ISlideshowService slideshowService;

    @RequiresPermissions("catering:slideshow:view")
    @GetMapping()
    public String slideshow()
    {
        return prefix + "/slideshow";
    }

    /**
     * 查询轮播图列表
     */
    @RequiresPermissions("catering:slideshow:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Slideshow slideshow)
    {
        startPage();
        List<Slideshow> list = slideshowService.selectSlideshowList(slideshow);
        return getDataTable(list);
    }

    /**
     * 导出轮播图列表
     */
    @RequiresPermissions("catering:slideshow:export")
    @Log(title = "轮播图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Slideshow slideshow)
    {
        List<Slideshow> list = slideshowService.selectSlideshowList(slideshow);
        ExcelUtil<Slideshow> util = new ExcelUtil<Slideshow>(Slideshow.class);
        return util.exportExcel(list, "slideshow");
    }

    /**
     * 新增轮播图
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存轮播图
     */
    @RequiresPermissions("catering:slideshow:add")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Slideshow slideshow)
    {
        return toAjax(slideshowService.insertSlideshow(slideshow));
    }

    /**
     * 修改轮播图
     */
    @GetMapping("/edit/{slideshowId}")
    public String edit(@PathVariable("slideshowId") Long slideshowId, ModelMap mmap)
    {
        Slideshow slideshow = slideshowService.selectSlideshowById(slideshowId);
        mmap.put("slideshow", slideshow);
        return prefix + "/edit";
    }

    /**
     * 修改保存轮播图
     */
    @RequiresPermissions("catering:slideshow:edit")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Slideshow slideshow)
    {
        return toAjax(slideshowService.updateSlideshow(slideshow));
    }

    /**
     * 删除轮播图
     */
    @RequiresPermissions("catering:slideshow:remove")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(slideshowService.deleteSlideshowByIds(ids));
    }
}
