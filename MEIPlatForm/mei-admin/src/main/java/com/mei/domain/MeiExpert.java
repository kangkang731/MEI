package com.mei.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.mei.common.annotation.Excel;
import com.mei.common.core.domain.BaseEntity;

/**
 * MEI-双创专家信息对象 mei_expert
 * 
 * @author mei
 * @date 2026-04-11
 */
public class MeiExpert extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 关联用户ID */
    @Excel(name = "关联用户ID")
    private Long userId;

    /** 专家姓名 */
    @Excel(name = "专家姓名")
    private String name;

    /** 专业领域 */
    @Excel(name = "专业领域")
    private String major;

    /** 专家简介 */
    @Excel(name = "专家简介")
    private String intro;

    /** 状态 0正常 1停用 */
    @Excel(name = "状态 0正常 1停用")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setMajor(String major) 
    {
        this.major = major;
    }

    public String getMajor() 
    {
        return major;
    }

    public void setIntro(String intro) 
    {
        this.intro = intro;
    }

    public String getIntro() 
    {
        return intro;
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
            .append("userId", getUserId())
            .append("name", getName())
            .append("major", getMajor())
            .append("intro", getIntro())
            .append("status", getStatus())
            .toString();
    }
}
