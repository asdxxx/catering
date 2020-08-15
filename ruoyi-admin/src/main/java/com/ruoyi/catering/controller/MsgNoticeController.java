package com.ruoyi.catering.controller;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ruoyi.catering.domain.Feedback;
import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.vo.FeedbackVo;
import com.ruoyi.catering.vo.MsgNoticeVo;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.SysUserRole;
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
import com.ruoyi.catering.domain.MsgNotice;
import com.ruoyi.catering.service.IMsgNoticeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 消息通知Controller
 *
 * @author lsy
 * @date 2020-08-11
 */
@Controller
@RequestMapping("/catering/msgNotice")
public class MsgNoticeController extends BaseController {
    private String prefix = "catering/msgNotice";

    @Autowired
    private IMsgNoticeService msgNoticeService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysUserService userService;

    @RequiresPermissions("catering:msgNotice:view")
    @GetMapping()
    public String msgNotice() {
        return prefix + "/msgNotice";
    }

    /**
     * 查询消息通知列表
     */
    @RequiresPermissions("catering:msgNotice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MsgNotice msgNotice) {
        startPage();
        List<MsgNotice> list = msgNoticeService.selectMsgNoticeList(msgNotice);
        List<MsgNoticeVo> msgNoticeVos = new ArrayList<>();
        for (MsgNotice mn : list) {
            MsgNoticeVo msgNoticeVo = toVo(mn);
            msgNoticeVos.add(msgNoticeVo);
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(msgNoticeVos);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
        //return getDataTable(list);
    }

    /**
     * 导出消息通知列表
     */
    @RequiresPermissions("catering:msgNotice:export")
    @Log(title = "消息通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MsgNotice msgNotice) {
        List<MsgNotice> list = msgNoticeService.selectMsgNoticeList(msgNotice);
        ExcelUtil<MsgNotice> util = new ExcelUtil<MsgNotice>(MsgNotice.class);
        return util.exportExcel(list, "msgNotice");
    }

    /**
     * 新增消息通知
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存消息通知
     */
    @RequiresPermissions("catering:msgNotice:add")
    @Log(title = "消息通知", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MsgNotice msgNotice) {
        SysUser user = userService.selectUserById(msgNotice.getUserId());
        List<SysRole> roles = user.getRoles();
        if (roles != null && roles.size() > 0) {
            if (roles.get(0).getRoleKey().contains("recycle")) {
                msgNotice.setType(2);
            } else {
                msgNotice.setType(1);
            }
        }
        msgNotice.setHasRead("N");
        return toAjax(msgNoticeService.insertMsgNotice(msgNotice));
    }

    /**
     * 修改消息通知
     */
    @GetMapping("/edit/{noticeId}")
    public String edit(@PathVariable("noticeId") Long noticeId, ModelMap mmap) {
        MsgNotice msgNotice = msgNoticeService.selectMsgNoticeById(noticeId);
        MsgNoticeVo msgNoticeVo = toVo(msgNotice);
        mmap.put("msgNotice", msgNoticeVo);
        return prefix + "/edit";
    }

    /**
     * 修改保存消息通知
     */
    @RequiresPermissions("catering:msgNotice:edit")
    @Log(title = "消息通知", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MsgNotice msgNotice) {
        MsgNotice old = msgNoticeService.selectMsgNoticeById(msgNotice.getNoticeId());
        if (!old.getUserId().equals(msgNotice.getUserId())) {
            SysUser user = userService.selectUserById(msgNotice.getUserId());
            List<SysRole> roles = user.getRoles();
            if (roles != null && roles.size() > 0) {
                if (roles.get(0).getRoleKey().contains("recycle")) {
                    msgNotice.setType(2);
                } else {
                    msgNotice.setType(1);
                }
            }
        }
        return toAjax(msgNoticeService.updateMsgNotice(msgNotice));
    }

    /**
     * 删除消息通知
     */
    @RequiresPermissions("catering:msgNotice:remove")
    @Log(title = "消息通知", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(msgNoticeService.deleteMsgNoticeByIds(ids));
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

    public MsgNoticeVo toVo(MsgNotice msgNotice) {
        MsgNoticeVo msgNoticeVo = new MsgNoticeVo();
        BeanUtils.copyProperties(msgNotice, msgNoticeVo);
        if (msgNotice.getUserId() != null) {
            SysUser user = userService.selectUserById(msgNotice.getUserId());
            msgNoticeVo.setUser(user);
        }
        return msgNoticeVo;
    }
}
