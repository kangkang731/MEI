package com.mei.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.mei.common.annotation.Excel;
import com.mei.common.core.domain.BaseEntity;

/**
 * MEI-政策公告信息对象 mei_policy
 * 
 * @author mei
 * @date 2026-04-11
 */
public class MeiPolicy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 标题(藏汉双语) */
    @Excel(name = "标题(藏汉双语)")
    private String title;

    /** 内容详情 */
    @Excel(name = "内容详情")
    private String content;

    /** 类型 0政策 1公告 */
    @Excel(name = "类型 0政策 1公告")
    private String type;

    /** 区域分类 national/xz/city */
    @Excel(name = "区域分类")
    private String region;

    /** 排序权重 */
    @Excel(name = "排序权重")
    private Long sort;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getRegion()
    {
        return region;
    }

    public void setSort(Long sort)
    {
        this.sort = sort;
    }

    public Long getSort()
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("type", getType())
            .append("sort", getSort())
            .append("createTime", getCreateTime())
            .toString();
    }
}
