package com.mei.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.mei.common.annotation.Excel;
import com.mei.common.core.domain.BaseEntity;

/**
 * MEI-在线咨询服务对象 mei_consult
 * 
 * @author mei
 * @date 2026-04-11
 */
public class MeiConsult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 咨询用户ID */
    @Excel(name = "咨询用户ID")
    private Long userId;

    /** 回复专家ID */
    @Excel(name = "回复专家ID")
    private Long expertId;

    /** 咨询标题 */
    @Excel(name = "咨询标题")
    private String title;

    /** 咨询内容 */
    @Excel(name = "咨询内容")
    private String content;

    /** 回复内容 */
    @Excel(name = "回复内容")
    private String reply;

    /** 咨询类型 */
    @Excel(name = "咨询类型")
    private String type;

    /** 回复状态 0未回复 1已回复 */
    @Excel(name = "回复状态 0未回复 1已回复")
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

    public void setExpertId(Long expertId) 
    {
        this.expertId = expertId;
    }

    public Long getExpertId() 
    {
        return expertId;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setReply(String reply)
    {
        this.reply = reply;
    }

    public String getReply()
    {
        return reply;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
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
            .append("expertId", getExpertId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("reply", getReply())
            .append("type", getType())
            .append("status", getStatus())
            .toString();
    }
}
