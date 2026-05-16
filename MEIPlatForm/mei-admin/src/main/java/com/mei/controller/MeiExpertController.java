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
import com.mei.domain.MeiExpert;
import com.mei.service.IMeiExpertService;
import com.mei.common.utils.poi.ExcelUtil;
import com.mei.common.core.page.TableDataInfo;

/**
 * MEI-双创专家信息Controller
 * 
 * @author mei
 * @date 2026-04-11
 */
@RestController
@RequestMapping("/mei/expert")
public class MeiExpertController extends BaseController
{
    @Autowired
    private IMeiExpertService meiExpertService;

    /**
     * 查询MEI-双创专家信息列表
     */
    @PreAuthorize("@ss.hasPermi('mei:expert:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeiExpert meiExpert)
    {
        startPage();
        List<MeiExpert> list = meiExpertService.selectMeiExpertList(meiExpert);
        return getDataTable(list);
    }

    /**
     * 导出MEI-双创专家信息列表
     */
    @PreAuthorize("@ss.hasPermi('mei:expert:export')")
    @Log(title = "MEI-双创专家信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeiExpert meiExpert)
    {
        List<MeiExpert> list = meiExpertService.selectMeiExpertList(meiExpert);
        ExcelUtil<MeiExpert> util = new ExcelUtil<MeiExpert>(MeiExpert.class);
        util.exportExcel(response, list, "MEI-双创专家信息数据");
    }

    /**
     * 获取MEI-双创专家信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('mei:expert:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(meiExpertService.selectMeiExpertById(id));
    }

    /**
     * 新增MEI-双创专家信息
     */
    @PreAuthorize("@ss.hasPermi('mei:expert:add')")
    @Log(title = "MEI-双创专家信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MeiExpert meiExpert)
    {
        return toAjax(meiExpertService.insertMeiExpert(meiExpert));
    }

    /**
     * 修改MEI-双创专家信息
     */
    @PreAuthorize("@ss.hasPermi('mei:expert:edit')")
    @Log(title = "MEI-双创专家信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeiExpert meiExpert)
    {
        return toAjax(meiExpertService.updateMeiExpert(meiExpert));
    }

    /**
     * 删除MEI-双创专家信息
     */
    @PreAuthorize("@ss.hasPermi('mei:expert:remove')")
    @Log(title = "MEI-双创专家信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(meiExpertService.deleteMeiExpertByIds(ids));
    }
}
