package com.mei.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mei.common.annotation.Log;
import com.mei.common.core.controller.BaseController;
import com.mei.common.core.domain.AjaxResult;
import com.mei.common.enums.BusinessType;
import com.mei.domain.MeiConsult;
import com.mei.service.IMeiConsultService;
import com.mei.common.utils.poi.ExcelUtil;
import com.mei.common.core.page.TableDataInfo;

/**
 * MEI-在线咨询服务Controller
 * 
 * @author mei
 * @date 2026-04-11
 */
@RestController
@RequestMapping("/mei/consult")
public class MeiConsultController extends BaseController
{
    @Autowired
    private IMeiConsultService meiConsultService;

    /**
     * 查询MEI-在线咨询服务列表
     */
    @PreAuthorize("@ss.hasPermi('mei:consult:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeiConsult meiConsult)
    {
        startPage();
        List<MeiConsult> list = meiConsultService.selectMeiConsultList(meiConsult);
        return getDataTable(list);
    }

    /**
     * 导出MEI-在线咨询服务列表
     */
    @PreAuthorize("@ss.hasPermi('mei:consult:export')")
    @Log(title = "MEI-在线咨询服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeiConsult meiConsult)
    {
        List<MeiConsult> list = meiConsultService.selectMeiConsultList(meiConsult);
        ExcelUtil<MeiConsult> util = new ExcelUtil<MeiConsult>(MeiConsult.class);
        util.exportExcel(response, list, "MEI-在线咨询服务数据");
    }

    /**
     * 获取MEI-在线咨询服务详细信息
     */
    @PreAuthorize("@ss.hasPermi('mei:consult:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(meiConsultService.selectMeiConsultById(id));
    }

    /**
     * 新增MEI-在线咨询服务
     */
    @PreAuthorize("@ss.hasPermi('mei:consult:add')")
    @Log(title = "MEI-在线咨询服务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MeiConsult meiConsult)
    {
        return toAjax(meiConsultService.insertMeiConsult(meiConsult));
    }

    /**
     * 修改MEI-在线咨询服务
     */
    @PreAuthorize("@ss.hasPermi('mei:consult:edit')")
    @Log(title = "MEI-在线咨询服务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeiConsult meiConsult)
    {
        // 如果回复内容不为空，自动将状态设为已回复
        if (meiConsult.getReply() != null && !meiConsult.getReply().trim().isEmpty()) {
            meiConsult.setStatus("1");
        }
        return toAjax(meiConsultService.updateMeiConsult(meiConsult));
    }

    /**
     * 删除MEI-在线咨询服务
     */
    @PreAuthorize("@ss.hasPermi('mei:consult:remove')")
    @Log(title = "MEI-在线咨询服务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(meiConsultService.deleteMeiConsultByIds(ids));
    }
}
