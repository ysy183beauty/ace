package com.ace.sqlxml;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "li")
@XmlAccessorType(XmlAccessType.FIELD)
public class SqlEntity {
    @XmlAttribute(name = "id") //sql语句的主键
    private String id;
    @XmlAttribute(name = "value") //sql语句的值
    private String value;
    @XmlAttribute(name = "description")
    private String description;//描述信息
    public SqlEntity(String id, String value, String description) {
        this.id = id;
        this.value = value;
        this.description = description;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public SqlEntity(){}
}
