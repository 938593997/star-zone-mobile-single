package com.starzone.vo;

import javax.persistence.Transient;

/**
 * 返回String、对象、List、Map的用法 业务bean
 * @doc 说明
 * @FileName UsageUser.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月21日
 * @history 1.0.0.0 2019年9月21日 下午9:52:54 created by【qiu_hf】
 */
public class UsageUser {

	private long id;
	
	private String name;
	
	private String sex;
	
	@Transient
	private String ids;
	
	public UsageUser(){}
	
	public UsageUser(long id, String name, String sex) {
		this.id = id;
		this.name = name;
		this.sex = sex;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
