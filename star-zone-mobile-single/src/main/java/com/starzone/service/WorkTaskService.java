package com.starzone.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.starzone.pojo.WorkTask;
import com.starzone.utils.AppPage;

/**
 * 任务管理业务层接口
 * @doc 说明
 * @FileName WorkTaskService.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2018年12月15日
 * @history 1.0.0.0 2018年12月15日 下午6:28:11 created by【qiu_hf】
 */
public interface WorkTaskService {

	/**
	 * 获取所有任务信息
	 * @doc 说明
	 * @param workTask 前端传过来的过滤信息对象
	 * @param currentPage 当前页
	 * @param pageSize 每页显示数
	 * @return 所有任务信息集合
	 * @author qiu_hf
	 * @history 2018年12月15日 下午6:45:32 Create by 【qiu_hf】
	 */
	List<WorkTask> getAllWorkTasks(WorkTask workTask, int currentPage, int pageSize);

	List<WorkTask> getAllWorkTask();

	void deleteWorkTask(long id);

	void createWorkTask(WorkTask workTask);

	void modifyWorkTask(WorkTask workTask);

	/**
	 * 任务数量信息（供前端饼状图、柱状图使用）
	 * @doc 说明
	 * @return 任务数量信息
	 * @author qiu_hf
	 * @history 2018年12月22日 下午3:59:58 Create by 【qiu_hf】
	 */
	List<WorkTask> findTaskMessages();
	
	Page<WorkTask> getPageSort(Integer pageNum, Integer pageLimit);
	
	/**
	 * Specification动态拼接参数 基本用法
	 * @explain 查询star-zone用户集合JPA
	 * @param   对象参数：SzUser
	 * @return  List<SzUser>
	 * @author  qiu_hf
	 */
	public List<WorkTask> querySzUserListSpecificationJPA(Specification<WorkTask> querySpecifi);

	/**
	 * Example动态拼接参数 基本用法
	 * @explain 查询star-zone用户集合JPA
	 * @param   对象参数：SzUser
	 * @return  List<SzUser>
	 * @author  qiu_hf
	 */
	List<WorkTask> querySzUserListExampleJPA(Example<WorkTask> example);

	Page<WorkTask> getWorkTaskPageBySpecification(AppPage<WorkTask> page);

}
