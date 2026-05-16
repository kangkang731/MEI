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
import com.mei.domain.MeiProject;
import com.mei.service.IMeiProjectService;
import com.mei.common.utils.poi.ExcelUtil;
import com.mei.common.core.page.TableDataInfo;

/**
 * MEI-创新创业项目申报Controller
 * 
 * @author mei
 * @date 2026-04-11
 */
@RestController
@RequestMapping("/mei/project")
public class MeiProjectController extends BaseController
{
    @Autowired
    private IMeiProjectService meiProjectService;

    /**
     * 查询MEI-创新创业项目申报列表
     */
    @PreAuthorize("@ss.hasPermi('mei:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeiProject meiProject)
    {
        startPage();
        List<MeiProject> list = meiProjectService.selectMeiProjectList(meiProject);
        return getDataTable(list);
    }

    /**
     * 导出MEI-创新创业项目申报列表
     */
    @PreAuthorize("@ss.hasPermi('mei:project:export')")
    @Log(title = "MEI-创新创业项目申报", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeiProject meiProject)
    {
        List<MeiProject> list = meiProjectService.selectMeiProjectList(meiProject);
        ExcelUtil<MeiProject> util = new ExcelUtil<MeiProject>(MeiProject.class);
        util.exportExcel(response, list, "MEI-创新创业项目申报数据");
    }

    /**
     * 获取MEI-创新创业项目申报详细信息
     */
    @PreAuthorize("@ss.hasPermi('mei:project:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(meiProjectService.selectMeiProjectById(id));
    }

    /**
     * 新增MEI-创新创业项目申报
     */
    @PreAuthorize("@ss.hasPermi('mei:project:add')")
    @Log(title = "MEI-创新创业项目申报", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MeiProject meiProject)
    {
        return toAjax(meiProjectService.insertMeiProject(meiProject));
    }

    /**
     * 修改MEI-创新创业项目申报
     */
    @PreAuthorize("@ss.hasPermi('mei:project:edit')")
    @Log(title = "MEI-创新创业项目申报", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeiProject meiProject)
    {
        return toAjax(meiProjectService.updateMeiProject(meiProject));
    }

    /**
     * 删除MEI-创新创业项目申报
     */
    @PreAuthorize("@ss.hasPermi('mei:project:remove')")
    @Log(title = "MEI-创新创业项目申报", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(meiProjectService.deleteMeiProjectByIds(ids));
    }
}
