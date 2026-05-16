package com.mei.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.mei.common.annotation.Excel;
import com.mei.common.core.domain.BaseEntity;

/**
 * MEI-西藏高校实验室对象 mei_lab
 * 
 * @author mei
 * @date 2026-04-11
 */
public class MeiLab extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 高校名称 */
    @Excel(name = "高校名称")
    private String schoolName;

    /** 实验室名称 */
    @Excel(name = "实验室名称")
    private String labName;

    /** 实验室介绍 */
    @Excel(name = "实验室介绍")
    private String content;

    /** 开放状态 0关闭 1开放 */
    @Excel(name = "开放状态 0关闭 1开放")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setSchoolName(String schoolName) 
    {
        this.schoolName = schoolName;
    }

    public String getSchoolName() 
    {
        return schoolName;
    }

    public void setLabName(String labName) 
    {
        this.labName = labName;
    }

    public String getLabName() 
    {
        return labName;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
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
            .append("schoolName", getSchoolName())
            .append("labName", getLabName())
            .append("content", getContent())
            .append("status", getStatus())
            .toString();
    }
}
