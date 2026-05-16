package com.mei.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.mei.common.annotation.Excel;
import com.mei.common.core.domain.BaseEntity;

/**
 * MEI-双创数据统计总对象 mei_statistics
 * 
 * @author mei
 * @date 2026-04-11
 */
public class MeiStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 入驻企业总数 */
    @Excel(name = "入驻企业总数")
    private Long companyNum;

    /** 申报项目总数 */
    @Excel(name = "申报项目总数")
    private Long projectNum;

    /** 成果展示总数 */
    @Excel(name = "成果展示总数")
    private Long achievementNum;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCompanyNum(Long companyNum) 
    {
        this.companyNum = companyNum;
    }

    public Long getCompanyNum() 
    {
        return companyNum;
    }

    public void setProjectNum(Long projectNum) 
    {
        this.projectNum = projectNum;
    }

    public Long getProjectNum() 
    {
        return projectNum;
    }

    public void setAchievementNum(Long achievementNum) 
    {
        this.achievementNum = achievementNum;
    }

    public Long getAchievementNum() 
    {
        return achievementNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyNum", getCompanyNum())
            .append("projectNum", getProjectNum())
            .append("achievementNum", getAchievementNum())
            .append("createTime", getCreateTime())
            .toString();
    }
}
