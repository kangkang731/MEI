package com.mei.mapper;

import java.util.List;
import com.mei.domain.MeiPolicy;

/**
 * MEI-政策公告信息Mapper接口
 * 
 * @author mei
 * @date 2026-04-11
 */
public interface MeiPolicyMapper 
{
    /**
     * 查询MEI-政策公告信息
     * 
     * @param id MEI-政策公告信息主键
     * @return MEI-政策公告信息
     */
    public MeiPolicy selectMeiPolicyById(Long id);

    /**
     * 查询MEI-政策公告信息列表
     * 
     * @param meiPolicy MEI-政策公告信息
     * @return MEI-政策公告信息集合
     */
    public List<MeiPolicy> selectMeiPolicyList(MeiPolicy meiPolicy);

    /**
     * 新增MEI-政策公告信息
     * 
     * @param meiPolicy MEI-政策公告信息
     * @return 结果
     */
    public int insertMeiPolicy(MeiPolicy meiPolicy);

    /**
     * 修改MEI-政策公告信息
     * 
     * @param meiPolicy MEI-政策公告信息
     * @return 结果
     */
    public int updateMeiPolicy(MeiPolicy meiPolicy);

    /**
     * 删除MEI-政策公告信息
     * 
     * @param id MEI-政策公告信息主键
     * @return 结果
     */
    public int deleteMeiPolicyById(Long id);

    /**
     * 批量删除MEI-政策公告信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMeiPolicyByIds(Long[] ids);
}
