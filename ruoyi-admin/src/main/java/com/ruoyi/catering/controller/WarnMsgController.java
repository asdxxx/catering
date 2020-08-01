package com.ruoyi.catering.controller;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ruoyi.catering.domain.Feedback;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.service.impl.RestaurantServiceImpl;
import com.ruoyi.catering.vo.FeedbackVo;
import com.ruoyi.catering.vo.RestaurantVo;
import com.ruoyi.catering.vo.WarnMsgVo;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.SysUser;
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
import com.ruoyi.catering.domain.WarnMsg;
import com.ruoyi.catering.service.IWarnMsgService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 告警信息Controller
 * 
 * @author lsy
 * @date 2020-07-12
 */
@Controller
@RequestMapping("/catering/warnMsg")
public class WarnMsgController extends BaseController
{
    private String prefix = "catering/warnMsg";

    @Autowired
    private IWarnMsgService warnMsgService;
    @Autowired
    private IRestaurantService restaurantService;

    @RequiresPermissions("catering:warnMsg:view")
    @GetMapping()
    public String warnMsg()
    {
        return prefix + "/warnMsg";
    }

    /**
     * 查询告警信息列表
     */
    @RequiresPermissions("catering:warnMsg:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WarnMsg warnMsg)
    {
        startPage();
        List<WarnMsg> list = warnMsgService.selectWarnMsgList(warnMsg);

        List<WarnMsgVo> warnMsgVos = new ArrayList<>();
        for (WarnMsg wm : list) {
            WarnMsgVo warnMsgVo = toVo(wm);
            warnMsgVos.add(warnMsgVo);
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(warnMsgVos);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
//        return getDataTable(list);
    }

    /**
     * 导出告警信息列表
     */
    @RequiresPermissions("catering:warnMsg:export")
    @Log(title = "告警信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WarnMsg warnMsg)
    {
        List<WarnMsg> list = warnMsgService.selectWarnMsgList(warnMsg);
        List<WarnMsgVo> warnMsgVos = new ArrayList<>();
        for (WarnMsg wm : list) {
            WarnMsgVo warnMsgVo = toVo(wm);
            warnMsgVos.add(warnMsgVo);
        }
        ExcelUtil<WarnMsgVo> util = new ExcelUtil<WarnMsgVo>(WarnMsgVo.class);
        return util.exportExcel(warnMsgVos, "告警信息");
//        ExcelUtil<WarnMsg> util = new ExcelUtil<WarnMsg>(WarnMsg.class);
//        return util.exportExcel(list, "warnMsg");
    }

    /**
     * 新增告警信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存告警信息
     */
    @RequiresPermissions("catering:warnMsg:add")
    @Log(title = "告警信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WarnMsg warnMsg)
    {
        return toAjax(warnMsgService.insertWarnMsg(warnMsg));
    }

    /**
     * 修改告警信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        WarnMsg warnMsg = warnMsgService.selectWarnMsgById(id);
        mmap.put("warnMsg", warnMsg);
        return prefix + "/edit";
    }

    /**
     * 修改保存告警信息
     */
    @RequiresPermissions("catering:warnMsg:edit")
    @Log(title = "告警信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WarnMsg warnMsg)
    {
        return toAjax(warnMsgService.updateWarnMsg(warnMsg));
    }

    /**
     * 删除告警信息
     */
    @RequiresPermissions("catering:warnMsg:remove")
    @Log(title = "告警信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(warnMsgService.deleteWarnMsgByIds(ids));
    }

    public WarnMsgVo toVo(WarnMsg warnMsg) {
        WarnMsgVo warnMsgVo = new WarnMsgVo();
        BeanUtils.copyProperties(warnMsg, warnMsgVo);
        if (warnMsg.getRestaurantId() != null) {
            Restaurant restaurant = restaurantService.selectRestaurantById(warnMsg.getRestaurantId());
            warnMsgVo.setRestaurant(restaurant);
        }
        return warnMsgVo;
    }

}
