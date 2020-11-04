package com.ace.sysytem.entity;

public class BaseInfoSys {
    private Integer id;//主键
    private String fieldname;//字段名称
    private String fieldtype;//字段类型(数字、字符串、时间等)
    private Integer fieldlength;//字段允许的长度
    private String fieldlabel;//中文名称
    private Integer listdisplay;//是否显示在列表上
    private Integer formdisplay;//是否显示在form表单上
    private Integer querydisplay;//是否作为查询条件
    private String tablename;//表名
    private String statusmap;//存放下拉框固定值
    private String url;//存放下拉框服务器地址
    public BaseInfoSys(){}
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFieldname() {
        return fieldname;
    }
    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }
    public String getFieldtype() {
        return fieldtype;
    }
    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }
    public Integer getFieldlength() {
        return fieldlength;
    }
    public void setFieldlength(Integer fieldlength) {
        this.fieldlength = fieldlength;
    }
    public String getFieldlabel() {
        return fieldlabel;
    }
    public void setFieldlabel(String fieldlabel) {
        this.fieldlabel = fieldlabel;
    }

    public Integer getListdisplay() {
        return listdisplay;
    }

    public void setListdisplay(Integer listdisplay) {
        this.listdisplay = listdisplay;
    }

    public Integer getFormdisplay() {
        return formdisplay;
    }

    public void setFormdisplay(Integer formdisplay) {
        this.formdisplay = formdisplay;
    }

    public Integer getQuerydisplay() {
        return querydisplay;
    }

    public void setQuerydisplay(Integer querydisplay) {
        this.querydisplay = querydisplay;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getStatusmap() {
        return statusmap;
    }

    public void setStatusmap(String statusmap) {
        this.statusmap = statusmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
