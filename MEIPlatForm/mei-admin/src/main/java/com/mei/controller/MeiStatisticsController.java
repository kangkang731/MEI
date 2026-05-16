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
import com.mei.domain.MeiStatistics;
import com.mei.service.IMeiStatisticsService;
import com.mei.common.utils.poi.ExcelUtil;
import com.mei.common.core.page.TableDataInfo;

/**
 * MEI-双创数据统计总Controller
 * 
 * @author mei
 * @date 2026-04-11
 */
@RestController
@RequestMapping("/mei/statistics")
public class MeiStatisticsController extends BaseController
{
    @Autowired
    private IMeiStatisticsService meiStatisticsService;

    /**
     * 查询MEI-双创数据统计总列表
     */
    @PreAuthorize("@ss.hasPermi('mei:statistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeiStatistics meiStatistics)
    {
        startPage();
        List<MeiStatistics> list = meiStatisticsService.selectMeiStatisticsList(meiStatistics);
        return getDataTable(list);
    }

    /**
     * 导出MEI-双创数据统计总列表
     */
    @PreAuthorize("@ss.hasPermi('mei:statistics:export')")
    @Log(title = "MEI-双创数据统计总", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeiStatistics meiStatistics)
    {
        List<MeiStatistics> list = meiStatisticsService.selectMeiStatisticsList(meiStatistics);
        ExcelUtil<MeiStatistics> util = new ExcelUtil<MeiStatistics>(MeiStatistics.class);
        util.exportExcel(response, list, "MEI-双创数据统计总数据");
    }

    /**
     * 获取MEI-双创数据统计总详细信息
     */
    @PreAuthorize("@ss.hasPermi('mei:statistics:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(meiStatisticsService.selectMeiStatisticsById(id));
    }

    /**
     * 新增MEI-双创数据统计总
     */
    @PreAuthorize("@ss.hasPermi('mei:statistics:add')")
    @Log(title = "MEI-双创数据统计总", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MeiStatistics meiStatistics)
    {
        return toAjax(meiStatisticsService.insertMeiStatistics(meiStatistics));
    }

    /**
     * 修改MEI-双创数据统计总
     */
    @PreAuthorize("@ss.hasPermi('mei:statistics:edit')")
    @Log(title = "MEI-双创数据统计总", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeiStatistics meiStatistics)
    {
        return toAjax(meiStatisticsService.updateMeiStatistics(meiStatistics));
    }

    /**
     * 删除MEI-双创数据统计总
     */
    @PreAuthorize("@ss.hasPermi('mei:statistics:remove')")
    @Log(title = "MEI-双创数据统计总", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(meiStatisticsService.deleteMeiStatisticsByIds(ids));
    }
}
