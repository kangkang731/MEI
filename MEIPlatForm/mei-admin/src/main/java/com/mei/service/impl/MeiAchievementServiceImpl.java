package com.mei.service.impl;

import java.util.List;
import com.mei.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mei.mapper.MeiAchievementMapper;
import com.mei.domain.MeiAchievement;
import com.mei.service.IMeiAchievementService;

/**
 * 成果Service业务层处理
 * 
 * @author mei
 * @date 2026-04-10
 */
@Service
public class MeiAchievementServiceImpl implements IMeiAchievementService 
{
    @Autowired
    private MeiAchievementMapper meiAchievementMapper;

    /**
     * 查询成果
     * 
     * @param id 成果主键
     * @return 成果
     */
    @Override
    public MeiAchievement selectMeiAchievementById(String id)
    {
        return meiAchievementMapper.selectMeiAchievementById(id);
    }

    /**
     * 查询成果列表
     * 
     * @param meiAchievement 成果
     * @return 成果
     */
    @Override
    public List<MeiAchievement> selectMeiAchievementList(MeiAchievement meiAchievement)
    {
        return meiAchievementMapper.selectMeiAchievementList(meiAchievement);
    }

    /**
     * 新增成果
     * 
     * @param meiAchievement 成果
     * @return 结果
     */
    @Override
    public int insertMeiAchievement(MeiAchievement meiAchievement)
    {
        meiAchievement.setCreateTime(DateUtils.getNowDate());
        return meiAchievementMapper.insertMeiAchievement(meiAchievement);
    }

    /**
     * 修改成果
     * 
     * @param meiAchievement 成果
     * @return 结果
     */
    @Override
    public int updateMeiAchievement(MeiAchievement meiAchievement)
    {
        return meiAchievementMapper.updateMeiAchievement(meiAchievement);
    }

    /**
     * 批量删除成果
     * 
     * @param ids 需要删除的成果主键
     * @return 结果
     */
    @Override
    public int deleteMeiAchievementByIds(Long[] ids)
    {
        return meiAchievementMapper.deleteMeiAchievementByIds(ids);
    }

    /**
     * 删除成果信息
     * 
     * @param id 成果主键
     * @return 结果
     */
    @Override
    public int deleteMeiAchievementById(String id)
    {
        return meiAchievementMapper.deleteMeiAchievementById(id);
    }
}
