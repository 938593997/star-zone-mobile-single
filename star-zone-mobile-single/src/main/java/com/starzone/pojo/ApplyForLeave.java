/**
 * @filename:ApplyForLeave 2019年9月27日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**   
 * @Description:  请假申请
 * @Author:       qiu_hf   
 * @CreateDate:   2019年9月27日
 * @Version:      V1.0
 */
@SuppressWarnings("unused")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyForLeave implements Serializable {

	private static final long serialVersionUID = 1569587961694L;
	
	@ApiModelProperty(name = "id" , value = "主键id")
	private String id;
	@ApiModelProperty(name = "userId" , value = "申请人")
	private String userId;
	@ApiModelProperty(name = "owner" , value = "申请人id")
	private String owner;
	@ApiModelProperty(name = "applyDate" , value = "请假天数")
	private String applyDate;
	@ApiModelProperty(name = "startDate" , value = "开始请假时间")
	private String startDate;
	@ApiModelProperty(name = "endDate" , value = "结束请假时间")
	private String endDate;
	@ApiModelProperty(name = "applyReason" , value = "请假原因")
	private String applyReason;
	@ApiModelProperty(name = "description" , value = "备注")
	private String description;
	@ApiModelProperty(name = "processId" , value = "流程实例id")
	private String processId;
	
	@Transient
	private List<HistoricTaskInstance> historyList; // 审批历史
	
	@Transient
	private String activedTitle; // 区分是我的审批 我的申请 审批历史
	
	@Transient
	private String imgFollowNginxUrl; // 流程跟踪nginx路径
	
	@Transient
	private List<Task> taskList; // 用户任务列表（待审批列表）
	
	@Transient
	private String condition;   // 前端传过来的条件 0表示不同意  1表示同意且请假天数小于2天  大于等于2表示同意，且请假天数大于2
	
	@Transient
	private String keyWords; // 模糊搜索关键字
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getApplyReason() {
		return applyReason;
	}
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public List<HistoricTaskInstance> getHistoryList() {
		return historyList;
	}
	public void setHistoryList(List<HistoricTaskInstance> historyList) {
		this.historyList = historyList;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getActivedTitle() {
		return activedTitle;
	}
	public void setActivedTitle(String activedTitle) {
		this.activedTitle = activedTitle;
	}
	public String getImgFollowNginxUrl() {
		return imgFollowNginxUrl;
	}
	public void setImgFollowNginxUrl(String imgFollowNginxUrl) {
		this.imgFollowNginxUrl = imgFollowNginxUrl;
	}
	public List<Task> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
}
