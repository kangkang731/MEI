package com.mei.service;

import java.util.List;
import com.mei.domain.MeiProject;

/**
 * MEI-创新创业项目申报Service接口
 * 
 * @author mei
 * @date 2026-04-11
 */
public interface IMeiProjectService 
{
    /**
     * 查询MEI-创新创业项目申报
     * 
     * @param id MEI-创新创业项目申报主键
     * @return MEI-创新创业项目申报
     */
    public MeiProject selectMeiProjectById(Long id);

    /**
     * 查询MEI-创新创业项目申报列表
     * 
     * @param meiProject MEI-创新创业项目申报
     * @return MEI-创新创业项目申报集合
     */
    public List<MeiProject> selectMeiProjectList(MeiProject meiProject);

    /**
     * 新增MEI-创新创业项目申报
     * 
     * @param meiProject MEI-创新创业项目申报
     * @return 结果
     */
    public int insertMeiProject(MeiProject meiProject);

    /**
     * 修改MEI-创新创业项目申报
     * 
     * @param meiProject MEI-创新创业项目申报
     * @return 结果
     */
    public int updateMeiProject(MeiProject meiProject);

    /**
     * 批量删除MEI-创新创业项目申报
     * 
     * @param ids 需要删除的MEI-创新创业项目申报主键集合
     * @return 结果
     */
    public int deleteMeiProjectByIds(Long[] ids);

    /**
     * 删除MEI-创新创业项目申报信息
     * 
     * @param id MEI-创新创业项目申报主键
     * @return 结果
     */
    public int deleteMeiProjectById(Long id);
}
