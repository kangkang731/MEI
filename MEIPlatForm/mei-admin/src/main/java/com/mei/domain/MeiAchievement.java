package com.mei.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.mei.common.annotation.Excel;
import com.mei.common.core.domain.BaseEntity;

/**
 * 成果对象 mei_achievement
 * 
 * @author mei
 * @date 2026-04-10
 */
@Data
public class MeiAchievement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 成果 ID */
    @Excel(name = "成果 ID")
    private String id;

    /** 关联项目 ID */
    @Excel(name = "关联项目 ID")
    private String projectId;

    /** 成果标题 */
    @Excel(name = "成果标题")
    private String title;

    /** 封面路径 */
    @Excel(name = "封面路径")
    private String cover;

    /** 成果详情 */
    @Excel(name = "成果详情")
    private String content;

    /** 是否展示（1 = 展示，0 = 隐藏） */
    @Excel(name = "是否展示", readConverterExp = "1=,==,展=示，0,==,隐=藏")
    private String isShow;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }

    public void setProjectId(String projectId) 
    {
        this.projectId = projectId;
    }

    public String getProjectId() 
    {
        return projectId;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setCover(String cover) 
    {
        this.cover = cover;
    }

    public String getCover() 
    {
        return cover;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setIsShow(String isShow) 
    {
        this.isShow = isShow;
    }

    public String getIsShow() 
    {
        return isShow;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("title", getTitle())
            .append("cover", getCover())
            .append("content", getContent())
            .append("isShow", getIsShow())
            .append("createTime", getCreateTime())
            .toString();
    }
}
