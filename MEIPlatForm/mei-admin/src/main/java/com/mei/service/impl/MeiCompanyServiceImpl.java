package com.mei.service.impl;

import java.util.List;
import com.mei.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mei.mapper.MeiCompanyMapper;
import com.mei.domain.MeiCompany;
import com.mei.service.IMeiCompanyService;

/**
 * MEI-入驻企业信息Service业务层处理
 * 
 * @author mei
 * @date 2026-04-11
 */
@Service
public class MeiCompanyServiceImpl implements IMeiCompanyService 
{
    @Autowired
    private MeiCompanyMapper meiCompanyMapper;

    /**
     * 查询MEI-入驻企业信息
     * 
     * @param id MEI-入驻企业信息主键
     * @return MEI-入驻企业信息
     */
    @Override
    public MeiCompany selectMeiCompanyById(Long id)
    {
        return meiCompanyMapper.selectMeiCompanyById(id);
    }

    /**
     * 查询MEI-入驻企业信息列表
     * 
     * @param meiCompany MEI-入驻企业信息
     * @return MEI-入驻企业信息
     */
    @Override
    public List<MeiCompany> selectMeiCompanyList(MeiCompany meiCompany)
    {
        return meiCompanyMapper.selectMeiCompanyList(meiCompany);
    }

    /**
     * 新增MEI-入驻企业信息
     * 
     * @param meiCompany MEI-入驻企业信息
     * @return 结果
     */
    @Override
    public int insertMeiCompany(MeiCompany meiCompany)
    {
        meiCompany.setCreateTime(DateUtils.getNowDate());
        return meiCompanyMapper.insertMeiCompany(meiCompany);
    }

    /**
     * 修改MEI-入驻企业信息
     * 
     * @param meiCompany MEI-入驻企业信息
     * @return 结果
     */
    @Override
    public int updateMeiCompany(MeiCompany meiCompany)
    {
        return meiCompanyMapper.updateMeiCompany(meiCompany);
    }

    /**
     * 批量删除MEI-入驻企业信息
     * 
     * @param ids 需要删除的MEI-入驻企业信息主键
     * @return 结果
     */
    @Override
    public int deleteMeiCompanyByIds(Long[] ids)
    {
        return meiCompanyMapper.deleteMeiCompanyByIds(ids);
    }

    /**
     * 删除MEI-入驻企业信息信息
     * 
     * @param id MEI-入驻企业信息主键
     * @return 结果
     */
    @Override
    public int deleteMeiCompanyById(Long id)
    {
        return meiCompanyMapper.deleteMeiCompanyById(id);
    }
}
