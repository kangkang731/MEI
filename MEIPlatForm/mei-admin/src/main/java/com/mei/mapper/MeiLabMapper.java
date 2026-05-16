package com.mei.mapper;

import java.util.List;
import com.mei.domain.MeiLab;

/**
 * MEI-西藏高校实验室Mapper接口
 * 
 * @author mei
 * @date 2026-04-11
 */
public interface MeiLabMapper 
{
    /**
     * 查询MEI-西藏高校实验室
     * 
     * @param id MEI-西藏高校实验室主键
     * @return MEI-西藏高校实验室
     */
    public MeiLab selectMeiLabById(Long id);

    /**
     * 查询MEI-西藏高校实验室列表
     * 
     * @param meiLab MEI-西藏高校实验室
     * @return MEI-西藏高校实验室集合
     */
    public List<MeiLab> selectMeiLabList(MeiLab meiLab);

    /**
     * 新增MEI-西藏高校实验室
     * 
     * @param meiLab MEI-西藏高校实验室
     * @return 结果
     */
    public int insertMeiLab(MeiLab meiLab);

    /**
     * 修改MEI-西藏高校实验室
     * 
     * @param meiLab MEI-西藏高校实验室
     * @return 结果
     */
    public int updateMeiLab(MeiLab meiLab);

    /**
     * 删除MEI-西藏高校实验室
     * 
     * @param id MEI-西藏高校实验室主键
     * @return 结果
     */
    public int deleteMeiLabById(Long id);

    /**
     * 批量删除MEI-西藏高校实验室
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMeiLabByIds(Long[] ids);
}
