package com.mei.service;

import java.util.List;
import com.mei.domain.MeiExpert;

/**
 * MEI-双创专家信息Service接口
 * 
 * @author mei
 * @date 2026-04-11
 */
public interface IMeiExpertService 
{
    /**
     * 查询MEI-双创专家信息
     * 
     * @param id MEI-双创专家信息主键
     * @return MEI-双创专家信息
     */
    public MeiExpert selectMeiExpertById(Long id);

    /**
     * 查询MEI-双创专家信息列表
     * 
     * @param meiExpert MEI-双创专家信息
     * @return MEI-双创专家信息集合
     */
    public List<MeiExpert> selectMeiExpertList(MeiExpert meiExpert);

    /**
     * 新增MEI-双创专家信息
     * 
     * @param meiExpert MEI-双创专家信息
     * @return 结果
     */
    public int insertMeiExpert(MeiExpert meiExpert);

    /**
     * 修改MEI-双创专家信息
     * 
     * @param meiExpert MEI-双创专家信息
     * @return 结果
     */
    public int updateMeiExpert(MeiExpert meiExpert);

    /**
     * 批量删除MEI-双创专家信息
     * 
     * @param ids 需要删除的MEI-双创专家信息主键集合
     * @return 结果
     */
    public int deleteMeiExpertByIds(Long[] ids);

    /**
     * 删除MEI-双创专家信息信息
     * 
     * @param id MEI-双创专家信息主键
     * @return 结果
     */
    public int deleteMeiExpertById(Long id);
}
