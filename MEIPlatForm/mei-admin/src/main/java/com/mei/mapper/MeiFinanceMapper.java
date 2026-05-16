package com.mei.mapper;

import java.util.List;
import com.mei.domain.MeiFinance;

/**
 * MEI-融资申请服务Mapper接口
 * 
 * @author mei
 * @date 2026-04-11
 */
public interface MeiFinanceMapper 
{
    /**
     * 查询MEI-融资申请服务
     * 
     * @param id MEI-融资申请服务主键
     * @return MEI-融资申请服务
     */
    public MeiFinance selectMeiFinanceById(Long id);

    /**
     * 查询MEI-融资申请服务列表
     * 
     * @param meiFinance MEI-融资申请服务
     * @return MEI-融资申请服务集合
     */
    public List<MeiFinance> selectMeiFinanceList(MeiFinance meiFinance);

    /**
     * 新增MEI-融资申请服务
     * 
     * @param meiFinance MEI-融资申请服务
     * @return 结果
     */
    public int insertMeiFinance(MeiFinance meiFinance);

    /**
     * 修改MEI-融资申请服务
     * 
     * @param meiFinance MEI-融资申请服务
     * @return 结果
     */
    public int updateMeiFinance(MeiFinance meiFinance);

    /**
     * 删除MEI-融资申请服务
     * 
     * @param id MEI-融资申请服务主键
     * @return 结果
     */
    public int deleteMeiFinanceById(Long id);

    /**
     * 批量删除MEI-融资申请服务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMeiFinanceByIds(Long[] ids);
}
