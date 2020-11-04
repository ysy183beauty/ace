package com.ace.business.entity;

import java.math.BigDecimal;

public class TOrg {
    private BigDecimal orgId;

    private String orgName;

    private String orgAddress;

    private String orgProvice;

    private Long status;

    private String orgCode;

    public BigDecimal getOrgId() {
        return orgId;
    }

    public void setOrgId(BigDecimal orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress == null ? null : orgAddress.trim();
    }

    public String getOrgProvice() {
        return orgProvice;
    }

    public void setOrgProvice(String orgProvice) {
        this.orgProvice = orgProvice == null ? null : orgProvice.trim();
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }
}