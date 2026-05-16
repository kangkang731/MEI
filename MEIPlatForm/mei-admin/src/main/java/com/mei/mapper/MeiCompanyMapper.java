package com.mei.mapper;

import java.util.List;
import com.mei.domain.MeiCompany;

/**
 * MEI-入驻企业信息Mapper接口
 * 
 * @author mei
 * @date 2026-04-11
 */
public interface MeiCompanyMapper 
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
     * 删除MEI-入驻企业信息
     * 
     * @param id MEI-入驻企业信息主键
     * @return 结果
     */
    public int deleteMeiCompanyById(Long id);

    /**
     * 批量删除MEI-入驻企业信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMeiCompanyByIds(Long[] ids);
}
