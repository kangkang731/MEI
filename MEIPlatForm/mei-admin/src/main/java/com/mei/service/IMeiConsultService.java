package com.mei.service;

import java.util.List;
import com.mei.domain.MeiConsult;

/**
 * MEI-在线咨询服务Service接口
 * 
 * @author mei
 * @date 2026-04-11
 */
public interface IMeiConsultService 
{
    /**
     * 查询MEI-在线咨询服务
     * 
     * @param id MEI-在线咨询服务主键
     * @return MEI-在线咨询服务
     */
    public MeiConsult selectMeiConsultById(Long id);

    /**
     * 查询MEI-在线咨询服务列表
     * 
     * @param meiConsult MEI-在线咨询服务
     * @return MEI-在线咨询服务集合
     */
    public List<MeiConsult> selectMeiConsultList(MeiConsult meiConsult);

    /**
     * 新增MEI-在线咨询服务
     * 
     * @param meiConsult MEI-在线咨询服务
     * @return 结果
     */
    public int insertMeiConsult(MeiConsult meiConsult);

    /**
     * 修改MEI-在线咨询服务
     * 
     * @param meiConsult MEI-在线咨询服务
     * @return 结果
     */
    public int updateMeiConsult(MeiConsult meiConsult);

    /**
     * 批量删除MEI-在线咨询服务
     * 
     * @param ids 需要删除的MEI-在线咨询服务主键集合
     * @return 结果
     */
    public int deleteMeiConsultByIds(Long[] ids);

    /**
     * 删除MEI-在线咨询服务信息
     * 
     * @param id MEI-在线咨询服务主键
     * @return 结果
     */
    public int deleteMeiConsultById(Long id);
}
