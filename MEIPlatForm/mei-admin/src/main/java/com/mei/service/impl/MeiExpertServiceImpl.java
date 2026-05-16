package com.mei.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mei.mapper.MeiExpertMapper;
import com.mei.domain.MeiExpert;
import com.mei.service.IMeiExpertService;

/**
 * MEI-双创专家信息Service业务层处理
 * 
 * @author mei
 * @date 2026-04-11
 */
@Service
public class MeiExpertServiceImpl implements IMeiExpertService 
{
    @Autowired
    private MeiExpertMapper meiExpertMapper;

    /**
     * 查询MEI-双创专家信息
     * 
     * @param id MEI-双创专家信息主键
     * @return MEI-双创专家信息
     */
    @Override
    public MeiExpert selectMeiExpertById(Long id)
    {
        return meiExpertMapper.selectMeiExpertById(id);
    }

    /**
     * 查询MEI-双创专家信息列表
     * 
     * @param meiExpert MEI-双创专家信息
     * @return MEI-双创专家信息
     */
    @Override
    public List<MeiExpert> selectMeiExpertList(MeiExpert meiExpert)
    {
        return meiExpertMapper.selectMeiExpertList(meiExpert);
    }

    /**
     * 新增MEI-双创专家信息
     * 
     * @param meiExpert MEI-双创专家信息
     * @return 结果
     */
    @Override
    public int insertMeiExpert(MeiExpert meiExpert)
    {
        return meiExpertMapper.insertMeiExpert(meiExpert);
    }

    /**
     * 修改MEI-双创专家信息
     * 
     * @param meiExpert MEI-双创专家信息
     * @return 结果
     */
    @Override
    public int updateMeiExpert(MeiExpert meiExpert)
    {
        return meiExpertMapper.updateMeiExpert(meiExpert);
    }

    /**
     * 批量删除MEI-双创专家信息
     * 
     * @param ids 需要删除的MEI-双创专家信息主键
     * @return 结果
     */
    @Override
    public int deleteMeiExpertByIds(Long[] ids)
    {
        return meiExpertMapper.deleteMeiExpertByIds(ids);
    }

    /**
     * 删除MEI-双创专家信息信息
     * 
     * @param id MEI-双创专家信息主键
     * @return 结果
     */
    @Override
    public int deleteMeiExpertById(Long id)
    {
        return meiExpertMapper.deleteMeiExpertById(id);
    }
}
