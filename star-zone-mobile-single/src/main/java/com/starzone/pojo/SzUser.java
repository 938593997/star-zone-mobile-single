/**
 * @filename:SzUser 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**   
 * @Description:  star-zone用户
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@SuppressWarnings("unused")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="sz_user")
public class SzUser implements Serializable {

	private static final long serialVersionUID = 1555145693532L;
	
	@ApiModelProperty(name = "id" , value = "")
	@Id
	@GeneratedValue
	private String id;
	@ApiModelProperty(name = "name" , value = "")
	private String name;
	@ApiModelProperty(name = "picUrl" , value = "")
	private String picUrl;
	@ApiModelProperty(name = "sex" , value = "")
	private String sex;
	@ApiModelProperty(name = "password" , value = "")
	private String password;
	@ApiModelProperty(name = "des" , value = "")
	private String des;
	@ApiModelProperty(name = "birthday" , value = "")
	private String birthday;
	@ApiModelProperty(name = "registDate" , value = "")
	private String registDate;
	@ApiModelProperty(name = "lastLogin" , value = "")
	private String lastLogin;
	@ApiModelProperty(name = "ip" , value = "")
	private String ip;
	@ApiModelProperty(name = "status" , value = "")
	private String status;
	@ApiModelProperty(name = "useSzDate" , value = "")
	private String useSzDate;
	@ApiModelProperty(name = "role" , value = "")
	private String role;
	@ApiModelProperty(name = "ext1" , value = "城市")
	private String ext1;
	@ApiModelProperty(name = "ext2" , value = "邮箱")
	private String ext2;
	@ApiModelProperty(name = "ext3" , value = "")
	private String ext3;
	@ApiModelProperty(name = "ext4" , value = "")
	private String ext4;
	@ApiModelProperty(name = "ext5" , value = "")
	private String ext5;
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
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUseSzDate() {
		return useSzDate;
	}
	public void setUseSzDate(String useSzDate) {
		this.useSzDate = useSzDate;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public String getExt4() {
		return ext4;
	}
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}
	public String getExt5() {
		return ext5;
	}
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}
	
}
