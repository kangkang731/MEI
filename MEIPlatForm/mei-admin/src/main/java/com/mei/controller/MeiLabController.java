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
import com.mei.domain.MeiLab;
import com.mei.service.IMeiLabService;
import com.mei.common.utils.poi.ExcelUtil;
import com.mei.common.core.page.TableDataInfo;

/**
 * MEI-西藏高校实验室Controller
 * 
 * @author mei
 * @date 2026-04-11
 */
@RestController
@RequestMapping("/mei/lab")
public class MeiLabController extends BaseController
{
    @Autowired
    private IMeiLabService meiLabService;

    /**
     * 查询MEI-西藏高校实验室列表
     */
    @PreAuthorize("@ss.hasPermi('mei:lab:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeiLab meiLab)
    {
        startPage();
        List<MeiLab> list = meiLabService.selectMeiLabList(meiLab);
        return getDataTable(list);
    }

    /**
     * 导出MEI-西藏高校实验室列表
     */
    @PreAuthorize("@ss.hasPermi('mei:lab:export')")
    @Log(title = "MEI-西藏高校实验室", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeiLab meiLab)
    {
        List<MeiLab> list = meiLabService.selectMeiLabList(meiLab);
        ExcelUtil<MeiLab> util = new ExcelUtil<MeiLab>(MeiLab.class);
        util.exportExcel(response, list, "MEI-西藏高校实验室数据");
    }

    /**
     * 获取MEI-西藏高校实验室详细信息
     */
    @PreAuthorize("@ss.hasPermi('mei:lab:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(meiLabService.selectMeiLabById(id));
    }

    /**
     * 新增MEI-西藏高校实验室
     */
    @PreAuthorize("@ss.hasPermi('mei:lab:add')")
    @Log(title = "MEI-西藏高校实验室", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MeiLab meiLab)
    {
        return toAjax(meiLabService.insertMeiLab(meiLab));
    }

    /**
     * 修改MEI-西藏高校实验室
     */
    @PreAuthorize("@ss.hasPermi('mei:lab:edit')")
    @Log(title = "MEI-西藏高校实验室", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeiLab meiLab)
    {
        return toAjax(meiLabService.updateMeiLab(meiLab));
    }

    /**
     * 删除MEI-西藏高校实验室
     */
    @PreAuthorize("@ss.hasPermi('mei:lab:remove')")
    @Log(title = "MEI-西藏高校实验室", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(meiLabService.deleteMeiLabByIds(ids));
    }
}
