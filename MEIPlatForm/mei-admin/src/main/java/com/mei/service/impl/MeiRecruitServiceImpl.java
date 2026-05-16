package com.mei.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mei.mapper.MeiRecruitMapper;
import com.mei.domain.MeiRecruit;
import com.mei.service.IMeiRecruitService;

/**
 * MEI-人才招聘信息Service业务层处理
 * 
 * @author mei
 * @date 2026-04-11
 */
@Service
public class MeiRecruitServiceImpl implements IMeiRecruitService 
{
    @Autowired
    private MeiRecruitMapper meiRecruitMapper;

    /**
     * 查询MEI-人才招聘信息
     * 
     * @param id MEI-人才招聘信息主键
     * @return MEI-人才招聘信息
     */
    @Override
    public MeiRecruit selectMeiRecruitById(Long id)
    {
        return meiRecruitMapper.selectMeiRecruitById(id);
    }

    /**
     * 查询MEI-人才招聘信息列表
     * 
     * @param meiRecruit MEI-人才招聘信息
     * @return MEI-人才招聘信息
     */
    @Override
    public List<MeiRecruit> selectMeiRecruitList(MeiRecruit meiRecruit)
    {
        return meiRecruitMapper.selectMeiRecruitList(meiRecruit);
    }

    /**
     * 新增MEI-人才招聘信息
     * 
     * @param meiRecruit MEI-人才招聘信息
     * @return 结果
     */
    @Override
    public int insertMeiRecruit(MeiRecruit meiRecruit)
    {
        return meiRecruitMapper.insertMeiRecruit(meiRecruit);
    }

    /**
     * 修改MEI-人才招聘信息
     * 
     * @param meiRecruit MEI-人才招聘信息
     * @return 结果
     */
    @Override
    public int updateMeiRecruit(MeiRecruit meiRecruit)
    {
        return meiRecruitMapper.updateMeiRecruit(meiRecruit);
    }

    /**
     * 批量删除MEI-人才招聘信息
     * 
     * @param ids 需要删除的MEI-人才招聘信息主键
     * @return 结果
     */
    @Override
    public int deleteMeiRecruitByIds(Long[] ids)
    {
        return meiRecruitMapper.deleteMeiRecruitByIds(ids);
    }

    /**
     * 删除MEI-人才招聘信息信息
     * 
     * @param id MEI-人才招聘信息主键
     * @return 结果
     */
    @Override
    public int deleteMeiRecruitById(Long id)
    {
        return meiRecruitMapper.deleteMeiRecruitById(id);
    }
}
