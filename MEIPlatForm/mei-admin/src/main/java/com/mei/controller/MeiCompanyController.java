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
import com.mei.domain.MeiCompany;
import com.mei.service.IMeiCompanyService;
import com.mei.common.utils.poi.ExcelUtil;
import com.mei.common.core.page.TableDataInfo;

/**
 * MEI-入驻企业信息Controller
 * 
 * @author mei
 * @date 2026-04-11
 */
@RestController
@RequestMapping("/mei/company")
public class MeiCompanyController extends BaseController
{
    @Autowired
    private IMeiCompanyService meiCompanyService;

    /**
     * 查询MEI-入驻企业信息列表
     */
    @PreAuthorize("@ss.hasPermi('mei:company:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeiCompany meiCompany)
    {
        startPage();
        List<MeiCompany> list = meiCompanyService.selectMeiCompanyList(meiCompany);
        return getDataTable(list);
    }

    /**
     * 导出MEI-入驻企业信息列表
     */
    @PreAuthorize("@ss.hasPermi('mei:company:export')")
    @Log(title = "MEI-入驻企业信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeiCompany meiCompany)
    {
        List<MeiCompany> list = meiCompanyService.selectMeiCompanyList(meiCompany);
        ExcelUtil<MeiCompany> util = new ExcelUtil<MeiCompany>(MeiCompany.class);
        util.exportExcel(response, list, "MEI-入驻企业信息数据");
    }

    /**
     * 获取MEI-入驻企业信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('mei:company:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(meiCompanyService.selectMeiCompanyById(id));
    }

    /**
     * 新增MEI-入驻企业信息
     */
    @PreAuthorize("@ss.hasPermi('mei:company:add')")
    @Log(title = "MEI-入驻企业信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MeiCompany meiCompany)
    {
        return toAjax(meiCompanyService.insertMeiCompany(meiCompany));
    }

    /**
     * 修改MEI-入驻企业信息
     */
    @PreAuthorize("@ss.hasPermi('mei:company:edit')")
    @Log(title = "MEI-入驻企业信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeiCompany meiCompany)
    {
        return toAjax(meiCompanyService.updateMeiCompany(meiCompany));
    }

    /**
     * 删除MEI-入驻企业信息
     */
    @PreAuthorize("@ss.hasPermi('mei:company:remove')")
    @Log(title = "MEI-入驻企业信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(meiCompanyService.deleteMeiCompanyByIds(ids));
    }
}
