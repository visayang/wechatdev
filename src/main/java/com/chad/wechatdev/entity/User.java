package com.chad.wechatdev.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
@Getter
@Setter
public class User implements Serializable {

  private Integer id;
  private String openid;
  private String name;
  private String headImg;
  private String phone;
  private String sign;
  private Integer sex;
  private String city;
  private java.util.Date createTime;
  private String shopid;
  private String companyId;
  private String deskNum;
  private String environmental;


  public User(String openid, String name, String headImg) {
    this.openid = openid;
    this.name = name;
    this.headImg = headImg;
  }
  public User(){

  }
}
