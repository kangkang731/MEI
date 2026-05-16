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
import com.mei.domain.MeiAchievement;
import com.mei.service.IMeiAchievementService;
import com.mei.common.utils.poi.ExcelUtil;
import com.mei.common.core.page.TableDataInfo;

/**
 * MEI-成果业务Controller
 * 
 * @author mei
 * @date 2026-04-10
 */
@RestController
@RequestMapping("/mei/achievement")
public class MeiAchievementController extends BaseController
{
    @Autowired
    private IMeiAchievementService meiAchievementService;

    /**
     * 查询成果列表
     */
    @PreAuthorize("@ss.hasPermi('mei:achievement:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeiAchievement meiAchievement)
    {
        startPage();
        List<MeiAchievement> list = meiAchievementService.selectMeiAchievementList(meiAchievement);
        return getDataTable(list);
    }

    /**
     * 导出成果列表
     */
    @PreAuthorize("@ss.hasPermi('mei:achievement:export')")
    @Log(title = "成果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeiAchievement meiAchievement)
    {
        List<MeiAchievement> list = meiAchievementService.selectMeiAchievementList(meiAchievement);
        ExcelUtil<MeiAchievement> util = new ExcelUtil<MeiAchievement>(MeiAchievement.class);
        util.exportExcel(response, list, "成果数据");
    }

    /**
     * 获取成果详细信息
     */
    @PreAuthorize("@ss.hasPermi('mei:achievement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(meiAchievementService.selectMeiAchievementById(id));
    }

    /**
     * 新增成果
     */
    @PreAuthorize("@ss.hasPermi('mei:achievement:add')")
    @Log(title = "成果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MeiAchievement meiAchievement)
    {
        return toAjax(meiAchievementService.insertMeiAchievement(meiAchievement));
    }

    /**
     * 修改成果
     */
    @PreAuthorize("@ss.hasPermi('mei:achievement:edit')")
    @Log(title = "成果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeiAchievement meiAchievement)
    {
        return toAjax(meiAchievementService.updateMeiAchievement(meiAchievement));
    }

    /**
     * 删除成果
     */
    @PreAuthorize("@ss.hasPermi('mei:achievement:remove')")
    @Log(title = "成果", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(meiAchievementService.deleteMeiAchievementByIds(ids));
    }
}
