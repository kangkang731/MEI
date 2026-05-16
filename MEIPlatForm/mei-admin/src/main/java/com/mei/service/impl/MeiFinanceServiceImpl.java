package com.mei.service.impl;

import java.util.List;
import com.mei.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mei.mapper.MeiFinanceMapper;
import com.mei.domain.MeiFinance;
import com.mei.service.IMeiFinanceService;

/**
 * MEI-融资申请服务Service业务层处理
 * 
 * @author mei
 * @date 2026-04-11
 */
@Service
public class MeiFinanceServiceImpl implements IMeiFinanceService 
{
    @Autowired
    private MeiFinanceMapper meiFinanceMapper;

    /**
     * 查询MEI-融资申请服务
     * 
     * @param id MEI-融资申请服务主键
     * @return MEI-融资申请服务
     */
    @Override
    public MeiFinance selectMeiFinanceById(Long id)
    {
        return meiFinanceMapper.selectMeiFinanceById(id);
    }

    /**
     * 查询MEI-融资申请服务列表
     * 
     * @param meiFinance MEI-融资申请服务
     * @return MEI-融资申请服务
     */
    @Override
    public List<MeiFinance> selectMeiFinanceList(MeiFinance meiFinance)
    {
        return meiFinanceMapper.selectMeiFinanceList(meiFinance);
    }

    /**
     * 新增MEI-融资申请服务
     * 
     * @param meiFinance MEI-融资申请服务
     * @return 结果
     */
    @Override
    public int insertMeiFinance(MeiFinance meiFinance)
    {
        meiFinance.setCreateTime(DateUtils.getNowDate());
        return meiFinanceMapper.insertMeiFinance(meiFinance);
    }

    /**
     * 修改MEI-融资申请服务
     * 
     * @param meiFinance MEI-融资申请服务
     * @return 结果
     */
    @Override
    public int updateMeiFinance(MeiFinance meiFinance)
    {
        return meiFinanceMapper.updateMeiFinance(meiFinance);
    }

    /**
     * 批量删除MEI-融资申请服务
     * 
     * @param ids 需要删除的MEI-融资申请服务主键
     * @return 结果
     */
    @Override
    public int deleteMeiFinanceByIds(Long[] ids)
    {
        return meiFinanceMapper.deleteMeiFinanceByIds(ids);
    }

    /**
     * 删除MEI-融资申请服务信息
     * 
     * @param id MEI-融资申请服务主键
     * @return 结果
     */
    @Override
    public int deleteMeiFinanceById(Long id)
    {
        return meiFinanceMapper.deleteMeiFinanceById(id);
    }
}
