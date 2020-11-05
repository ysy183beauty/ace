package com.ace.business.entity;

import java.math.BigDecimal;

public class TOrg {
    private BigDecimal orgid;

    private String orgname;

    private String orgaddress;

    private String orgprovice;

    private Long status;

    private String orgcode;

    public BigDecimal getOrgid() {
        return orgid;
    }

    public void setOrgid(BigDecimal orgid) {
        this.orgid = orgid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getOrgaddress() {
        return orgaddress;
    }

    public void setOrgaddress(String orgaddress) {
        this.orgaddress = orgaddress;
    }

    public String getOrgprovice() {
        return orgprovice;
    }

    public void setOrgprovice(String orgprovice) {
        this.orgprovice = orgprovice;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }
}