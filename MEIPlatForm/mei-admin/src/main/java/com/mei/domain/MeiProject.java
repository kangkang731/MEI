package com.mei.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.mei.common.annotation.Excel;
import com.mei.common.core.domain.BaseEntity;

/**
 * MEI-创新创业项目申报对象 mei_project
 * 
 * @author mei
 * @date 2026-04-11
 */
public class MeiProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 关联企业ID */
    @Excel(name = "关联企业ID")
    private Long companyId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目类型 信息技术/藏医药/文旅 */
    @Excel(name = "项目类型 信息技术/藏医药/文旅")
    private String projectType;

    /** 项目内容详情 */
    @Excel(name = "项目内容详情")
    private String content;

    /** 项目附件 */
    @Excel(name = "项目附件")
    private String attach;

    /** 审核状态 0待审核 1通过 2驳回 */
    @Excel(name = "审核状态 0待审核 1通过 2驳回")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCompanyId(Long companyId) 
    {
        this.companyId = companyId;
    }

    public Long getCompanyId() 
    {
        return companyId;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setProjectType(String projectType) 
    {
        this.projectType = projectType;
    }

    public String getProjectType() 
    {
        return projectType;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setAttach(String attach) 
    {
        this.attach = attach;
    }

    public String getAttach() 
    {
        return attach;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("projectName", getProjectName())
            .append("projectType", getProjectType())
            .append("content", getContent())
            .append("attach", getAttach())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
