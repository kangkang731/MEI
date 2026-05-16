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
import com.mei.domain.MeiFavorite;
import com.mei.service.IMeiFavoriteService;
import com.mei.common.utils.poi.ExcelUtil;
import com.mei.common.core.page.TableDataInfo;

/**
 * MEI-政策收藏Controller
 * 
 * @author mei
 * @date 2026-04-28
 */
@RestController
@RequestMapping("/favorite")
public class MeiFavoriteController extends BaseController
{
    @Autowired
    private IMeiFavoriteService meiFavoriteService;

    /**
     * 查询MEI-政策收藏列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MeiFavorite meiFavorite)
    {
        startPage();
        List<MeiFavorite> list = meiFavoriteService.selectMeiFavoriteList(meiFavorite);
        return getDataTable(list);
    }

    /**
     * 导出MEI-政策收藏列表
     */
    @Log(title = "MEI-政策收藏", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MeiFavorite meiFavorite)
    {
        List<MeiFavorite> list = meiFavoriteService.selectMeiFavoriteList(meiFavorite);
        ExcelUtil<MeiFavorite> util = new ExcelUtil<MeiFavorite>(MeiFavorite.class);
        util.exportExcel(response, list, "MEI-政策收藏数据");
    }

    /**
     * 获取MEI-政策收藏详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(meiFavoriteService.selectMeiFavoriteById(id));
    }

    /**
     * 新增MEI-政策收藏
     */
    @Log(title = "MEI-政策收藏", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody MeiFavorite meiFavorite)
    {
        // 检查是否已经收藏
        MeiFavorite existFavorite = meiFavoriteService.selectMeiFavoriteByUserAndPolicy(
            meiFavorite.getUserId(), 
            meiFavorite.getPolicyId()
        );
        
        if (existFavorite != null) {
            return error("您已经收藏过该政策了");
        }
        
        return toAjax(meiFavoriteService.insertMeiFavorite(meiFavorite));
    }

    /**
     * 修改MEI-政策收藏
     */
    @Log(title = "MEI-政策收藏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MeiFavorite meiFavorite)
    {
        return toAjax(meiFavoriteService.updateMeiFavorite(meiFavorite));
    }

    /**
     * 删除MEI-政策收藏
     */
    @Log(title = "MEI-政策收藏", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(meiFavoriteService.deleteMeiFavoriteByIds(ids));
    }

    /**
     * 根据用户ID和政策ID删除收藏
     */
    @Log(title = "MEI-政策收藏", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{userId}/{policyId}")
    public AjaxResult removeByUserAndPolicy(@PathVariable Long userId, @PathVariable Long policyId)
    {
        MeiFavorite favorite = meiFavoriteService.selectMeiFavoriteByUserAndPolicy(userId, policyId);
        if (favorite != null) {
            return toAjax(meiFavoriteService.deleteMeiFavoriteById(favorite.getId()));
        }
        return error("收藏记录不存在");
    }

    /**
     * 检查用户是否已收藏某政策
     */
    @GetMapping("/check")
    public AjaxResult checkFavorite(Long userId, Long policyId)
    {
        MeiFavorite favorite = meiFavoriteService.selectMeiFavoriteByUserAndPolicy(userId, policyId);
        return success(favorite != null);
    }
}
