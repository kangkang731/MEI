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
import com.mei.domain.MeiPolicy;
import com.mei.service.IMeiPolicyService;
import com.mei.common.utils.poi.ExcelUtil;
import com.mei.common.core.page.TableDataInfo;

/**
 * MEI-政策公告信息Controller
 * 
 * @author mei
 * @date 2026-04-11
 */
@RestController
@RequestMapping("/mei/policy")
public class MeiPolicyController extends BaseController
{
    @Autowired
    private IMeiPolicyService meiPolicyService;

    /**
     * 查询MEI-政策公告信息列表
     */
    @PreAuthorize("@ss.hasPermi('mei:policy:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeiPolicy meiPolicy)
    {
        startPage();
        List<MeiPolicy> list = meiPolicyService.selectMeiPolicyList(meiPolicy);
        return getDataTable(list);
    }

    /**
     * 导出MEI-政策公告信息列表
     */
    @PreAuthorize("@ss.hasPermi('mei:policy:export')")
    @Log(title = "MEI-政策公告信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeiPolicy meiPolicy)
    {
        List<MeiPolicy> list = meiPolicyService.selectMeiPolicyList(meiPolicy);
        ExcelUtil<MeiPolicy> util = new ExcelUtil<MeiPolicy>(MeiPolicy.class);
        util.exportExcel(response, list, "MEI-政策公告信息数据");
    }

    /**
     * 获取MEI-政策公告信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('mei:policy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(meiPolicyService.selectMeiPolicyById(id));
    }

    /**
     * 新增MEI-政策公告信息
     */
    @PreAuthorize("@ss.hasPermi('mei:policy:add')")
    @Log(title = "MEI-政策公告信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MeiPolicy meiPolicy)
    {
        return toAjax(meiPolicyService.insertMeiPolicy(meiPolicy));
    }

    /**
     * 修改MEI-政策公告信息
     */
    @PreAuthorize("@ss.hasPermi('mei:policy:edit')")
    @Log(title = "MEI-政策公告信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeiPolicy meiPolicy)
    {
        return toAjax(meiPolicyService.updateMeiPolicy(meiPolicy));
    }

    /**
     * 删除MEI-政策公告信息
     */
    @PreAuthorize("@ss.hasPermi('mei:policy:remove')")
    @Log(title = "MEI-政策公告信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(meiPolicyService.deleteMeiPolicyByIds(ids));
    }
}
