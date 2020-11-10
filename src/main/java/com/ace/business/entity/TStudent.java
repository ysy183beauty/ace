package com.ace.business.entity;
import java.sql.Timestamp;
public class TStudent {
  private String id;
  private String name;
  private String gid;
  private String cid;
  private Timestamp startdate;
  private Timestamp enddate;
  private String introduce;
  private String sex;
  private String address;
  private String age;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getGid() {
    return gid;
  }

  public void setGid(String gid) {
    this.gid = gid;
  }


  public String getCid() {
    return cid;
  }

  public void setCid(String cid) {
    this.cid = cid;
  }


  public Timestamp getStartdate() {
    return startdate;
  }

  public void setStartdate(Timestamp startdate) {
    this.startdate = startdate;
  }


  public Timestamp getEnddate() {
    return enddate;
  }

  public void setEnddate(Timestamp enddate) {
    this.enddate = enddate;
  }


  public String getIntroduce() {
    return introduce;
  }

  public void setIntroduce(String introduce) {
    this.introduce = introduce;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

}
