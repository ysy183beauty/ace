package com.ace.sqlxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sqls")
@XmlAccessorType(XmlAccessType.FIELD)
public class SqlList {
    @XmlElement(name = "li")
    private List<SqlEntity> list;
    public SqlList(List<SqlEntity> list) {
        this.list = list;
    }
    public List<SqlEntity> getList() {
        return list;
    }
    public void setList(List<SqlEntity> list) {
        this.list = list;
    }
    public SqlList(){}
}
