package com.ruoyi.catering.controller;

import com.github.pagehelper.PageInfo;
import com.ruoyi.catering.domain.Garbage;
import com.ruoyi.catering.domain.SmsRecord;
import com.ruoyi.catering.service.IGarbageService;
import com.ruoyi.catering.service.ISmsRecordService;
import com.ruoyi.catering.vo.SmsRecordVo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信发送记录Controller
 *
 * @author lsy
 * @date 2020-07-08
 */
@Controller
@RequestMapping("/catering/smsRecord")
public class SmsRecordController extends BaseController {
    private String prefix = "catering/smsRecord";

    @Autowired
    private ISmsRecordService smsRecordService;
    @Autowired
    private IGarbageService garbageService;

    @RequiresPermissions("catering:smsRecord:view")
    @GetMapping()
    public String smsRecord() {
        return prefix + "/smsRecord";
    }

    /**
     * 查询短信发送记录列表
     */
    @RequiresPermissions("catering:smsRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SmsRecord smsRecord) {
        startPage();
        List<SmsRecord> list = smsRecordService.selectSmsRecordList(smsRecord);
        List<SmsRecordVo> smsRecordVos = new ArrayList<>();
        for (SmsRecord sr : list) {
            SmsRecordVo smsRecordVo = toVo(sr);
            smsRecordVos.add(smsRecordVo);
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(smsRecordVos);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
//        return getDataTable(list);
    }

    /**
     * 导出短信发送记录列表
     */
    @RequiresPermissions("catering:smsRecord:export")
    @Log(title = "短信发送记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SmsRecord smsRecord) {
        List<SmsRecord> list = smsRecordService.selectSmsRecordList(smsRecord);
        List<SmsRecordVo> smsRecordVos = new ArrayList<>();
        for (SmsRecord sr : list) {
            SmsRecordVo smsRecordVo = toVo(sr);
            smsRecordVos.add(smsRecordVo);
        }
        ExcelUtil<SmsRecordVo> util = new ExcelUtil<SmsRecordVo>(SmsRecordVo.class);
        return util.exportExcel(smsRecordVos, "短信发送记录");
//        ExcelUtil<SmsRecord> util = new ExcelUtil<SmsRecord>(SmsRecord.class);
//        return util.exportExcel(list, "smsRecord");
    }

    /**
     * 新增短信发送记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存短信发送记录
     */
    @RequiresPermissions("catering:smsRecord:add")
    @Log(title = "短信发送记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SmsRecord smsRecord) {
        return toAjax(smsRecordService.insertSmsRecord(smsRecord));
    }

    /**
     * 修改短信发送记录
     */
    @GetMapping("/edit/{recordId}")
    public String edit(@PathVariable("recordId") Long recordId, ModelMap mmap) {
        SmsRecord smsRecord = smsRecordService.selectSmsRecordById(recordId);
        mmap.put("smsRecord", smsRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存短信发送记录
     */
    @RequiresPermissions("catering:smsRecord:edit")
    @Log(title = "短信发送记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SmsRecord smsRecord) {
        return toAjax(smsRecordService.updateSmsRecord(smsRecord));
    }

    /**
     * 删除短信发送记录
     */
    @RequiresPermissions("catering:smsRecord:remove")
    @Log(title = "短信发送记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(smsRecordService.deleteSmsRecordByIds(ids));
    }

    public SmsRecordVo toVo(SmsRecord smsRecord) {
        SmsRecordVo smsRecordVo = new SmsRecordVo();
        BeanUtils.copyProperties(smsRecord, smsRecordVo);
        if (smsRecord.getGarbageId() != null) {
            Garbage garbage = garbageService.selectGarbageById(smsRecord.getGarbageId());
            smsRecordVo.setGarbage(garbage);
        }
        return smsRecordVo;
    }
}
