package com.starzone.vo;

import java.util.List;


/**
 * 邮件发送业务bean
 * @doc 说明
 * @FileName EmailBean.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月17日
 * @history 1.0.0.0 2019年9月17日 下午8:45:02 created by【qiu_hf】
 */
public class EmailBean {

	private String name; // 用户名
	
	private String task; // 任务
	
	private String endTime; // 截止时间
	
	private List<String> to; // 收件人邮箱
	
	private String userName; // 登入人账户名
	
	private String content; // 邮件内容
	
	private String subject; // 邮件主题

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
