package com.mei.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mei.mapper.MeiLabMapper;
import com.mei.domain.MeiLab;
import com.mei.service.IMeiLabService;

/**
 * MEI-西藏高校实验室Service业务层处理
 * 
 * @author mei
 * @date 2026-04-11
 */
@Service
public class MeiLabServiceImpl implements IMeiLabService 
{
    @Autowired
    private MeiLabMapper meiLabMapper;

    /**
     * 查询MEI-西藏高校实验室
     * 
     * @param id MEI-西藏高校实验室主键
     * @return MEI-西藏高校实验室
     */
    @Override
    public MeiLab selectMeiLabById(Long id)
    {
        return meiLabMapper.selectMeiLabById(id);
    }

    /**
     * 查询MEI-西藏高校实验室列表
     * 
     * @param meiLab MEI-西藏高校实验室
     * @return MEI-西藏高校实验室
     */
    @Override
    public List<MeiLab> selectMeiLabList(MeiLab meiLab)
    {
        return meiLabMapper.selectMeiLabList(meiLab);
    }

    /**
     * 新增MEI-西藏高校实验室
     * 
     * @param meiLab MEI-西藏高校实验室
     * @return 结果
     */
    @Override
    public int insertMeiLab(MeiLab meiLab)
    {
        return meiLabMapper.insertMeiLab(meiLab);
    }

    /**
     * 修改MEI-西藏高校实验室
     * 
     * @param meiLab MEI-西藏高校实验室
     * @return 结果
     */
    @Override
    public int updateMeiLab(MeiLab meiLab)
    {
        return meiLabMapper.updateMeiLab(meiLab);
    }

    /**
     * 批量删除MEI-西藏高校实验室
     * 
     * @param ids 需要删除的MEI-西藏高校实验室主键
     * @return 结果
     */
    @Override
    public int deleteMeiLabByIds(Long[] ids)
    {
        return meiLabMapper.deleteMeiLabByIds(ids);
    }

    /**
     * 删除MEI-西藏高校实验室信息
     * 
     * @param id MEI-西藏高校实验室主键
     * @return 结果
     */
    @Override
    public int deleteMeiLabById(Long id)
    {
        return meiLabMapper.deleteMeiLabById(id);
    }
}
