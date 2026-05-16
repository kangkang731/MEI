package com.mei.service;

import java.util.List;
import com.mei.domain.MeiMessage;

/**
 * MEI-平台互动交流Service接口
 * 
 * @author mei
 * @date 2026-04-11
 */
public interface IMeiMessageService 
{
    /**
     * 查询MEI-平台互动交流
     * 
     * @param id MEI-平台互动交流主键
     * @return MEI-平台互动交流
     */
    public MeiMessage selectMeiMessageById(Long id);

    /**
     * 查询MEI-平台互动交流列表
     * 
     * @param meiMessage MEI-平台互动交流
     * @return MEI-平台互动交流集合
     */
    public List<MeiMessage> selectMeiMessageList(MeiMessage meiMessage);

    /**
     * 新增MEI-平台互动交流
     * 
     * @param meiMessage MEI-平台互动交流
     * @return 结果
     */
    public int insertMeiMessage(MeiMessage meiMessage);

    /**
     * 修改MEI-平台互动交流
     * 
     * @param meiMessage MEI-平台互动交流
     * @return 结果
     */
    public int updateMeiMessage(MeiMessage meiMessage);

    /**
     * 批量删除MEI-平台互动交流
     * 
     * @param ids 需要删除的MEI-平台互动交流主键集合
     * @return 结果
     */
    public int deleteMeiMessageByIds(Long[] ids);

    /**
     * 删除MEI-平台互动交流信息
     * 
     * @param id MEI-平台互动交流主键
     * @return 结果
     */
    public int deleteMeiMessageById(Long id);
}
