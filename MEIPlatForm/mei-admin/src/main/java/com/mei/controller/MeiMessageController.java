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
import com.mei.domain.MeiMessage;
import com.mei.service.IMeiMessageService;
import com.mei.common.utils.poi.ExcelUtil;
import com.mei.common.core.page.TableDataInfo;

/**
 * MEI-平台互动交流Controller
 * 
 * @author mei
 * @date 2026-04-11
 */
@RestController
@RequestMapping("/mei/message")
public class MeiMessageController extends BaseController
{
    @Autowired
    private IMeiMessageService meiMessageService;

    /**
     * 查询MEI-平台互动交流列表
     */
    @PreAuthorize("@ss.hasPermi('mei:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(MeiMessage meiMessage)
    {
        startPage();
        List<MeiMessage> list = meiMessageService.selectMeiMessageList(meiMessage);
        return getDataTable(list);
    }

    /**
     * 导出MEI-平台互动交流列表
     */
    @PreAuthorize("@ss.hasPermi('mei:message:export')")
    @Log(title = "MEI-平台互动交流", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeiMessage meiMessage)
    {
        List<MeiMessage> list = meiMessageService.selectMeiMessageList(meiMessage);
        ExcelUtil<MeiMessage> util = new ExcelUtil<MeiMessage>(MeiMessage.class);
        util.exportExcel(response, list, "MEI-平台互动交流数据");
    }

    /**
     * 获取MEI-平台互动交流详细信息
     */
    @PreAuthorize("@ss.hasPermi('mei:message:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(meiMessageService.selectMeiMessageById(id));
    }

    /**
     * 新增MEI-平台互动交流
     */
    @PreAuthorize("@ss.hasPermi('mei:message:add')")
    @Log(title = "MEI-平台互动交流", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MeiMessage meiMessage)
    {
        return toAjax(meiMessageService.insertMeiMessage(meiMessage));
    }

    /**
     * 修改MEI-平台互动交流
     */
    @PreAuthorize("@ss.hasPermi('mei:message:edit')")
    @Log(title = "MEI-平台互动交流", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeiMessage meiMessage)
    {
        return toAjax(meiMessageService.updateMeiMessage(meiMessage));
    }

    /**
     * 删除MEI-平台互动交流
     */
    @PreAuthorize("@ss.hasPermi('mei:message:remove')")
    @Log(title = "MEI-平台互动交流", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(meiMessageService.deleteMeiMessageByIds(ids));
    }
}
