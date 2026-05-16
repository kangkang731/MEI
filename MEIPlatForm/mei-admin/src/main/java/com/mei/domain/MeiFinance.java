package com.mei.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.mei.common.annotation.Excel;
import com.mei.common.core.domain.BaseEntity;

/**
 * MEI-融资申请服务对象 mei_finance
 * 
 * @author mei
 * @date 2026-04-11
 */
public class MeiFinance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 关联企业ID */
    @Excel(name = "关联企业ID")
    private Long companyId;

    /** 申请融资金额 */
    @Excel(name = "申请融资金额")
    private BigDecimal money;

    /** 融资用途说明 */
    @Excel(name = "融资用途说明")
    private String reason;

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

    public void setMoney(BigDecimal money) 
    {
        this.money = money;
    }

    public BigDecimal getMoney() 
    {
        return money;
    }

    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
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
            .append("money", getMoney())
            .append("reason", getReason())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
