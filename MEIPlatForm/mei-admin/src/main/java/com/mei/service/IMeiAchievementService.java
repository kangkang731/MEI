package com.mei.service;

import java.util.List;
import com.mei.domain.MeiAchievement;

/**
 * 成果Service接口
 * 
 * @author mei
 * @date 2026-04-10
 */
public interface IMeiAchievementService 
{
    /**
     * 查询成果
     * 
     * @param id 成果主键
     * @return 成果
     */
    public MeiAchievement selectMeiAchievementById(String id);

    /**
     * 查询成果列表
     * 
     * @param meiAchievement 成果
     * @return 成果集合
     */
    public List<MeiAchievement> selectMeiAchievementList(MeiAchievement meiAchievement);

    /**
     * 新增成果
     * 
     * @param meiAchievement 成果
     * @return 结果
     */
    public int insertMeiAchievement(MeiAchievement meiAchievement);

    /**
     * 修改成果
     * 
     * @param meiAchievement 成果
     * @return 结果
     */
    public int updateMeiAchievement(MeiAchievement meiAchievement);

    /**
     * 批量删除成果
     * 
     * @param ids 需要删除的成果主键集合
     * @return 结果
     */
    public int deleteMeiAchievementByIds(Long[] ids);

    /**
     * 删除成果信息
     * 
     * @param id 成果主键
     * @return 结果
     */
    public int deleteMeiAchievementById(String id);
}
