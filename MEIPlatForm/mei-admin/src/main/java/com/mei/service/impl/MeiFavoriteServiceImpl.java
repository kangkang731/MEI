package com.mei.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mei.mapper.MeiFavoriteMapper;
import com.mei.domain.MeiFavorite;
import com.mei.service.IMeiFavoriteService;

/**
 * MEI-政策收藏Service业务层处理
 * 
 * @author mei
 * @date 2026-04-28
 */
@Service
public class MeiFavoriteServiceImpl implements IMeiFavoriteService 
{
    @Autowired
    private MeiFavoriteMapper meiFavoriteMapper;

    /**
     * 查询MEI-政策收藏
     * 
     * @param id MEI-政策收藏主键
     * @return MEI-政策收藏
     */
    @Override
    public MeiFavorite selectMeiFavoriteById(Long id)
    {
        return meiFavoriteMapper.selectMeiFavoriteById(id);
    }

    /**
     * 查询MEI-政策收藏列表
     * 
     * @param meiFavorite MEI-政策收藏
     * @return MEI-政策收藏
     */
    @Override
    public List<MeiFavorite> selectMeiFavoriteList(MeiFavorite meiFavorite)
    {
        return meiFavoriteMapper.selectMeiFavoriteList(meiFavorite);
    }

    /**
     * 根据用户ID和政策ID查询收藏
     * 
     * @param userId 用户ID
     * @param policyId 政策ID
     * @return MEI-政策收藏
     */
    @Override
    public MeiFavorite selectMeiFavoriteByUserAndPolicy(Long userId, Long policyId)
    {
        return meiFavoriteMapper.selectMeiFavoriteByUserAndPolicy(userId, policyId);
    }

    /**
     * 新增MEI-政策收藏
     * 
     * @param meiFavorite MEI-政策收藏
     * @return 结果
     */
    @Override
    public int insertMeiFavorite(MeiFavorite meiFavorite)
    {
        return meiFavoriteMapper.insertMeiFavorite(meiFavorite);
    }

    /**
     * 修改MEI-政策收藏
     * 
     * @param meiFavorite MEI-政策收藏
     * @return 结果
     */
    @Override
    public int updateMeiFavorite(MeiFavorite meiFavorite)
    {
        return meiFavoriteMapper.updateMeiFavorite(meiFavorite);
    }

    /**
     * 批量删除MEI-政策收藏
     * 
     * @param ids 需要删除的MEI-政策收藏主键
     * @return 结果
     */
    @Override
    public int deleteMeiFavoriteByIds(Long[] ids)
    {
        return meiFavoriteMapper.deleteMeiFavoriteByIds(ids);
    }

    /**
     * 删除MEI-政策收藏信息
     * 
     * @param id MEI-政策收藏主键
     * @return 结果
     */
    @Override
    public int deleteMeiFavoriteById(Long id)
    {
        return meiFavoriteMapper.deleteMeiFavoriteById(id);
    }
}
