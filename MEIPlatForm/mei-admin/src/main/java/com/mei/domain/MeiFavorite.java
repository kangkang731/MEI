package com.mei.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.mei.common.annotation.Excel;
import com.mei.common.core.domain.BaseEntity;

/**
 * MEI-政策收藏对象 mei_favorite
 * 
 * @author mei
 * @date 2026-04-28
 */
public class MeiFavorite extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 政策ID */
    @Excel(name = "政策ID")
    private Long policyId;

    /** 政策标题 */
    @Excel(name = "政策标题")
    private String policyTitle;

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

    public void setPolicyId(Long policyId) 
    {
        this.policyId = policyId;
    }

    public Long getPolicyId() 
    {
        return policyId;
    }

    public void setPolicyTitle(String policyTitle) 
    {
        this.policyTitle = policyTitle;
    }

    public String getPolicyTitle() 
    {
        return policyTitle;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("policyId", getPolicyId())
            .append("policyTitle", getPolicyTitle())
            .append("createTime", getCreateTime())
            .toString();
    }
}
