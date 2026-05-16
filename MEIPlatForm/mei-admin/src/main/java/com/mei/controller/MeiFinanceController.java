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
import com.mei.domain.MeiFinance;
import com.mei.service.IMeiFinanceService;
import com.mei.common.utils.poi.ExcelUtil;
import com.mei.common.core.page.TableDataInfo;

/**
 * MEI-融资申请服务Controller
 * 
 * @author mei
 * @date 2026-04-11
 */
@RestController
@RequestMapping("/mei/finance")
public class MeiFinanceController extends BaseController
{
    @Autowired
    private IMeiFinanceService meiFinanceService;

    /**
     * 查询MEI-融资申请服务列表
     */
    @PreAuthorize("@ss.hasPermi('mei:finance:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeiFinance meiFinance)
    {
        startPage();
        List<MeiFinance> list = meiFinanceService.selectMeiFinanceList(meiFinance);
        return getDataTable(list);
    }

    /**
     * 导出MEI-融资申请服务列表
     */
    @PreAuthorize("@ss.hasPermi('mei:finance:export')")
    @Log(title = "MEI-融资申请服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeiFinance meiFinance)
    {
        List<MeiFinance> list = meiFinanceService.selectMeiFinanceList(meiFinance);
        ExcelUtil<MeiFinance> util = new ExcelUtil<MeiFinance>(MeiFinance.class);
        util.exportExcel(response, list, "MEI-融资申请服务数据");
    }

    /**
     * 获取MEI-融资申请服务详细信息
     */
    @PreAuthorize("@ss.hasPermi('mei:finance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(meiFinanceService.selectMeiFinanceById(id));
    }

    /**
     * 新增MEI-融资申请服务
     */
    @PreAuthorize("@ss.hasPermi('mei:finance:add')")
    @Log(title = "MEI-融资申请服务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MeiFinance meiFinance)
    {
        return toAjax(meiFinanceService.insertMeiFinance(meiFinance));
    }

    /**
     * 修改MEI-融资申请服务
     */
    @PreAuthorize("@ss.hasPermi('mei:finance:edit')")
    @Log(title = "MEI-融资申请服务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeiFinance meiFinance)
    {
        return toAjax(meiFinanceService.updateMeiFinance(meiFinance));
    }

    /**
     * 删除MEI-融资申请服务
     */
    @PreAuthorize("@ss.hasPermi('mei:finance:remove')")
    @Log(title = "MEI-融资申请服务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(meiFinanceService.deleteMeiFinanceByIds(ids));
    }
}
