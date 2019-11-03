package com.starzone.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 工作流节点信息
 * @doc 说明
 * @FileName ActivitiNode.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月26日
 * @history 1.0.0.0 2019年9月26日 下午7:34:16 created by【qiu_hf】
 */
public class ActivitiNode implements Serializable{

	private static final long serialVersionUID = 2248469053925414262L;
	
	private String userId; // 其实是用户名
	 
    private String condition; // 提交条件
 
    private Date startDate;
 
    private Date endDate;
 
    private float totalDay;
 
    private String desc;
 
    private String taskId;
 
    private String taskName;
 
    private String processId;           // 流程实例id
    
    private String bpmnName;            // 流程图名（流程定义表中的key）
    
    private String bpmnNamePath;        // 流程图相对路径
    
    private String createTime;          // 流程创建时间
    
    private String endTime; // 处理时间
    
    private String assignee; // 处理人
    
    private String deleteReason; // 处理状态
    
    private Long durationInMillis; // 处理了多久
    
    private Boolean isReject; // 是否是驳回到第一个节点

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public float getTotalDay() {
		return totalDay;
	}

	public void setTotalDay(float totalDay) {
		this.totalDay = totalDay;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getBpmnName() {
		return bpmnName;
	}

	public void setBpmnName(String bpmnName) {
		this.bpmnName = bpmnName;
	}

	public String getBpmnNamePath() {
		return bpmnNamePath;
	}

	public void setBpmnNamePath(String bpmnNamePath) {
		this.bpmnNamePath = bpmnNamePath;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public Long getDurationInMillis() {
		return durationInMillis;
	}

	public void setDurationInMillis(Long durationInMillis) {
		this.durationInMillis = durationInMillis;
	}

	public Boolean getIsReject() {
		return isReject;
	}

	public void setIsReject(Boolean isReject) {
		this.isReject = isReject;
	}
    
}
