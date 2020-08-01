package com.ruoyi.catering.controller;

import java.util.List;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysRole;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.catering.domain.Region;
import com.ruoyi.catering.service.IRegionService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 区域信息Controller
 * 
 * @author lsy
 * @date 2020-07-08
 */
@Controller
@RequestMapping("/catering/region")
public class RegionController extends BaseController
{
    private String prefix = "catering/region";

    @Autowired
    private IRegionService regionService;

    @RequiresPermissions("catering:region:view")
    @GetMapping()
    public String region()
    {
        return prefix + "/region";
    }

    @RequiresPermissions("catering:region:list")
    @PostMapping("/list")
    @ResponseBody
    public List<Region> list(Region region)
    {
        List<Region> regionList = regionService.selectRegionList(region);
        return regionList;
    }

    /**
     * 新增区域
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap)
    {
        mmap.put("region", regionService.selectRegionById(parentId));
        return prefix + "/add";
    }

    /**
     * 新增保存区域
     */
    @Log(title = "区域管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("catering:region:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Region region)
    {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(regionService.checkRegionNameUnique(region)))
        {
            return error("新增区域'" + region.getRegionName() + "'失败，区域名称已存在");
        }
        region.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(regionService.insertRegion(region));
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{regionId}")
    public String edit(@PathVariable("regionId") Long regionId, ModelMap mmap)
    {
        Region region = regionService.selectRegionById(regionId);
        if (StringUtils.isNotNull(region) && 100L == regionId)
        {
            region.setParentName("无");
        }
        mmap.put("region", region);
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "区域管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("catering:region:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Region region)
    {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(regionService.checkRegionNameUnique(region)))
        {
            return error("修改区域'" + region.getRegionName() + "'失败，区域名称已存在");
        }
        else if (region.getParentId().equals(region.getRegionId()))
        {
            return error("修改区域'" + region.getRegionName() + "'失败，上级区域不能是自己");
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, region.getStatus())
                && regionService.selectNormalChildrenRegionById(region.getRegionId()) > 0)
        {
            return AjaxResult.error("该区域包含未停用的子区域！");
        }
        region.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(regionService.updateRegion(region));
    }

    /**
     * 删除
     */
    @Log(title = "区域管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("catering:region:remove")
    @GetMapping("/remove/{regionId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("regionId") Long regionId)
    {
        if (regionService.selectRegionCount(regionId) > 0)
        {
            return AjaxResult.warn("存在下级区域,不允许删除");
        }
        if (regionService.checkRegionExistRestaurant(regionId))
        {
            return AjaxResult.warn("区域存在店铺,不允许删除");
        }
        return toAjax(regionService.deleteRegionById(regionId));
    }

    /**
     * 校验区域名称
     */
    @PostMapping("/checkRegionNameUnique")
    @ResponseBody
    public String checkRegionNameUnique(Region region)
    {
        return regionService.checkRegionNameUnique(region);
    }

    /**
     * 选择区域树
     *
     * @param regionId 区域ID
     * @param excludeId 排除ID
     */
    @GetMapping(value = { "/selectRegionTree/{regionId}", "/selectRegionTree/{regionId}/{excludeId}" })
    public String selectRegionTree(@PathVariable("regionId") Long regionId,
                                 @PathVariable(value = "excludeId", required = false) String excludeId, ModelMap mmap)
    {
        mmap.put("region", regionService.selectRegionById(regionId));
        mmap.put("excludeId", excludeId);
        return prefix + "/tree";
    }

    /**
     * 加载区域列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = regionService.selectRegionTree(new Region());
        return ztrees;
    }

    /**
     * 加载区域列表树（排除下级）
     */
    @GetMapping("/treeData/{excludeId}")
    @ResponseBody
    public List<Ztree> treeDataExcludeChild(@PathVariable(value = "excludeId", required = false) Long excludeId)
    {
        Region region = new Region();
        region.setRegionId(excludeId);
        List<Ztree> ztrees = regionService.selectRegionTreeExcludeChild(region);
        return ztrees;
    }

//    /**
//     * 加载角色区域（数据权限）列表树
//     */
//    @GetMapping("/roleRegionTreeData")
//    @ResponseBody
//    public List<Ztree> regionTreeData(SysRole role)
//    {
//        List<Ztree> ztrees = regionService.roleRegionTreeData(role);
//        return ztrees;
//    }
}
