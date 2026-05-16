package com.mei.service.impl;

import java.util.List;
import com.mei.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mei.mapper.MeiProjectMapper;
import com.mei.domain.MeiProject;
import com.mei.service.IMeiProjectService;

/**
 * MEI-创新创业项目申报Service业务层处理
 * 
 * @author mei
 * @date 2026-04-11
 */
@Service
public class MeiProjectServiceImpl implements IMeiProjectService 
{
    @Autowired
    private MeiProjectMapper meiProjectMapper;

    /**
     * 查询MEI-创新创业项目申报
     * 
     * @param id MEI-创新创业项目申报主键
     * @return MEI-创新创业项目申报
     */
    @Override
    public MeiProject selectMeiProjectById(Long id)
    {
        return meiProjectMapper.selectMeiProjectById(id);
    }

    /**
     * 查询MEI-创新创业项目申报列表
     * 
     * @param meiProject MEI-创新创业项目申报
     * @return MEI-创新创业项目申报
     */
    @Override
    public List<MeiProject> selectMeiProjectList(MeiProject meiProject)
    {
        return meiProjectMapper.selectMeiProjectList(meiProject);
    }

    /**
     * 新增MEI-创新创业项目申报
     * 
     * @param meiProject MEI-创新创业项目申报
     * @return 结果
     */
    @Override
    public int insertMeiProject(MeiProject meiProject)
    {
        meiProject.setCreateTime(DateUtils.getNowDate());
        return meiProjectMapper.insertMeiProject(meiProject);
    }

    /**
     * 修改MEI-创新创业项目申报
     * 
     * @param meiProject MEI-创新创业项目申报
     * @return 结果
     */
    @Override
    public int updateMeiProject(MeiProject meiProject)
    {
        return meiProjectMapper.updateMeiProject(meiProject);
    }

    /**
     * 批量删除MEI-创新创业项目申报
     * 
     * @param ids 需要删除的MEI-创新创业项目申报主键
     * @return 结果
     */
    @Override
    public int deleteMeiProjectByIds(Long[] ids)
    {
        return meiProjectMapper.deleteMeiProjectByIds(ids);
    }

    /**
     * 删除MEI-创新创业项目申报信息
     * 
     * @param id MEI-创新创业项目申报主键
     * @return 结果
     */
    @Override
    public int deleteMeiProjectById(Long id)
    {
        return meiProjectMapper.deleteMeiProjectById(id);
    }
}
