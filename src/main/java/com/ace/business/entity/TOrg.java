package com.ace.business.entity;

import java.math.BigDecimal;

public class TOrg {
    private BigDecimal org_id;

    private String org_name;

    private String org_address;

    private String org_provice;

    private Long status;

    private String org_code;

    public BigDecimal getOrg_id() {
        return org_id;
    }

    public void setOrg_id(BigDecimal org_id) {
        this.org_id = org_id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_address() {
        return org_address;
    }

    public void setOrg_address(String org_address) {
        this.org_address = org_address;
    }

    public String getOrg_provice() {
        return org_provice;
    }

    public void setOrg_provice(String org_provice) {
        this.org_provice = org_provice;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }
}