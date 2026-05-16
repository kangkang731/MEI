package com.mei.service;

import java.util.List;
import com.mei.domain.MeiFavorite;

/**
 * MEI-政策收藏Service接口
 * 
 * @author mei
 * @date 2026-04-28
 */
public interface IMeiFavoriteService 
{
    /**
     * 查询MEI-政策收藏
     * 
     * @param id MEI-政策收藏主键
     * @return MEI-政策收藏
     */
    public MeiFavorite selectMeiFavoriteById(Long id);

    /**
     * 查询MEI-政策收藏列表
     * 
     * @param meiFavorite MEI-政策收藏
     * @return MEI-政策收藏集合
     */
    public List<MeiFavorite> selectMeiFavoriteList(MeiFavorite meiFavorite);

    /**
     * 根据用户ID和政策ID查询收藏
     * 
     * @param userId 用户ID
     * @param policyId 政策ID
     * @return MEI-政策收藏
     */
    public MeiFavorite selectMeiFavoriteByUserAndPolicy(Long userId, Long policyId);

    /**
     * 新增MEI-政策收藏
     * 
     * @param meiFavorite MEI-政策收藏
     * @return 结果
     */
    public int insertMeiFavorite(MeiFavorite meiFavorite);

    /**
     * 修改MEI-政策收藏
     * 
     * @param meiFavorite MEI-政策收藏
     * @return 结果
     */
    public int updateMeiFavorite(MeiFavorite meiFavorite);

    /**
     * 批量删除MEI-政策收藏
     * 
     * @param ids 需要删除的MEI-政策收藏主键集合
     * @return 结果
     */
    public int deleteMeiFavoriteByIds(Long[] ids);

    /**
     * 删除MEI-政策收藏信息
     * 
     * @param id MEI-政策收藏主键
     * @return 结果
     */
    public int deleteMeiFavoriteById(Long id);
}
