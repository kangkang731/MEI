package com.mei.mapper;

import java.util.List;
import com.mei.domain.MeiStatistics;

/**
 * MEI-双创数据统计总Mapper接口
 * 
 * @author mei
 * @date 2026-04-11
 */
public interface MeiStatisticsMapper 
{
    /**
     * 查询MEI-双创数据统计总
     * 
     * @param id MEI-双创数据统计总主键
     * @return MEI-双创数据统计总
     */
    public MeiStatistics selectMeiStatisticsById(Long id);

    /**
     * 查询MEI-双创数据统计总列表
     * 
     * @param meiStatistics MEI-双创数据统计总
     * @return MEI-双创数据统计总集合
     */
    public List<MeiStatistics> selectMeiStatisticsList(MeiStatistics meiStatistics);

    /**
     * 新增MEI-双创数据统计总
     * 
     * @param meiStatistics MEI-双创数据统计总
     * @return 结果
     */
    public int insertMeiStatistics(MeiStatistics meiStatistics);

    /**
     * 修改MEI-双创数据统计总
     * 
     * @param meiStatistics MEI-双创数据统计总
     * @return 结果
     */
    public int updateMeiStatistics(MeiStatistics meiStatistics);

    /**
     * 删除MEI-双创数据统计总
     * 
     * @param id MEI-双创数据统计总主键
     * @return 结果
     */
    public int deleteMeiStatisticsById(Long id);

    /**
     * 批量删除MEI-双创数据统计总
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMeiStatisticsByIds(Long[] ids);
}
