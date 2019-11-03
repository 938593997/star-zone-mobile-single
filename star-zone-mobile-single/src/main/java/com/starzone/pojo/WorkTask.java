package com.starzone.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;

/**
 * 工作任务管理类
 * @doc 说明
 * @FileName WorkTask.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2018年12月15日
 * @history 1.0.0.0 2018年12月15日 下午6:10:26 created by【qiu_hf】
 */
@Entity
@Table(name="work_task")
public class WorkTask {

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue
	@ApiModelProperty(name = "id" , value = "")
	private long id;
	
	/**
	 * 谁的任务
	 */
	@Column(name="name", nullable = false, unique = true)
	@ApiModelProperty(name = "name" , value = "")
	private String name;
	
	/**
	 * 任务内容
	 */
	@Column(name="task_content", nullable = false)
	@ApiModelProperty(name = "taskContent" , value = "")
	private String taskContent;
	
	/**
	 * 任务创建时间
	 */
	@Column(name="create_date", nullable = false)
	@ApiModelProperty(name = "createDate" , value = "")
	private String createDate;
	
	/**
	 * 任务完成时间
	 */
	@Column(name="finish_date", nullable = true)
	@ApiModelProperty(name = "finishDate" , value = "")
	private String finishDate;
	
	/**
	 * 任务是否完成
	 */
	@Column(name="is_finish", nullable = false)
	@ApiModelProperty(name = "isFinish" , value = "")
	private int isFinish;
	
	@Transient
	private String startDate;
	
	@Transient
	private String endDate;
	
	/**
	 * 接收分组查询任务数
	 */
//	private int taskNum;
//	
//	public int getTaskNum() {
//		return taskNum;
//	}
//	public void setTaskNum(int taskNum) {
//		this.taskNum = taskNum;
//	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTaskContent() {
		return taskContent;
	}
	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public int getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(int isFinish) {
		this.isFinish = isFinish;
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
	
}
