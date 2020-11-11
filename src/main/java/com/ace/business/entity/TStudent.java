package com.ace.business.entity;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;

public class TStudent {
  private Integer id;
  private String name;
  private Integer gid;
  private Integer cid;
  private Date startdate;
  private Date enddate;
  private Clob introduce;
  private String sex;
  private Clob address;
  private Integer age;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getGid() {
    return gid;
  }

  public void setGid(Integer gid) {
    this.gid = gid;
  }

  public Integer getCid() {
    return cid;
  }

  public void setCid(Integer cid) {
    this.cid = cid;
  }

  public Date getStartdate() {
    return startdate;
  }

  public void setStartdate(Date startdate) {
    this.startdate = startdate;
  }

  public Date getEnddate() {
    return enddate;
  }

  public void setEnddate(Date enddate) {
    this.enddate = enddate;
  }
  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public Clob getAddress() {
    return address;
  }

  public void setAddress(Clob address) {
    this.address = address;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Clob getIntroduce() {
    return introduce;
  }

  public void setIntroduce(Clob introduce) {
    this.introduce = introduce;
  }
}
