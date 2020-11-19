package com.ruoyi.catering.controller;

import com.github.pagehelper.PageInfo;
import com.ruoyi.catering.domain.Feedback;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.service.IFeedbackService;
import com.ruoyi.catering.service.IRestaurantService;
import com.ruoyi.catering.vo.FeedbackVo;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 意见反馈Controller
 *
 * @author lsy
 * @date 2020-07-08
 */
@Controller
@RequestMapping("/catering/feedback")
public class FeedbackController extends BaseController {
    private String prefix = "catering/feedback";

    @Autowired
    private IFeedbackService feedbackService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private ISysRoleService roleService;

    @RequiresPermissions("catering:feedback:view")
    @GetMapping()
    public String feedback() {
        return prefix + "/feedback";
    }

    /**
     * 查询意见反馈列表
     */
    @RequiresPermissions("catering:feedback:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Feedback feedback) {
        startPage();
        List<Feedback> list = feedbackService.selectFeedbackList(feedback);
        List<FeedbackVo> feedbackVos = new ArrayList<>();
        for (Feedback f : list) {
            FeedbackVo feedbackVo = toVo(f);
            feedbackVos.add(feedbackVo);
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(feedbackVos);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
//        return getDataTable(list);
    }

    /**
     * 导出意见反馈列表
     */
    @RequiresPermissions("catering:feedback:export")
    @Log(title = "意见反馈", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Feedback feedback) {
        List<Feedback> list = feedbackService.selectFeedbackList(feedback);
        List<FeedbackVo> feedbackVos = new ArrayList<>();
        for(Feedback fb:list){
            FeedbackVo feedbackVo = toVo(fb);
            feedbackVos.add(feedbackVo);
        }
        ExcelUtil<FeedbackVo> util = new ExcelUtil<FeedbackVo>(FeedbackVo.class);
        return util.exportExcel(feedbackVos, "意见反馈");
    }

    /**
     * 新增意见反馈
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存意见反馈
     */
    @RequiresPermissions("catering:feedback:add")
    @Log(title = "意见反馈", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Feedback feedback) {
        return toAjax(feedbackService.insertFeedback(feedback));
    }

    /**
     * 修改意见反馈
     */
    @GetMapping("/edit/{feedbackId}")
    public String edit(@PathVariable("feedbackId") Long feedbackId, ModelMap mmap) {
        Feedback feedback = feedbackService.selectFeedbackById(feedbackId);
        FeedbackVo feedbackVo = toVo(feedback);
        mmap.put("feedback", feedbackVo);
        return prefix + "/edit";
    }

    /**
     * 修改保存意见反馈
     */
    @RequiresPermissions("catering:feedback:edit")
    @Log(title = "意见反馈", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Feedback feedback) {
        return toAjax(feedbackService.updateFeedback(feedback));
    }

    /**
     * 删除意见反馈
     */
    @RequiresPermissions("catering:feedback:remove")
    @Log(title = "意见反馈", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(feedbackService.deleteFeedbackByIds(ids));
    }

    public FeedbackVo toVo(Feedback feedback) {
        FeedbackVo feedbackVo = new FeedbackVo();
        BeanUtils.copyProperties(feedback, feedbackVo);
        if (feedback.getUserId() != null) {
            SysUser user = userService.selectUserById(feedback.getUserId());
            feedbackVo.setUser(user);
        }
        if (feedback.getRestaurantId() != null) {
            Restaurant restaurant = restaurantService.selectRestaurantById(feedback.getRestaurantId());
            feedbackVo.setRestaurant(restaurant);
        }
        return feedbackVo;
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
