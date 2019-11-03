package com.starzone.dao;


import java.util.List;

//import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.starzone.pojo.WorkTask;

/**
 * 任务管理数据持久层
 * @doc 说明
 * @FileName WorkTaskDao.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2018年12月15日
 * @history 1.0.0.0 2018年12月15日 下午6:31:52 created by【qiu_hf】
 */
@Mapper
public interface WorkTaskDao extends JpaRepository<WorkTask,Long>, JpaSpecificationExecutor<WorkTask>{

	/**
	 * 修改任务信息
	 * @doc 说明  自定义操作用Query，?1 ?2 ?3表示参数位置，修改操作、删除操作要加 @Modifying 注解。
	 * nativeQuery = true 就代表使用原始 sql（用来查询的是表字段跟表名）
	 * @param name 姓名
	 * @param taskContent 任务内容
	 * @param createDate 创建时间
	 * @param finishDate 结束时间
	 * @param isFinish 任务状态（1未完成 2已完成）
	 * @param id 数据主键id
	 * @author qiu_hf
	 * @history 2018年12月16日 下午12:55:39 Create by 【qiu_hf】
	 */
	@Modifying
//    @Transactional
    @Query(value="update work_task set name=?1,task_content=?2,create_date=?3,finish_date=?4,is_finish=?5 where id=?6",nativeQuery = true)
	void modifyWorkTasks(String name, String taskContent, String createDate, String finishDate, int isFinish, Long id);

	/**
	 * 注释的方式是用hql的方式
	 * @doc 说明 用来查询的是实体字段跟实体名
	 * @return
	 * @author qiu_hf
	 * @history 2018年12月22日 下午4:42:59 Create by 【qiu_hf】
	 */
	@Query(value="select t.id,t.create_date,t.finish_date,t.is_finish,count(*) AS task_content,t.name from work_task t GROUP BY t.name ORDER BY t.id DESC",nativeQuery = true)
//	@Query(value="select id,count(*) AS taskNum,name from WorkTask GROUP BY name ORDER BY id DESC")
	List<WorkTask> findTaskMessages();

//	@Select("SELECT * FROM work_task")
//	List<WorkTask> getAllWorkTasks(WorkTask workTask, int currentPage, int pageSize);

}
