package com.mei.service.impl;

import java.util.List;
import com.mei.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mei.mapper.MeiStatisticsMapper;
import com.mei.domain.MeiStatistics;
import com.mei.service.IMeiStatisticsService;

/**
 * MEI-双创数据统计总Service业务层处理
 * 
 * @author mei
 * @date 2026-04-11
 */
@Service
public class MeiStatisticsServiceImpl implements IMeiStatisticsService 
{
    @Autowired
    private MeiStatisticsMapper meiStatisticsMapper;

    /**
     * 查询MEI-双创数据统计总
     * 
     * @param id MEI-双创数据统计总主键
     * @return MEI-双创数据统计总
     */
    @Override
    public MeiStatistics selectMeiStatisticsById(Long id)
    {
        return meiStatisticsMapper.selectMeiStatisticsById(id);
    }

    /**
     * 查询MEI-双创数据统计总列表
     * 
     * @param meiStatistics MEI-双创数据统计总
     * @return MEI-双创数据统计总
     */
    @Override
    public List<MeiStatistics> selectMeiStatisticsList(MeiStatistics meiStatistics)
    {
        return meiStatisticsMapper.selectMeiStatisticsList(meiStatistics);
    }

    /**
     * 新增MEI-双创数据统计总
     * 
     * @param meiStatistics MEI-双创数据统计总
     * @return 结果
     */
    @Override
    public int insertMeiStatistics(MeiStatistics meiStatistics)
    {
        meiStatistics.setCreateTime(DateUtils.getNowDate());
        return meiStatisticsMapper.insertMeiStatistics(meiStatistics);
    }

    /**
     * 修改MEI-双创数据统计总
     * 
     * @param meiStatistics MEI-双创数据统计总
     * @return 结果
     */
    @Override
    public int updateMeiStatistics(MeiStatistics meiStatistics)
    {
        return meiStatisticsMapper.updateMeiStatistics(meiStatistics);
    }

    /**
     * 批量删除MEI-双创数据统计总
     * 
     * @param ids 需要删除的MEI-双创数据统计总主键
     * @return 结果
     */
    @Override
    public int deleteMeiStatisticsByIds(Long[] ids)
    {
        return meiStatisticsMapper.deleteMeiStatisticsByIds(ids);
    }

    /**
     * 删除MEI-双创数据统计总信息
     * 
     * @param id MEI-双创数据统计总主键
     * @return 结果
     */
    @Override
    public int deleteMeiStatisticsById(Long id)
    {
        return meiStatisticsMapper.deleteMeiStatisticsById(id);
    }
}
