package com.mei.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mei.mapper.MeiConsultMapper;
import com.mei.domain.MeiConsult;
import com.mei.service.IMeiConsultService;

/**
 * MEI-在线咨询服务Service业务层处理
 * 
 * @author mei
 * @date 2026-04-11
 */
@Service
public class MeiConsultServiceImpl implements IMeiConsultService 
{
    @Autowired
    private MeiConsultMapper meiConsultMapper;

    /**
     * 查询MEI-在线咨询服务
     * 
     * @param id MEI-在线咨询服务主键
     * @return MEI-在线咨询服务
     */
    @Override
    public MeiConsult selectMeiConsultById(Long id)
    {
        return meiConsultMapper.selectMeiConsultById(id);
    }

    /**
     * 查询MEI-在线咨询服务列表
     * 
     * @param meiConsult MEI-在线咨询服务
     * @return MEI-在线咨询服务
     */
    @Override
    public List<MeiConsult> selectMeiConsultList(MeiConsult meiConsult)
    {
        return meiConsultMapper.selectMeiConsultList(meiConsult);
    }

    /**
     * 新增MEI-在线咨询服务
     * 
     * @param meiConsult MEI-在线咨询服务
     * @return 结果
     */
    @Override
    public int insertMeiConsult(MeiConsult meiConsult)
    {
        return meiConsultMapper.insertMeiConsult(meiConsult);
    }

    /**
     * 修改MEI-在线咨询服务
     * 
     * @param meiConsult MEI-在线咨询服务
     * @return 结果
     */
    @Override
    public int updateMeiConsult(MeiConsult meiConsult)
    {
        return meiConsultMapper.updateMeiConsult(meiConsult);
    }

    /**
     * 批量删除MEI-在线咨询服务
     * 
     * @param ids 需要删除的MEI-在线咨询服务主键
     * @return 结果
     */
    @Override
    public int deleteMeiConsultByIds(Long[] ids)
    {
        return meiConsultMapper.deleteMeiConsultByIds(ids);
    }

    /**
     * 删除MEI-在线咨询服务信息
     * 
     * @param id MEI-在线咨询服务主键
     * @return 结果
     */
    @Override
    public int deleteMeiConsultById(Long id)
    {
        return meiConsultMapper.deleteMeiConsultById(id);
    }
}
