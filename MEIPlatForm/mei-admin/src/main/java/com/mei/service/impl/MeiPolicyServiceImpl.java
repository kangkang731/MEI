package com.mei.service.impl;

import java.util.List;
import com.mei.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mei.mapper.MeiPolicyMapper;
import com.mei.domain.MeiPolicy;
import com.mei.service.IMeiPolicyService;

/**
 * MEI-政策公告信息Service业务层处理
 * 
 * @author mei
 * @date 2026-04-11
 */
@Service
public class MeiPolicyServiceImpl implements IMeiPolicyService 
{
    @Autowired
    private MeiPolicyMapper meiPolicyMapper;

    /**
     * 查询MEI-政策公告信息
     * 
     * @param id MEI-政策公告信息主键
     * @return MEI-政策公告信息
     */
    @Override
    public MeiPolicy selectMeiPolicyById(Long id)
    {
        return meiPolicyMapper.selectMeiPolicyById(id);
    }

    /**
     * 查询MEI-政策公告信息列表
     * 
     * @param meiPolicy MEI-政策公告信息
     * @return MEI-政策公告信息
     */
    @Override
    public List<MeiPolicy> selectMeiPolicyList(MeiPolicy meiPolicy)
    {
        return meiPolicyMapper.selectMeiPolicyList(meiPolicy);
    }

    /**
     * 新增MEI-政策公告信息
     * 
     * @param meiPolicy MEI-政策公告信息
     * @return 结果
     */
    @Override
    public int insertMeiPolicy(MeiPolicy meiPolicy)
    {
        meiPolicy.setCreateTime(DateUtils.getNowDate());
        return meiPolicyMapper.insertMeiPolicy(meiPolicy);
    }

    /**
     * 修改MEI-政策公告信息
     * 
     * @param meiPolicy MEI-政策公告信息
     * @return 结果
     */
    @Override
    public int updateMeiPolicy(MeiPolicy meiPolicy)
    {
        return meiPolicyMapper.updateMeiPolicy(meiPolicy);
    }

    /**
     * 批量删除MEI-政策公告信息
     * 
     * @param ids 需要删除的MEI-政策公告信息主键
     * @return 结果
     */
    @Override
    public int deleteMeiPolicyByIds(Long[] ids)
    {
        return meiPolicyMapper.deleteMeiPolicyByIds(ids);
    }

    /**
     * 删除MEI-政策公告信息信息
     * 
     * @param id MEI-政策公告信息主键
     * @return 结果
     */
    @Override
    public int deleteMeiPolicyById(Long id)
    {
        return meiPolicyMapper.deleteMeiPolicyById(id);
    }
}
