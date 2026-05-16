package com.mei.service.impl;

import java.util.List;
import com.mei.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mei.mapper.MeiMessageMapper;
import com.mei.domain.MeiMessage;
import com.mei.service.IMeiMessageService;

/**
 * MEI-平台互动交流Service业务层处理
 * 
 * @author mei
 * @date 2026-04-11
 */
@Service
public class MeiMessageServiceImpl implements IMeiMessageService 
{
    @Autowired
    private MeiMessageMapper meiMessageMapper;

    /**
     * 查询MEI-平台互动交流
     * 
     * @param id MEI-平台互动交流主键
     * @return MEI-平台互动交流
     */
    @Override
    public MeiMessage selectMeiMessageById(Long id)
    {
        return meiMessageMapper.selectMeiMessageById(id);
    }

    /**
     * 查询MEI-平台互动交流列表
     * 
     * @param meiMessage MEI-平台互动交流
     * @return MEI-平台互动交流
     */
    @Override
    public List<MeiMessage> selectMeiMessageList(MeiMessage meiMessage)
    {
        return meiMessageMapper.selectMeiMessageList(meiMessage);
    }

    /**
     * 新增MEI-平台互动交流
     * 
     * @param meiMessage MEI-平台互动交流
     * @return 结果
     */
    @Override
    public int insertMeiMessage(MeiMessage meiMessage)
    {
        meiMessage.setCreateTime(DateUtils.getNowDate());
        return meiMessageMapper.insertMeiMessage(meiMessage);
    }

    /**
     * 修改MEI-平台互动交流
     * 
     * @param meiMessage MEI-平台互动交流
     * @return 结果
     */
    @Override
    public int updateMeiMessage(MeiMessage meiMessage)
    {
        return meiMessageMapper.updateMeiMessage(meiMessage);
    }

    /**
     * 批量删除MEI-平台互动交流
     * 
     * @param ids 需要删除的MEI-平台互动交流主键
     * @return 结果
     */
    @Override
    public int deleteMeiMessageByIds(Long[] ids)
    {
        return meiMessageMapper.deleteMeiMessageByIds(ids);
    }

    /**
     * 删除MEI-平台互动交流信息
     * 
     * @param id MEI-平台互动交流主键
     * @return 结果
     */
    @Override
    public int deleteMeiMessageById(Long id)
    {
        return meiMessageMapper.deleteMeiMessageById(id);
    }
}
