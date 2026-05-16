package com.mei.mapper;

import java.util.List;
import com.mei.domain.MeiRecruit;

/**
 * MEI-人才招聘信息Mapper接口
 * 
 * @author mei
 * @date 2026-04-11
 */
public interface MeiRecruitMapper 
{
    /**
     * 查询MEI-人才招聘信息
     * 
     * @param id MEI-人才招聘信息主键
     * @return MEI-人才招聘信息
     */
    public MeiRecruit selectMeiRecruitById(Long id);

    /**
     * 查询MEI-人才招聘信息列表
     * 
     * @param meiRecruit MEI-人才招聘信息
     * @return MEI-人才招聘信息集合
     */
    public List<MeiRecruit> selectMeiRecruitList(MeiRecruit meiRecruit);

    /**
     * 新增MEI-人才招聘信息
     * 
     * @param meiRecruit MEI-人才招聘信息
     * @return 结果
     */
    public int insertMeiRecruit(MeiRecruit meiRecruit);

    /**
     * 修改MEI-人才招聘信息
     * 
     * @param meiRecruit MEI-人才招聘信息
     * @return 结果
     */
    public int updateMeiRecruit(MeiRecruit meiRecruit);

    /**
     * 删除MEI-人才招聘信息
     * 
     * @param id MEI-人才招聘信息主键
     * @return 结果
     */
    public int deleteMeiRecruitById(Long id);

    /**
     * 批量删除MEI-人才招聘信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMeiRecruitByIds(Long[] ids);
}
