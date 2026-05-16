package com.mei.service;

import java.util.List;
import com.mei.domain.MeiCompany;

/**
 * MEI-入驻企业信息Service接口
 * 
 * @author mei
 * @date 2026-04-11
 */
public interface IMeiCompanyService 
{
    /**
     * 查询MEI-入驻企业信息
     * 
     * @param id MEI-入驻企业信息主键
     * @return MEI-入驻企业信息
     */
    public MeiCompany selectMeiCompanyById(Long id);

    /**
     * 查询MEI-入驻企业信息列表
     * 
     * @param meiCompany MEI-入驻企业信息
     * @return MEI-入驻企业信息集合
     */
    public List<MeiCompany> selectMeiCompanyList(MeiCompany meiCompany);

    /**
     * 新增MEI-入驻企业信息
     * 
     * @param meiCompany MEI-入驻企业信息
     * @return 结果
     */
    public int insertMeiCompany(MeiCompany meiCompany);

    /**
     * 修改MEI-入驻企业信息
     * 
     * @param meiCompany MEI-入驻企业信息
     * @return 结果
     */
    public int updateMeiCompany(MeiCompany meiCompany);

    /**
     * 批量删除MEI-入驻企业信息
     * 
     * @param ids 需要删除的MEI-入驻企业信息主键集合
     * @return 结果
     */
    public int deleteMeiCompanyByIds(Long[] ids);

    /**
     * 删除MEI-入驻企业信息信息
     * 
     * @param id MEI-入驻企业信息主键
     * @return 结果
     */
    public int deleteMeiCompanyById(Long id);
}
