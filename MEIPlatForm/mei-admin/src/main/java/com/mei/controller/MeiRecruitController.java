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
import com.mei.domain.MeiRecruit;
import com.mei.service.IMeiRecruitService;
import com.mei.common.utils.poi.ExcelUtil;
import com.mei.common.core.page.TableDataInfo;

/**
 * MEI-人才招聘信息Controller
 * 
 * @author mei
 * @date 2026-04-11
 */
@RestController
@RequestMapping("/mei/recruit")
public class MeiRecruitController extends BaseController
{
    @Autowired
    private IMeiRecruitService meiRecruitService;

    /**
     * 查询MEI-人才招聘信息列表
     */
    @PreAuthorize("@ss.hasPermi('mei:recruit:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeiRecruit meiRecruit)
    {
        startPage();
        List<MeiRecruit> list = meiRecruitService.selectMeiRecruitList(meiRecruit);
        return getDataTable(list);
    }

    /**
     * 导出MEI-人才招聘信息列表
     */
    @PreAuthorize("@ss.hasPermi('mei:recruit:export')")
    @Log(title = "MEI-人才招聘信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeiRecruit meiRecruit)
    {
        List<MeiRecruit> list = meiRecruitService.selectMeiRecruitList(meiRecruit);
        ExcelUtil<MeiRecruit> util = new ExcelUtil<MeiRecruit>(MeiRecruit.class);
        util.exportExcel(response, list, "MEI-人才招聘信息数据");
    }

    /**
     * 获取MEI-人才招聘信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('mei:recruit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(meiRecruitService.selectMeiRecruitById(id));
    }

    /**
     * 新增MEI-人才招聘信息
     */
    @PreAuthorize("@ss.hasPermi('mei:recruit:add')")
    @Log(title = "MEI-人才招聘信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MeiRecruit meiRecruit)
    {
        return toAjax(meiRecruitService.insertMeiRecruit(meiRecruit));
    }

    /**
     * 修改MEI-人才招聘信息
     */
    @PreAuthorize("@ss.hasPermi('mei:recruit:edit')")
    @Log(title = "MEI-人才招聘信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeiRecruit meiRecruit)
    {
        return toAjax(meiRecruitService.updateMeiRecruit(meiRecruit));
    }

    /**
     * 删除MEI-人才招聘信息
     */
    @PreAuthorize("@ss.hasPermi('mei:recruit:remove')")
    @Log(title = "MEI-人才招聘信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(meiRecruitService.deleteMeiRecruitByIds(ids));
    }
}
